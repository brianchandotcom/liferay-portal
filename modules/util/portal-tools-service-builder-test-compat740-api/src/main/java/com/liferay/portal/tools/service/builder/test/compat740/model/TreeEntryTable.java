/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;TreeEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see TreeEntry
 * @generated
 */
public class TreeEntryTable extends BaseTable<TreeEntryTable> {

	public static final TreeEntryTable INSTANCE = new TreeEntryTable();

	public final Column<TreeEntryTable, Long> treeEntryId = createColumn(
		"treeEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<TreeEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TreeEntryTable, Long> parentTreeEntryId = createColumn(
		"parentTreeEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TreeEntryTable, Long> leftTreeEntryId = createColumn(
		"leftTreeEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TreeEntryTable, Long> rightTreeEntryId = createColumn(
		"rightTreeEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private TreeEntryTable() {
		super("TreeEntry", TreeEntryTable::new);
	}

}
// SB-Hash:-128277068