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

package com.liferay.change.tracking.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CTComment&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTComment
 * @generated
 */
public class CTCommentTable extends BaseTable<CTCommentTable> {

	public static final CTCommentTable INSTANCE = new CTCommentTable();

	public final Column<CTCommentTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTCommentTable, Long> ctCommentId = createColumn(
		"ctCommentId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTCommentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTCommentTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTCommentTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CTCommentTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CTCommentTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTCommentTable, Long> ctEntryId = createColumn(
		"ctEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTCommentTable, Clob> value = createColumn(
		"value", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CTCommentTable() {
		super("CTComment", CTCommentTable::new);
	}

}