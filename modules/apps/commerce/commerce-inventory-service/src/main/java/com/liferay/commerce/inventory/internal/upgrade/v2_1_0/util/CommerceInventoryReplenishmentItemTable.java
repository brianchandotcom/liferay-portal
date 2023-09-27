/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.upgrade.v2_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceInventoryReplenishmentItemTable {

	public static final String TABLE_NAME = "CIReplenishmentItem";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"CIReplenishmentItemId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"commerceInventoryWarehouseId", Types.BIGINT}, {"sku", Types.VARCHAR},
		{"availabilityDate", Types.TIMESTAMP}, {"quantity", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("CIReplenishmentItemId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commerceInventoryWarehouseId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("sku", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("availabilityDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("quantity", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table CIReplenishmentItem (mvccVersion LONG default 0 not null,CIReplenishmentItemId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceInventoryWarehouseId LONG,sku VARCHAR(75) null,availabilityDate DATE null,quantity INTEGER)";

	public static final String TABLE_SQL_DROP =
"drop table CIReplenishmentItem";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}