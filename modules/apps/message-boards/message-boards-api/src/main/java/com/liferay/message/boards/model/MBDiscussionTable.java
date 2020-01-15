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

package com.liferay.message.boards.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the MBDiscussion.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBDiscussionTable extends Table<MBDiscussionTable> {

	public static final MBDiscussionTable INSTANCE = new MBDiscussionTable();

	public final Column<MBDiscussionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<MBDiscussionTable, Long> discussionId = createColumn(
		"discussionId", Long.class, Types.BIGINT);
	public final Column<MBDiscussionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<MBDiscussionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<MBDiscussionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<MBDiscussionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<MBDiscussionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<MBDiscussionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<MBDiscussionTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<MBDiscussionTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<MBDiscussionTable, Long> threadId = createColumn(
		"threadId", Long.class, Types.BIGINT);
	public final Column<MBDiscussionTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);

	private MBDiscussionTable() {
		super("MBDiscussion", MBDiscussionTable::new);
	}

}