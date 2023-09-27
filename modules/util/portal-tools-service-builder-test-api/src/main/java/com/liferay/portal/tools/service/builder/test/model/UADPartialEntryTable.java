/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;UADPartialEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UADPartialEntry
 * @generated
 */
public class UADPartialEntryTable extends BaseTable<UADPartialEntryTable> {

	public static final UADPartialEntryTable INSTANCE =
		new UADPartialEntryTable();

	public final Column<UADPartialEntryTable, Long> uadPartialEntryId =
		createColumn(
			"uadPartialEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UADPartialEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UADPartialEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UADPartialEntryTable, String> message = createColumn(
		"message", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private UADPartialEntryTable() {
		super("UADPartialEntry", UADPartialEntryTable::new);
	}

}