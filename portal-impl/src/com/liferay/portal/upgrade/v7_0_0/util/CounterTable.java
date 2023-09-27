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
public class CounterTable {

	public static final String TABLE_NAME = "Counter";

	public static final Object[][] TABLE_COLUMNS = {
		{"name", Types.VARCHAR},
		{"currentId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("currentId", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table Counter (name VARCHAR(75) not null primary key,currentId LONG)";

	public static final String TABLE_SQL_DROP = "drop table Counter";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}