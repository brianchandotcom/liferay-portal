/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;WhereClauseEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see WhereClauseEntry
 * @generated
 */
public class WhereClauseEntryTable extends BaseTable<WhereClauseEntryTable> {

	public static final WhereClauseEntryTable INSTANCE =
		new WhereClauseEntryTable();

	public final Column<WhereClauseEntryTable, Long> whereClauseEntryId =
		createColumn(
			"whereClauseEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<WhereClauseEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WhereClauseEntryTable, String> nickname = createColumn(
		"nickname", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private WhereClauseEntryTable() {
		super("WhereClauseEntry", WhereClauseEntryTable::new);
	}

}
// SB-Hash:-1736909656