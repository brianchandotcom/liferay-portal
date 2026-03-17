/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;MappingEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MappingEntry
 * @generated
 */
public class MappingEntryTable extends BaseTable<MappingEntryTable> {

	public static final MappingEntryTable INSTANCE = new MappingEntryTable();

	public final Column<MappingEntryTable, Long> mappingEntryId = createColumn(
		"mappingEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MappingEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MappingEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private MappingEntryTable() {
		super("MappingEntry", MappingEntryTable::new);
	}

}
// SB-Hash:-359702172