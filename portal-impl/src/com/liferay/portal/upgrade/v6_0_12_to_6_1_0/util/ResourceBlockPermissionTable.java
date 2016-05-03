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

package com.liferay.portal.upgrade.v6_0_12_to_6_1_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ResourceBlockPermissionTable {

	public static final String TABLE_NAME = "ResourceBlockPermission";

	public static final Object[][] TABLE_COLUMNS = {
		{"resourceBlockPermissionId", Types.BIGINT},
		{"resourceBlockId", Types.BIGINT},
		{"roleId", Types.BIGINT},
		{"actionIds", Types.BIGINT}
	};

	public static final String TABLE_SQL_CREATE = "create table ResourceBlockPermission (resourceBlockPermissionId LONG not null primary key,resourceBlockId LONG,roleId LONG,actionIds LONG)";

	public static final String TABLE_SQL_DROP = "drop table ResourceBlockPermission";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_4AB3756 on ResourceBlockPermission (resourceBlockId)",
		"create unique index IX_D63D20BB on ResourceBlockPermission (resourceBlockId, roleId)"
	};

}