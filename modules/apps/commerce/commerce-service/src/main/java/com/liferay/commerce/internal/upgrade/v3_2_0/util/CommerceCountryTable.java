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

package com.liferay.commerce.internal.upgrade.v3_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceCountryTable {

	public static final String TABLE_NAME = "CommerceCountry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"commerceCountryId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"name", Types.VARCHAR},
		{"billingAllowed", Types.BOOLEAN}, {"shippingAllowed", Types.BOOLEAN},
		{"twoLettersISOCode", Types.VARCHAR},
		{"threeLettersISOCode", Types.VARCHAR},
		{"numericISOCode", Types.INTEGER}, {"subjectToVAT", Types.BOOLEAN},
		{"priority", Types.DOUBLE}, {"active_", Types.BOOLEAN},
		{"lastPublishDate", Types.TIMESTAMP},
		{"channelFilterEnabled", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceCountryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("billingAllowed", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("shippingAllowed", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("twoLettersISOCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("threeLettersISOCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("numericISOCode", Types.INTEGER);

TABLE_COLUMNS_MAP.put("subjectToVAT", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("channelFilterEnabled", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceCountry (uuid_ VARCHAR(75) null,commerceCountryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name STRING null,billingAllowed BOOLEAN,shippingAllowed BOOLEAN,twoLettersISOCode VARCHAR(75) null,threeLettersISOCode VARCHAR(75) null,numericISOCode INTEGER,subjectToVAT BOOLEAN,priority DOUBLE,active_ BOOLEAN,lastPublishDate DATE null,channelFilterEnabled BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table CommerceCountry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F8360682 on CommerceCountry (companyId, active_)",
		"create index IX_9DE350C1 on CommerceCountry (companyId, billingAllowed, active_)",
		"create unique index IX_7C7152E0 on CommerceCountry (companyId, numericISOCode)",
		"create index IX_570D4D26 on CommerceCountry (companyId, shippingAllowed, active_)",
		"create unique index IX_415D595A on CommerceCountry (companyId, twoLettersISOCode[$COLUMN_LENGTH:75$])",
		"create index IX_91EA24D5 on CommerceCountry (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}