/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;GrandParent&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see GrandParent
 * @generated
 */
public class GrandParentTable extends BaseTable<GrandParentTable> {

	public static final GrandParentTable INSTANCE = new GrandParentTable();

	public final Column<GrandParentTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<GrandParentTable, Long> grandParentId = createColumn(
		"grandParentId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<GrandParentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<GrandParentTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<GrandParentTable, Long> parentGrandParentId =
		createColumn(
			"parentGrandParentId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private GrandParentTable() {
		super("GrandParent", GrandParentTable::new);
	}

}