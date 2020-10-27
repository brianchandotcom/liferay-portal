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

package com.liferay.commerce.product.internal.upgrade.v2_3_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceChannelTable {

	public static final String TABLE_NAME = "CommerceChannel";

	public static final Object[][] TABLE_COLUMNS = {
		{"externalReferenceCode", Types.VARCHAR},
		{"commerceChannelId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"siteGroupId", Types.BIGINT}, {"name", Types.VARCHAR},
		{"type_", Types.VARCHAR}, {"typeSettings", Types.VARCHAR},
		{"commerceCurrencyCode", Types.VARCHAR},
		{"priceDisplayType", Types.VARCHAR},
		{"discountsTargetNetPrice", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceChannelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("siteGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("typeSettings", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceCurrencyCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priceDisplayType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("discountsTargetNetPrice", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceChannel (externalReferenceCode VARCHAR(75) null,commerceChannelId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,siteGroupId LONG,name VARCHAR(75) null,type_ VARCHAR(75) null,typeSettings VARCHAR(75) null,commerceCurrencyCode VARCHAR(75) null,priceDisplayType VARCHAR(75) null,discountsTargetNetPrice BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table CommerceChannel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_690E2FE3 on CommerceChannel (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_E1ECD95 on CommerceChannel (siteGroupId)"
	};

}