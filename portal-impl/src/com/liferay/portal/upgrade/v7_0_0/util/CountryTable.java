/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CountryTable {

	public static final String TABLE_NAME = "Country";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"countryId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"a2", Types.VARCHAR},
		{"a3", Types.VARCHAR},
		{"number_", Types.VARCHAR},
		{"idd_", Types.VARCHAR},
		{"zipRequired", Types.BOOLEAN},
		{"active_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("countryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("a2", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("a3", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("number_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("idd_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("zipRequired", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table Country (mvccVersion LONG default 0 not null,countryId LONG not null primary key,name VARCHAR(75) null,a2 VARCHAR(75) null,a3 VARCHAR(75) null,number_ VARCHAR(75) null,idd_ VARCHAR(75) null,zipRequired BOOLEAN,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Country";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_717B97E1 on Country (a2[$COLUMN_LENGTH:75$])",
		"create unique index IX_717B9BA2 on Country (a3[$COLUMN_LENGTH:75$])",
		"create index IX_25D734CD on Country (active_)",
		"create unique index IX_19DA007B on Country (name[$COLUMN_LENGTH:75$])"
	};

}