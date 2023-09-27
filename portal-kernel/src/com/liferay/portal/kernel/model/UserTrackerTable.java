/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;UserTracker&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UserTracker
 * @generated
 */
public class UserTrackerTable extends BaseTable<UserTrackerTable> {

	public static final UserTrackerTable INSTANCE = new UserTrackerTable();

	public final Column<UserTrackerTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<UserTrackerTable, Long> userTrackerId = createColumn(
		"userTrackerId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserTrackerTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserTrackerTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserTrackerTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<UserTrackerTable, String> sessionId = createColumn(
		"sessionId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTrackerTable, String> remoteAddr = createColumn(
		"remoteAddr", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTrackerTable, String> remoteHost = createColumn(
		"remoteHost", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTrackerTable, String> userAgent = createColumn(
		"userAgent", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private UserTrackerTable() {
		super("UserTracker", UserTrackerTable::new);
	}

}