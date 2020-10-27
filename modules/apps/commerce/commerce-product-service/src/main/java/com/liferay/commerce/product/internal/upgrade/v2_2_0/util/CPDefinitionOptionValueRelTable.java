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

package com.liferay.commerce.product.internal.upgrade.v2_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CPDefinitionOptionValueRelTable {

	public static final String TABLE_NAME = "CPDefinitionOptionValueRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"CPDefinitionOptionValueRelId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"CPDefinitionOptionRelId", Types.BIGINT},
		{"CPInstanceUuid", Types.VARCHAR}, {"CProductId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"priority", Types.DOUBLE},
		{"key_", Types.VARCHAR}, {"quantity", Types.INTEGER},
		{"preselected", Types.BOOLEAN}, {"price", Types.DECIMAL}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("CPDefinitionOptionValueRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("CPDefinitionOptionRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("CPInstanceUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("CProductId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("quantity", Types.INTEGER);

TABLE_COLUMNS_MAP.put("preselected", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("price", Types.DECIMAL);

}
	public static final String TABLE_SQL_CREATE =
"create table CPDefinitionOptionValueRel (uuid_ VARCHAR(75) null,CPDefinitionOptionValueRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPDefinitionOptionRelId LONG,CPInstanceUuid VARCHAR(75) null,CProductId LONG,name STRING null,priority DOUBLE,key_ VARCHAR(75) null,quantity INTEGER,preselected BOOLEAN,price DECIMAL(30, 16) null)";

	public static final String TABLE_SQL_DROP =
"drop table CPDefinitionOptionValueRel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_8FDF08C0 on CPDefinitionOptionValueRel (CPDefinitionOptionRelId, key_[$COLUMN_LENGTH:75$])",
		"create index IX_4A77D282 on CPDefinitionOptionValueRel (CPDefinitionOptionRelId, preselected)",
		"create index IX_3EB86274 on CPDefinitionOptionValueRel (CPInstanceUuid[$COLUMN_LENGTH:75$])",
		"create index IX_44C2E505 on CPDefinitionOptionValueRel (companyId)",
		"create index IX_695AE8C7 on CPDefinitionOptionValueRel (groupId)",
		"create index IX_2434CAD7 on CPDefinitionOptionValueRel (key_[$COLUMN_LENGTH:75$])",
		"create index IX_CD95E77 on CPDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_34516B9 on CPDefinitionOptionValueRel (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}