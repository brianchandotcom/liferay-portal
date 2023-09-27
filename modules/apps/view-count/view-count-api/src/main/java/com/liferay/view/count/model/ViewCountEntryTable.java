/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.view.count.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ViewCountEntry&quot; database table.
 *
 * @author Preston Crary
 * @see ViewCountEntry
 * @generated
 */
public class ViewCountEntryTable extends BaseTable<ViewCountEntryTable> {

	public static final ViewCountEntryTable INSTANCE =
		new ViewCountEntryTable();

	public final Column<ViewCountEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ViewCountEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ViewCountEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ViewCountEntryTable, Long> viewCount = createColumn(
		"viewCount", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private ViewCountEntryTable() {
		super("ViewCountEntry", ViewCountEntryTable::new);
	}

}