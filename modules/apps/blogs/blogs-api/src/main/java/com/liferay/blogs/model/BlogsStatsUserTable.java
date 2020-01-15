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

package com.liferay.blogs.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the BlogsStatsUser.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BlogsStatsUserTable extends Table<BlogsStatsUserTable> {

	public static final BlogsStatsUserTable INSTANCE =
		new BlogsStatsUserTable();

	public final Column<BlogsStatsUserTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<BlogsStatsUserTable, Long> statsUserId = createColumn(
		"statsUserId", Long.class, Types.BIGINT);
	public final Column<BlogsStatsUserTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<BlogsStatsUserTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<BlogsStatsUserTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<BlogsStatsUserTable, Integer> entryCount = createColumn(
		"entryCount", Integer.class, Types.INTEGER);
	public final Column<BlogsStatsUserTable, Date> lastPostDate = createColumn(
		"lastPostDate", Date.class, Types.TIMESTAMP);
	public final Column<BlogsStatsUserTable, Integer> ratingsTotalEntries =
		createColumn("ratingsTotalEntries", Integer.class, Types.INTEGER);
	public final Column<BlogsStatsUserTable, Double> ratingsTotalScore =
		createColumn("ratingsTotalScore", Double.class, Types.DOUBLE);
	public final Column<BlogsStatsUserTable, Double> ratingsAverageScore =
		createColumn("ratingsAverageScore", Double.class, Types.DOUBLE);

	private BlogsStatsUserTable() {
		super("BlogsStatsUser", BlogsStatsUserTable::new);
	}

}