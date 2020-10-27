/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.internal.upgrade.v2_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceCatalogTable {

	public static final String TABLE_NAME = "CommerceCatalog";

	public static final Object[][] TABLE_COLUMNS = {
		{"externalReferenceCode", Types.VARCHAR},
		{"commerceCatalogId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}, {"commerceCurrencyCode", Types.VARCHAR},
		{"catalogDefaultLanguageId", Types.VARCHAR}, {"system_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceCatalogId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceCurrencyCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("catalogDefaultLanguageId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("system_", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceCatalog (externalReferenceCode VARCHAR(75) null,commerceCatalogId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,commerceCurrencyCode VARCHAR(75) null,catalogDefaultLanguageId VARCHAR(75) null,system_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table CommerceCatalog";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_1198BFF9 on CommerceCatalog (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_65864AFC on CommerceCatalog (companyId, system_)"
	};

}