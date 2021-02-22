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

package com.liferay.commerce.internal.upgrade.v5_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceAddressTable {

	public static final String TABLE_NAME = "CommerceAddress";

	public static final Object[][] TABLE_COLUMNS = {
		{"externalReferenceCode", Types.VARCHAR},
		{"commerceAddressId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"name", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"street1", Types.VARCHAR},
		{"street2", Types.VARCHAR}, {"street3", Types.VARCHAR},
		{"city", Types.VARCHAR}, {"zip", Types.VARCHAR},
		{"regionId", Types.BIGINT}, {"countryId", Types.BIGINT},
		{"latitude", Types.DOUBLE}, {"longitude", Types.DOUBLE},
		{"phoneNumber", Types.VARCHAR}, {"defaultBilling", Types.BOOLEAN},
		{"defaultShipping", Types.BOOLEAN}, {"type_", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceAddressId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("street1", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("street2", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("street3", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("city", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("zip", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("regionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("countryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("latitude", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("longitude", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("phoneNumber", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("defaultBilling", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("defaultShipping", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceAddress (externalReferenceCode VARCHAR(75) null,commerceAddressId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,name VARCHAR(255) null,description STRING null,street1 VARCHAR(255) null,street2 VARCHAR(255) null,street3 VARCHAR(255) null,city VARCHAR(75) null,zip VARCHAR(75) null,regionId LONG,countryId LONG,latitude DOUBLE,longitude DOUBLE,phoneNumber VARCHAR(75) null,defaultBilling BOOLEAN,defaultShipping BOOLEAN,type_ INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table CommerceAddress";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_15EA4714 on CommerceAddress (classNameId, classPK)",
		"create index IX_CD76FE87 on CommerceAddress (commerceCountryId)",
		"create index IX_71C5A9DD on CommerceAddress (commerceRegionId)",
		"create index IX_6D0EADB7 on CommerceAddress (companyId, classNameId, classPK, type_)",
		"create index IX_4E616A54 on CommerceAddress (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_EEACF18E on CommerceAddress (groupId, classNameId, classPK, defaultBilling)",
		"create index IX_333246DF on CommerceAddress (groupId, classNameId, classPK, defaultShipping)"
	};

}