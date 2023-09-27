/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class OrganizationTable {

	public static final String TABLE_NAME = "Organization_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"externalReferenceCode", Types.VARCHAR},
		{"organizationId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"parentOrganizationId", Types.BIGINT}, {"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR}, {"type_", Types.VARCHAR},
		{"recursable", Types.BOOLEAN}, {"regionId", Types.BIGINT},
		{"countryId", Types.BIGINT}, {"statusId", Types.BIGINT},
		{"comments", Types.VARCHAR}, {"logoId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("organizationId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("parentOrganizationId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("recursable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("regionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("countryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("comments", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("logoId", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE =
"create table Organization_ (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,organizationId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentOrganizationId LONG,treePath STRING null,name VARCHAR(100) null,type_ VARCHAR(75) null,recursable BOOLEAN,regionId LONG,countryId LONG,statusId LONG,comments STRING null,logoId LONG)";

	public static final String TABLE_SQL_DROP = "drop table Organization_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_6B83F1C7 on Organization_ (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create unique index IX_E301BDF5 on Organization_ (companyId, name[$COLUMN_LENGTH:100$])",
		"create index IX_D834B361 on Organization_ (companyId, parentOrganizationId, name[$COLUMN_LENGTH:100$])",
		"create index IX_A9D85BA6 on Organization_ (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}