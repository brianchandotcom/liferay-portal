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

package com.liferay.polls.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;PollsVote&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PollsVote
 * @generated
 */
public class PollsVoteTable extends BaseTable<PollsVoteTable> {

	public static final PollsVoteTable INSTANCE = new PollsVoteTable();

	public final Column<PollsVoteTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PollsVoteTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> voteId = createColumn(
		"voteId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PollsVoteTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> questionId = createColumn(
		"questionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> choiceId = createColumn(
		"choiceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Date> voteDate = createColumn(
		"voteDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private PollsVoteTable() {
		super("PollsVote", PollsVoteTable::new);
	}

}