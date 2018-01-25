/**
 * Odoo, Open Source Management Solution
 * Copyright (C) 2012-today Odoo SA (<http:www.odoo.com>)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details
 * <p/>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http:www.gnu.org/licenses/>
 * <p/>
 * Created on 14/7/16 3:56 PM
 */
package com.odoo.addons.sponsor.models;

import android.content.Context;

import com.odoo.base.addons.res.ResPartner;
import com.odoo.core.orm.OModel;
import com.odoo.core.orm.OValues;
import com.odoo.core.orm.annotation.Odoo;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.OBlob;
import com.odoo.core.orm.fields.types.OInteger;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.datas.OConstants;

import java.util.ArrayList;

import odoo.helper.ODomain;

public class EventSponsor extends OModel {
    public static final String TAG = EventSponsor.class.getSimpleName();

    OColumn image_medium = new OColumn("Logo", OBlob.class);
    OColumn url = new OColumn("URL", OVarchar.class).setSize(100);
    OColumn sponsor_type_id = new OColumn("Type", EventSponsorType.class, OColumn.RelationType.ManyToOne);
    OColumn sequence = new OColumn("Sequence", OInteger.class).setDefaultValue(0);
    OColumn partner_id = new OColumn("Sponsor", ResPartner.class, OColumn.RelationType.ManyToOne);

    @Odoo.Functional(depends = {"partner_id"}, store = true, method = "storeSponsorName")
    OColumn sponsor_name = new OColumn("Sponsor name", OVarchar.class).setLocalColumn();

    @Odoo.Functional(depends = {"sponsor_type_id"}, store = true, method = "storeType")
    OColumn sponsor_type = new OColumn("Type", OVarchar.class).setLocalColumn();

    public EventSponsor(Context context) {
        super(context, "event.sponsor");
    }

    public String storeSponsorName(OValues values) {
        return ((ArrayList) values.get("partner_id")).get(1).toString();
    }

    public String storeType(OValues values) {
        return ((ArrayList) values.get("sponsor_type_id")).get(1).toString();
    }

    @Override
    public ODomain defaultDomain() {
        ODomain domain = new ODomain();
        domain.add("event_id", "=", OConstants.EVENT_ID);
        return domain;
    }

    @Override
    public boolean checkForCreateDate() {
        return false;
    }
}
