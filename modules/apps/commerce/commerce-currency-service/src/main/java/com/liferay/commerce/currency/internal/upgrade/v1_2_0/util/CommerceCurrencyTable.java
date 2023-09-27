/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.upgrade.v1_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceCurrencyTable {

	public static final String TABLE_NAME = "CommerceCurrency";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"commerceCurrencyId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"code_", Types.VARCHAR},
		{"name", Types.VARCHAR}, {"symbol", Types.VARCHAR},
		{"rate", Types.DECIMAL}, {"formatPattern", Types.VARCHAR},
		{"maxFractionDigits", Types.INTEGER},
		{"minFractionDigits", Types.INTEGER}, {"roundingMode", Types.VARCHAR},
		{"primary_", Types.BOOLEAN}, {"priority", Types.DOUBLE},
		{"active_", Types.BOOLEAN}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceCurrencyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("code_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("symbol", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("rate", Types.DECIMAL);

TABLE_COLUMNS_MAP.put("formatPattern", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("maxFractionDigits", Types.INTEGER);

TABLE_COLUMNS_MAP.put("minFractionDigits", Types.INTEGER);

TABLE_COLUMNS_MAP.put("roundingMode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("primary_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceCurrency (uuid_ VARCHAR(75) null,commerceCurrencyId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,code_ VARCHAR(75) null,name STRING null,symbol VARCHAR(75) null,rate BIGDECIMAL null,formatPattern STRING null,maxFractionDigits INTEGER,minFractionDigits INTEGER,roundingMode VARCHAR(75) null,primary_ BOOLEAN,priority DOUBLE,active_ BOOLEAN,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CommerceCurrency";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C671CBD3 on CommerceCurrency (companyId, active_)",
		"create unique index IX_2127F18C on CommerceCurrency (companyId, code_[$COLUMN_LENGTH:75$])",
		"create index IX_ADF54822 on CommerceCurrency (companyId, primary_, active_)",
		"create index IX_7C490A66 on CommerceCurrency (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}