/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MBMessage&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessage
 * @generated
 */
public class MBMessageTable extends BaseTable<MBMessageTable> {

	public static final MBMessageTable INSTANCE = new MBMessageTable();

	public final Column<MBMessageTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MBMessageTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MBMessageTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> messageId = createColumn(
		"messageId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MBMessageTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> categoryId = createColumn(
		"categoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> threadId = createColumn(
		"threadId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> rootMessageId = createColumn(
		"rootMessageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> parentMessageId = createColumn(
		"parentMessageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, String> subject = createColumn(
		"subject", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, String> urlSubject = createColumn(
		"urlSubject", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Clob> body = createColumn(
		"body", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, String> format = createColumn(
		"format", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Boolean> anonymous = createColumn(
		"anonymous", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Double> priority = createColumn(
		"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Boolean> allowPingbacks = createColumn(
		"allowPingbacks", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Boolean> answer = createColumn(
		"answer", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, String> statusByUserName = createColumn(
		"statusByUserName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBMessageTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private MBMessageTable() {
		super("MBMessage", MBMessageTable::new);
	}

}