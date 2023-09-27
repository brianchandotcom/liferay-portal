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
public class ClassNameTable {

	public static final String TABLE_NAME = "ClassName_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"classNameId", Types.BIGINT},
		{"value", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("value", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table ClassName_ (mvccVersion LONG default 0 not null,classNameId LONG not null primary key,value VARCHAR(200) null)";

	public static final String TABLE_SQL_DROP = "drop table ClassName_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_B27A301F on ClassName_ (value[$COLUMN_LENGTH:200$])"
	};

}