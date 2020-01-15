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

package com.liferay.knowledge.base.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the KBComment.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KBCommentTable extends Table<KBCommentTable> {

	public static final KBCommentTable INSTANCE = new KBCommentTable();

	public final Column<KBCommentTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<KBCommentTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<KBCommentTable, Long> kbCommentId = createColumn(
		"kbCommentId", Long.class, Types.BIGINT);
	public final Column<KBCommentTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<KBCommentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<KBCommentTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<KBCommentTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<KBCommentTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<KBCommentTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<KBCommentTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<KBCommentTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<KBCommentTable, String> content = createColumn(
		"content", String.class, Types.VARCHAR);
	public final Column<KBCommentTable, Integer> userRating = createColumn(
		"userRating", Integer.class, Types.INTEGER);
	public final Column<KBCommentTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);
	public final Column<KBCommentTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);

	private KBCommentTable() {
		super("KBComment", KBCommentTable::new);
	}

}