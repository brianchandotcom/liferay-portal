/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.changeset.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ChangesetEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetEntry
 * @generated
 */
public class ChangesetEntryTable extends BaseTable<ChangesetEntryTable> {

	public static final ChangesetEntryTable INSTANCE =
		new ChangesetEntryTable();

	public final Column<ChangesetEntryTable, Long> changesetEntryId =
		createColumn(
			"changesetEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ChangesetEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ChangesetEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ChangesetEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ChangesetEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ChangesetEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ChangesetEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ChangesetEntryTable, Long> changesetCollectionId =
		createColumn(
			"changesetCollectionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ChangesetEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ChangesetEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private ChangesetEntryTable() {
		super("ChangesetEntry", ChangesetEntryTable::new);
	}

}