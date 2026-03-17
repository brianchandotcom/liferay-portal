/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;MappingEntries_BasicEntries&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see BasicEntry
 * @see MappingEntry
 * @generated
 */
public class MappingEntries_BasicEntriesTable
	extends BaseTable<MappingEntries_BasicEntriesTable> {

	public static final MappingEntries_BasicEntriesTable INSTANCE =
		new MappingEntries_BasicEntriesTable();

	public final Column<MappingEntries_BasicEntriesTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MappingEntries_BasicEntriesTable, Long> basicEntryId =
		createColumn(
			"basicEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MappingEntries_BasicEntriesTable, Long> mappingEntryId =
		createColumn(
			"mappingEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);

	private MappingEntries_BasicEntriesTable() {
		super(
			"MappingEntries_BasicEntries",
			MappingEntries_BasicEntriesTable::new);
	}

}
// SB-Hash:601958646