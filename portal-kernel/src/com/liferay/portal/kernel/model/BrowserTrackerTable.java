/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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