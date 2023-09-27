/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v2_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CPDefinitionOptionRelTable {

	public static final String TABLE_NAME = "CPDefinitionOptionRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"CPDefinitionOptionRelId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"CPDefinitionId", Types.BIGINT}, {"CPOptionId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"DDMFormFieldTypeName", Types.VARCHAR}, {"priority", Types.DOUBLE},
		{"facetable", Types.BOOLEAN}, {"required", Types.BOOLEAN},
		{"skuContributor", Types.BOOLEAN}, {"key_", Types.VARCHAR},
		{"priceType", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("CPDefinitionOptionRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("CPDefinitionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("CPOptionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("DDMFormFieldTypeName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("facetable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("required", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("skuContributor", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priceType", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table CPDefinitionOptionRel (uuid_ VARCHAR(75) null,CPDefinitionOptionRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPDefinitionId LONG,CPOptionId LONG,name STRING null,description STRING null,DDMFormFieldTypeName VARCHAR(75) null,priority DOUBLE,facetable BOOLEAN,required BOOLEAN,skuContributor BOOLEAN,key_ VARCHAR(75) null,priceType VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP =
"drop table CPDefinitionOptionRel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_E57A1C2A on CPDefinitionOptionRel (CPDefinitionId, CPOptionId)",
		"create unique index IX_75456D8D on CPDefinitionOptionRel (CPDefinitionId, key_[$COLUMN_LENGTH:75$])",
		"create index IX_BDB8420C on CPDefinitionOptionRel (CPDefinitionId, required)",
		"create index IX_749E99EB on CPDefinitionOptionRel (CPDefinitionId, skuContributor)",
		"create index IX_449BFCFE on CPDefinitionOptionRel (companyId)",
		"create index IX_A65BAB00 on CPDefinitionOptionRel (groupId)",
		"create index IX_7BED0C5E on CPDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_EB691260 on CPDefinitionOptionRel (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}