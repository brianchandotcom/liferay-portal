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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;BrowserTracker&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see BrowserTracker
 * @generated
 */
public class BrowserTrackerTable extends BaseTable<BrowserTrackerTable> {

	public static final BrowserTrackerTable INSTANCE =
		new BrowserTrackerTable();

	public final Column<BrowserTrackerTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BrowserTrackerTable, Long> browserTrackerId =
		createColumn(
			"browserTrackerId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<BrowserTrackerTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BrowserTrackerTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BrowserTrackerTable, Long> browserKey = createColumn(
		"browserKey", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private BrowserTrackerTable() {
		super("BrowserTracker", BrowserTrackerTable::new);
	}

}