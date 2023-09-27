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
public class AccountTable {

	public static final String TABLE_NAME = "Account_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"accountId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"parentAccountId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"legalName", Types.VARCHAR},
		{"legalId", Types.VARCHAR}, {"legalType", Types.VARCHAR},
		{"sicCode", Types.VARCHAR}, {"tickerSymbol", Types.VARCHAR},
		{"industry", Types.VARCHAR}, {"type_", Types.VARCHAR},
		{"size_", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("accountId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("parentAccountId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("legalName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("legalId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("legalType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("sicCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("tickerSymbol", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("industry", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("size_", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table Account_ (mvccVersion LONG default 0 not null,accountId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentAccountId LONG,name VARCHAR(75) null,legalName VARCHAR(75) null,legalId VARCHAR(75) null,legalType VARCHAR(75) null,sicCode VARCHAR(75) null,tickerSymbol VARCHAR(75) null,industry VARCHAR(75) null,type_ VARCHAR(75) null,size_ VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table Account_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}