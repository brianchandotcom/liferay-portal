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
 * The table class for the &quot;UndefinedDefaultOrderEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UndefinedDefaultOrderEntry
 * @generated
 */
public class UndefinedDefaultOrderEntryTable
	extends BaseTable<UndefinedDefaultOrderEntryTable> {

	public static final UndefinedDefaultOrderEntryTable INSTANCE =
		new UndefinedDefaultOrderEntryTable();

	public final Column<UndefinedDefaultOrderEntryTable, Long>
		undefinedDefaultOrderEntryId = createColumn(
			"undefinedDefaultOrderEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<UndefinedDefaultOrderEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<UndefinedDefaultOrderEntryTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private UndefinedDefaultOrderEntryTable() {
		super(
			"UndefinedDefaultOrderEntry", UndefinedDefaultOrderEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1067070215