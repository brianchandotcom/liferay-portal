/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_1_0.util;

import java.sql.Types;

/**
 * @author Peter Shin
 */
public class KBTemplateTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"kbTemplateId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"title", Types.VARCHAR}, {"content", Types.CLOB},
		{"engineType", Types.INTEGER}, {"cacheable", Types.BOOLEAN}
	};

	public static final String TABLE_NAME = "KBTemplate";

	public static final String TABLE_SQL_CREATE =
		"create table KBTemplate (uuid_ VARCHAR(75) null,kbTemplateId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title STRING null,content TEXT null,engineType INTEGER,cacheable BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table KBTemplate";

}