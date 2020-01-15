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

package com.liferay.ratings.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the RatingsStats.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RatingsStatsTable extends Table<RatingsStatsTable> {

	public static final RatingsStatsTable INSTANCE = new RatingsStatsTable();

	public final Column<RatingsStatsTable, Long> statsId = createColumn(
		"statsId", Long.class, Types.BIGINT);
	public final Column<RatingsStatsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<RatingsStatsTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<RatingsStatsTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<RatingsStatsTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<RatingsStatsTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<RatingsStatsTable, Integer> totalEntries = createColumn(
		"totalEntries", Integer.class, Types.INTEGER);
	public final Column<RatingsStatsTable, Double> totalScore = createColumn(
		"totalScore", Double.class, Types.DOUBLE);
	public final Column<RatingsStatsTable, Double> averageScore = createColumn(
		"averageScore", Double.class, Types.DOUBLE);

	private RatingsStatsTable() {
		super("RatingsStats", RatingsStatsTable::new);
	}

}