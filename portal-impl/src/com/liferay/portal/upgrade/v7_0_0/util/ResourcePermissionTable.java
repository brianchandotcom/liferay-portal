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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Alberto Chaparro
 * @generated
 */
public class ResourcePermissionTable {

	public static final String TABLE_NAME = "ResourcePermission";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"resourcePermissionId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"scope", Types.INTEGER},
		{"primKey", Types.VARCHAR},
		{"primKeyId", Types.BIGINT},
		{"roleId", Types.BIGINT},
		{"ownerId", Types.BIGINT},
		{"actionIds", Types.BIGINT},
		{"viewActionId", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("resourcePermissionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("scope", Types.INTEGER);

TABLE_COLUMNS_MAP.put("primKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("primKeyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("roleId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ownerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("actionIds", Types.BIGINT);

TABLE_COLUMNS_MAP.put("viewActionId", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table ResourcePermission (mvccVersion LONG default 0 not null,resourcePermissionId LONG not null primary key,companyId LONG,name VARCHAR(255) null,scope INTEGER,primKey VARCHAR(255) null,primKeyId LONG,roleId LONG,ownerId LONG,actionIds LONG,viewActionId BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table ResourcePermission";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_8D83D0CE on ResourcePermission (companyId, name[$COLUMN_LENGTH:255$], scope, primKey[$COLUMN_LENGTH:255$], roleId)",
		"create index IX_49AEC6F3 on ResourcePermission (companyId, name[$COLUMN_LENGTH:255$], scope, primKeyId, roleId, viewActionId)",
		"create index IX_26284944 on ResourcePermission (companyId, primKey[$COLUMN_LENGTH:255$])",
		"create index IX_F6BAE86A on ResourcePermission (companyId, scope, primKey[$COLUMN_LENGTH:255$])",
		"create index IX_D5F1E2A2 on ResourcePermission (name[$COLUMN_LENGTH:255$])",
		"create index IX_A37A0588 on ResourcePermission (roleId)",
		"create index IX_F4555981 on ResourcePermission (scope)"
	};

}