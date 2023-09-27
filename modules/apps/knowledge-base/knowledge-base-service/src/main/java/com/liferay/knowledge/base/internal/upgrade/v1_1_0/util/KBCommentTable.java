/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_1_0.util;

import java.sql.Types;

/**
 * @author Peter Shin
 */
public class KBCommentTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"kbCommentId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"content", Types.VARCHAR}, {"helpful", Types.BOOLEAN}
	};

	public static final String TABLE_NAME = "KBComment";

	public static final String TABLE_SQL_CREATE =
		"create table KBComment (uuid_ VARCHAR(75) null,kbCommentId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,content STRING null,helpful BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table KBComment";

}