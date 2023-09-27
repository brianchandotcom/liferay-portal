/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SharingEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntry
 * @generated
 */
public class SharingEntryTable extends BaseTable<SharingEntryTable> {

	public static final SharingEntryTable INSTANCE = new SharingEntryTable();

	public final Column<SharingEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Long> sharingEntryId = createColumn(
		"sharingEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SharingEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Long> toUserId = createColumn(
		"toUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Boolean> shareable = createColumn(
		"shareable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Long> actionIds = createColumn(
		"actionIds", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SharingEntryTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private SharingEntryTable() {
		super("SharingEntry", SharingEntryTable::new);
	}

}