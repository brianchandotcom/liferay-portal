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

package com.liferay.portal.upgrade.v7_4_x.util;

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
		{"mvccVersion", Types.BIGINT}, {"portalPreferencesId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"ownerId", Types.BIGINT},
		{"ownerType", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("portalPreferencesId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ownerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ownerType", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table PortalPreferences (mvccVersion LONG default 0 not null,portalPreferencesId LONG not null primary key,companyId LONG,ownerId LONG,ownerType INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table PortalPreferences";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_D1F795F1 on PortalPreferences (ownerId, ownerType)"
	};

}