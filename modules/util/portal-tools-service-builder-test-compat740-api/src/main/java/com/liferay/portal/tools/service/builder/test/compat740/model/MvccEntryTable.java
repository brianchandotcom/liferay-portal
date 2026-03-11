/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;MvccEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MvccEntry
 * @generated
 */
public class MvccEntryTable extends BaseTable<MvccEntryTable> {

	public static final MvccEntryTable INSTANCE = new MvccEntryTable();

	public final Column<MvccEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MvccEntryTable, Long> mvccEntryId = createColumn(
		"mvccEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MvccEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MvccEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private MvccEntryTable() {
		super("MvccEntry", MvccEntryTable::new);
	}

}