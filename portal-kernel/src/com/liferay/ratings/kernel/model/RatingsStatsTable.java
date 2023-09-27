/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;RatingsStats&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsStats
 * @generated
 */
public class RatingsStatsTable extends BaseTable<RatingsStatsTable> {

	public static final RatingsStatsTable INSTANCE = new RatingsStatsTable();

	public final Column<RatingsStatsTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RatingsStatsTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RatingsStatsTable, Long> statsId = createColumn(
		"statsId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RatingsStatsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RatingsStatsTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RatingsStatsTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RatingsStatsTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RatingsStatsTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RatingsStatsTable, Integer> totalEntries = createColumn(
		"totalEntries", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<RatingsStatsTable, Double> totalScore = createColumn(
		"totalScore", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<RatingsStatsTable, Double> averageScore = createColumn(
		"averageScore", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private RatingsStatsTable() {
		super("RatingsStats", RatingsStatsTable::new);
	}

}