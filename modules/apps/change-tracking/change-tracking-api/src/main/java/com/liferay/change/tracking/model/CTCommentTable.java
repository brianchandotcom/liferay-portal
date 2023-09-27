/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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