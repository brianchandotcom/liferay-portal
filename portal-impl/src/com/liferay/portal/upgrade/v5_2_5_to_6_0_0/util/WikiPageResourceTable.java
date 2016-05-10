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

package com.liferay.portal.upgrade.v5_2_5_to_6_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 * @deprecated As of 7.0.1, with no direct replacement
 */
@Deprecated
public class WikiPageResourceTable {

	public static final String TABLE_NAME = "WikiPageResource";

	public static final Object[][] TABLE_COLUMNS = {
		{"resourcePrimKey", Types.BIGINT},
		{"nodeId", Types.BIGINT},
		{"title", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table WikiPageResource (resourcePrimKey LONG not null primary key,nodeId LONG,title VARCHAR(255) null)";

	public static final String TABLE_SQL_DROP = "drop table WikiPageResource";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_21277664 on WikiPageResource (nodeId, title)"
	};

}