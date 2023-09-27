/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DSLQueryEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryEntry
 * @generated
 */
public class DSLQueryEntryTable extends BaseTable<DSLQueryEntryTable> {

	public static final DSLQueryEntryTable INSTANCE = new DSLQueryEntryTable();

	public final Column<DSLQueryEntryTable, Long> dslQueryEntryId =
		createColumn(
			"dslQueryEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DSLQueryEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private DSLQueryEntryTable() {
		super("DSLQueryEntry", DSLQueryEntryTable::new);
	}

}