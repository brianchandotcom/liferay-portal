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

import java.sql.Types;

/**
 * The table class for the &quot;Mail_Attachment&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Attachment
 * @generated
 */
public class AttachmentTable extends BaseTable<AttachmentTable> {

	public static final AttachmentTable INSTANCE = new AttachmentTable();

	public final Column<AttachmentTable, Long> attachmentId = createColumn(
		"attachmentId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AttachmentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> accountId = createColumn(
		"accountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> messageId = createColumn(
		"messageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, String> contentPath = createColumn(
		"contentPath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, String> fileName = createColumn(
		"fileName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AttachmentTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AttachmentTable() {
		super("Mail_Attachment", AttachmentTable::new);
	}

}