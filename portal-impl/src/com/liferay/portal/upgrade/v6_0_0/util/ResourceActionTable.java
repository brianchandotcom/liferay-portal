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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 * @deprecated As of 7.0.1, with no direct replacement
 */
@Deprecated
public class ResourceActionTable {

	public static final String TABLE_NAME = "ResourceAction";

	public static final Object[][] TABLE_COLUMNS = {
		{"resourceActionId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"actionId", Types.VARCHAR},
		{"bitwiseValue", Types.BIGINT}
	};

	public static final String TABLE_SQL_CREATE = "create table ResourceAction (resourceActionId LONG not null primary key,name VARCHAR(255) null,actionId VARCHAR(75) null,bitwiseValue LONG)";

	public static final String TABLE_SQL_DROP = "drop table ResourceAction";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_81F2DB09 on ResourceAction (name)",
		"create unique index IX_EDB9986E on ResourceAction (name, actionId)"
	};

}