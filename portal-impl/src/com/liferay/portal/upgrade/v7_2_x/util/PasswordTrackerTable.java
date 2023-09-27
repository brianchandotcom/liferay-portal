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
public class PasswordTrackerTable {

	public static final String TABLE_NAME = "PasswordTracker";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"passwordTrackerId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"password_", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("passwordTrackerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("password_", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table PasswordTracker (mvccVersion LONG default 0 not null,passwordTrackerId LONG not null primary key,companyId LONG,userId LONG,createDate DATE null,password_ VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table PasswordTracker";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_326F75BD on PasswordTracker (userId)"
	};

}