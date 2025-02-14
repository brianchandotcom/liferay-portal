/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DefinedDefaultOrderEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DefinedDefaultOrderEntry
 * @generated
 */
public class DefinedDefaultOrderEntryTable
	extends BaseTable<DefinedDefaultOrderEntryTable> {

	public static final DefinedDefaultOrderEntryTable INSTANCE =
		new DefinedDefaultOrderEntryTable();

	public final Column<DefinedDefaultOrderEntryTable, Long>
		definedDefaultOrderEntryId = createColumn(
			"definedDefaultOrderEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DefinedDefaultOrderEntryTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DefinedDefaultOrderEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DefinedDefaultOrderEntryTable() {
		super("DefinedDefaultOrderEntry", DefinedDefaultOrderEntryTable::new);
	}

}