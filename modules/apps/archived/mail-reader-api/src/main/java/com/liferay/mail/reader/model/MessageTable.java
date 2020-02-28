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

package com.liferay.mail.reader.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Mail_Message&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Message
 * @generated
 */
public class MessageTable extends BaseTable<MessageTable> {

	public static final MessageTable INSTANCE = new MessageTable();

	public final Column<MessageTable, Long> messageId = createColumn(
		"messageId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MessageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MessageTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Long> accountId = createColumn(
		"accountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MessageTable, String> sender = createColumn(
		"sender", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Clob> to = createColumn(
		"to_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Clob> cc = createColumn(
		"cc", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Clob> bcc = createColumn(
		"bcc", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Date> sentDate = createColumn(
		"sentDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MessageTable, String> subject = createColumn(
		"subject", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MessageTable, String> preview = createColumn(
		"preview", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Clob> body = createColumn(
		"body", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<MessageTable, String> flags = createColumn(
		"flags", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MessageTable, Long> remoteMessageId = createColumn(
		"remoteMessageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MessageTable, String> contentType = createColumn(
		"contentType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private MessageTable() {
		super("Mail_Message", MessageTable::new);
	}

}