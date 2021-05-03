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
public class PortletPreferencesTable {

	public static final String TABLE_NAME = "PortletPreferences";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"portletPreferencesId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"ownerId", Types.BIGINT}, {"ownerType", Types.INTEGER},
		{"plid", Types.BIGINT}, {"portletId", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("portletPreferencesId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ownerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ownerType", Types.INTEGER);

TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("portletId", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table PortletPreferences (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,portletPreferencesId LONG not null,companyId LONG,ownerId LONG,ownerType INTEGER,plid LONG,portletId VARCHAR(200) null,primary key (portletPreferencesId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table PortletPreferences";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F0B8A3A0 on PortletPreferences (companyId, ownerId, ownerType, portletId[$COLUMN_LENGTH:200$], ctCollectionId)",
		"create index IX_31DA3CB8 on PortletPreferences (ownerId, ctCollectionId)",
		"create index IX_451D78CC on PortletPreferences (ownerId, ownerType, plid, ctCollectionId)",
		"create unique index IX_CB778855 on PortletPreferences (ownerId, ownerType, plid, portletId[$COLUMN_LENGTH:200$], ctCollectionId)",
		"create index IX_258CCF40 on PortletPreferences (ownerId, ownerType, portletId[$COLUMN_LENGTH:200$], ctCollectionId)",
		"create index IX_D48717FF on PortletPreferences (ownerType, plid, portletId[$COLUMN_LENGTH:200$], ctCollectionId)",
		"create index IX_D6C3E66A on PortletPreferences (ownerType, portletId[$COLUMN_LENGTH:200$], ctCollectionId)",
		"create index IX_C77A74AD on PortletPreferences (plid, ctCollectionId)",
		"create index IX_67E205D4 on PortletPreferences (plid, portletId[$COLUMN_LENGTH:200$], ctCollectionId)",
		"create index IX_8D0717FF on PortletPreferences (portletId[$COLUMN_LENGTH:200$], ctCollectionId)"
	};

}