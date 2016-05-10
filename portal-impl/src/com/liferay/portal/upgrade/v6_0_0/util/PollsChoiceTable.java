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
public class PollsChoiceTable {

	public static final String TABLE_NAME = "PollsChoice";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"choiceId", Types.BIGINT},
		{"questionId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table PollsChoice (uuid_ VARCHAR(75) null,choiceId LONG not null primary key,questionId LONG,name VARCHAR(75) null,description STRING null)";

	public static final String TABLE_SQL_DROP = "drop table PollsChoice";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_EC370F10 on PollsChoice (questionId)",
		"create unique index IX_D76DD2CF on PollsChoice (questionId, name)",
		"create index IX_6660B399 on PollsChoice (uuid_)"
	};

}