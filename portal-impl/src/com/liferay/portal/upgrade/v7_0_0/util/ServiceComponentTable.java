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
public class ServiceComponentTable {

	public static final String TABLE_NAME = "ServiceComponent";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"serviceComponentId", Types.BIGINT},
		{"buildNamespace", Types.VARCHAR},
		{"buildNumber", Types.BIGINT},
		{"buildDate", Types.BIGINT},
		{"data_", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("serviceComponentId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("buildNamespace", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("buildNumber", Types.BIGINT);

TABLE_COLUMNS_MAP.put("buildDate", Types.BIGINT);

TABLE_COLUMNS_MAP.put("data_", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE = "create table ServiceComponent (mvccVersion LONG default 0 not null,serviceComponentId LONG not null primary key,buildNamespace VARCHAR(75) null,buildNumber LONG,buildDate LONG,data_ TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table ServiceComponent";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_4F0315B8 on ServiceComponent (buildNamespace[$COLUMN_LENGTH:75$], buildNumber)"
	};

}