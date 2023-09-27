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
public class PortalPreferencesTable {

	public static final String TABLE_NAME = "PortalPreferences";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"portalPreferencesId", Types.BIGINT},
		{"ownerId", Types.BIGINT},
		{"ownerType", Types.INTEGER},
		{"preferences", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("portalPreferencesId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ownerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ownerType", Types.INTEGER);

TABLE_COLUMNS_MAP.put("preferences", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE = "create table PortalPreferences (mvccVersion LONG default 0 not null,portalPreferencesId LONG not null primary key,ownerId LONG,ownerType INTEGER,preferences TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table PortalPreferences";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_D1F795F1 on PortalPreferences (ownerId, ownerType)"
	};

}