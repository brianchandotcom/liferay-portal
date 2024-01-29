/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AutoEscapeEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AutoEscapeEntry
 * @generated
 */
public class AutoEscapeEntryTable extends BaseTable<AutoEscapeEntryTable> {

	public static final AutoEscapeEntryTable INSTANCE =
		new AutoEscapeEntryTable();

	public final Column<AutoEscapeEntryTable, Long> autoEscapeEntryId =
		createColumn(
			"autoEscapeEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AutoEscapeEntryTable, String> autoEscapeEnabledColumn =
		createColumn(
			"autoEscapeEnabledColumn", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<AutoEscapeEntryTable, String> autoEscapeDisabledColumn =
		createColumn(
			"autoEscapeDisabledColumn", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private AutoEscapeEntryTable() {
		super("AutoEscapeEntry", AutoEscapeEntryTable::new);
	}

}