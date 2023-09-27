/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MBThreadFlag&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlag
 * @generated
 */
public class MBThreadFlagTable extends BaseTable<MBThreadFlagTable> {

	public static final MBThreadFlagTable INSTANCE = new MBThreadFlagTable();

	public final Column<MBThreadFlagTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MBThreadFlagTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MBThreadFlagTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBThreadFlagTable, Long> threadFlagId = createColumn(
		"threadFlagId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MBThreadFlagTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadFlagTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadFlagTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadFlagTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBThreadFlagTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBThreadFlagTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBThreadFlagTable, Long> threadId = createColumn(
		"threadId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadFlagTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private MBThreadFlagTable() {
		super("MBThreadFlag", MBThreadFlagTable::new);
	}

}