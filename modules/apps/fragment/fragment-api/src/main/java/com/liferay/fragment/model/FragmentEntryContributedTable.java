/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;FragmentEntryContributed&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryContributed
 * @generated
 */
public class FragmentEntryContributedTable
	extends BaseTable<FragmentEntryContributedTable> {

	public static final FragmentEntryContributedTable INSTANCE =
		new FragmentEntryContributedTable();

	public final Column<FragmentEntryContributedTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FragmentEntryContributedTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FragmentEntryContributedTable, Long>
		fragmentEntryContributedId = createColumn(
			"fragmentEntryContributedId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FragmentEntryContributedTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryContributedTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryContributedTable, String>
		fragmentEntryKey = createColumn(
			"fragmentEntryKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<FragmentEntryContributedTable, String> css =
		createColumn("css", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryContributedTable, String> html =
		createColumn("html", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryContributedTable, String> js =
		createColumn("js", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryContributedTable, String> configuration =
		createColumn(
			"configuration", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryContributedTable, Integer> type =
		createColumn(
			"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private FragmentEntryContributedTable() {
		super("FragmentEntryContributed", FragmentEntryContributedTable::new);
	}

}