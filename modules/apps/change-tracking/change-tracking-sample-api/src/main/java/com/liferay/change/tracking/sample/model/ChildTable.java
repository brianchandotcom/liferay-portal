/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Child&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Child
 * @generated
 */
public class ChildTable extends BaseTable<ChildTable> {

	public static final ChildTable INSTANCE = new ChildTable();

	public final Column<ChildTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ChildTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ChildTable, Long> childId = createColumn(
		"childId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ChildTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ChildTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ChildTable, Long> grandParentId = createColumn(
		"grandParentId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ChildTable, Long> parentChildId = createColumn(
		"parentChildId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ChildTable, String> parentName = createColumn(
		"parentName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ChildTable() {
		super("Child", ChildTable::new);
	}

}