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
 * The table class for the RatingsEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RatingsEntryTable extends Table<RatingsEntryTable> {

	public static final RatingsEntryTable INSTANCE = new RatingsEntryTable();

	public final Column<RatingsEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<RatingsEntryTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT);
	public final Column<RatingsEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<RatingsEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<RatingsEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<RatingsEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<RatingsEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<RatingsEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<RatingsEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<RatingsEntryTable, Double> score = createColumn(
		"score", Double.class, Types.DOUBLE);

	private RatingsEntryTable() {
		super("RatingsEntry", RatingsEntryTable::new);
	}

}