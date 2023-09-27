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
public class CommerceInventoryWarehouseTable {

	public static final String TABLE_NAME = "CIWarehouse";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"externalReferenceCode", Types.VARCHAR},
		{"CIWarehouseId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"active_", Types.BOOLEAN}, {"street1", Types.VARCHAR},
		{"street2", Types.VARCHAR}, {"street3", Types.VARCHAR},
		{"city", Types.VARCHAR}, {"zip", Types.VARCHAR},
		{"commerceRegionCode", Types.VARCHAR},
		{"countryTwoLettersISOCode", Types.VARCHAR}, {"latitude", Types.DOUBLE},
		{"longitude", Types.DOUBLE}, {"type_", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("CIWarehouseId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("street1", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("street2", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("street3", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("city", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("zip", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceRegionCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("countryTwoLettersISOCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("latitude", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("longitude", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table CIWarehouse (mvccVersion LONG default 0 not null,externalReferenceCode VARCHAR(75) null,CIWarehouseId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description VARCHAR(75) null,active_ BOOLEAN,street1 VARCHAR(75) null,street2 VARCHAR(75) null,street3 VARCHAR(75) null,city VARCHAR(75) null,zip VARCHAR(75) null,commerceRegionCode VARCHAR(75) null,countryTwoLettersISOCode VARCHAR(75) null,latitude DOUBLE,longitude DOUBLE,type_ VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table CIWarehouse";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}