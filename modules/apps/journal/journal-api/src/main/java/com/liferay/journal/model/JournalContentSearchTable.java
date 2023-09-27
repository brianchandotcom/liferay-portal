/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;JournalContentSearch&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see JournalContentSearch
 * @generated
 */
public class JournalContentSearchTable
	extends BaseTable<JournalContentSearchTable> {

	public static final JournalContentSearchTable INSTANCE =
		new JournalContentSearchTable();

	public final Column<JournalContentSearchTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<JournalContentSearchTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JournalContentSearchTable, Long> contentSearchId =
		createColumn(
			"contentSearchId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JournalContentSearchTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalContentSearchTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalContentSearchTable, Boolean> privateLayout =
		createColumn(
			"privateLayout", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<JournalContentSearchTable, Long> layoutId =
		createColumn("layoutId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalContentSearchTable, String> portletId =
		createColumn(
			"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalContentSearchTable, String> articleId =
		createColumn(
			"articleId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private JournalContentSearchTable() {
		super("JournalContentSearch", JournalContentSearchTable::new);
	}

}