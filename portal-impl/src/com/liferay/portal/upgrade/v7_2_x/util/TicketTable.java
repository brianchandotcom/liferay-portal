/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class TicketTable {

	public static final String TABLE_NAME = "Ticket";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ticketId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"createDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"key_", Types.VARCHAR}, {"type_", Types.INTEGER},
		{"extraInfo", Types.CLOB}, {"expirationDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ticketId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("extraInfo", Types.CLOB);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table Ticket (mvccVersion LONG default 0 not null,ticketId LONG not null primary key,companyId LONG,createDate DATE null,classNameId LONG,classPK LONG,key_ VARCHAR(75) null,type_ INTEGER,extraInfo TEXT null,expirationDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table Ticket";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_1E8DFB2E on Ticket (classNameId, classPK, type_)",
		"create index IX_8BACD0AA on Ticket (companyId, classNameId, classPK, type_)",
		"create index IX_B2468446 on Ticket (key_[$COLUMN_LENGTH:75$])"
	};

}