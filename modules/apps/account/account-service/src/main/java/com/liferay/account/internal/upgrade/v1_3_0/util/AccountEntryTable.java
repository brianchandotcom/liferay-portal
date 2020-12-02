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

package com.liferay.account.internal.upgrade.v1_3_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryTable {

	public static final String TABLE_NAME = "AccountEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"externalReferenceCode", Types.VARCHAR},
		{"accountEntryId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"defaultBillingAddressId", Types.BIGINT},
		{"defaultShippingAddressId", Types.BIGINT},
		{"parentAccountEntryId", Types.BIGINT}, {"description", Types.VARCHAR},
		{"domains", Types.VARCHAR}, {"logoId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"taxIdNumber", Types.VARCHAR},
		{"type_", Types.VARCHAR}, {"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("accountEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("defaultBillingAddressId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("defaultShippingAddressId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentAccountEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("domains", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("logoId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("taxIdNumber", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table AccountEntry (mvccVersion LONG default 0 not null,externalReferenceCode VARCHAR(75) null,accountEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,defaultBillingAddressId LONG,defaultShippingAddressId LONG,parentAccountEntryId LONG,description STRING null,domains STRING null,logoId LONG,name VARCHAR(100) null,taxIdNumber VARCHAR(75) null,type_ VARCHAR(75) null,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table AccountEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_FBFAF640 on AccountEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_48CB043 on AccountEntry (companyId, status)"
	};

}