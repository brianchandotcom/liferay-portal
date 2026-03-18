/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;MVCCEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MVCCEntry
 * @generated
 */
public class MVCCEntryTable extends BaseTable<MVCCEntryTable> {

	public static final MVCCEntryTable INSTANCE = new MVCCEntryTable();

	public final Column<MVCCEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MVCCEntryTable, Long> mvccEntryId = createColumn(
		"mvccEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MVCCEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MVCCEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private MVCCEntryTable() {
		super("MVCCEntry", MVCCEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-399306151