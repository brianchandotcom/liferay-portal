/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;IndexEntries_KeywordsEntries&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntry
 * @see KeywordsEntry
 * @generated
 */
public class IndexEntries_KeywordsEntriesTable
	extends BaseTable<IndexEntries_KeywordsEntriesTable> {

	public static final IndexEntries_KeywordsEntriesTable INSTANCE =
		new IndexEntries_KeywordsEntriesTable();

	public final Column<IndexEntries_KeywordsEntriesTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<IndexEntries_KeywordsEntriesTable, Long> indexEntryId =
		createColumn(
			"indexEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<IndexEntries_KeywordsEntriesTable, Long>
		keywordsEntryId = createColumn(
			"keywordsEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);

	private IndexEntries_KeywordsEntriesTable() {
		super(
			"IndexEntries_KeywordsEntries",
			IndexEntries_KeywordsEntriesTable::new);
	}

}