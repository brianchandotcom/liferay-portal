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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the UserTracker.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserTrackerTable extends Table<UserTrackerTable> {

	public static final UserTrackerTable INSTANCE = new UserTrackerTable();

	public final Column<UserTrackerTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<UserTrackerTable, Long> userTrackerId = createColumn(
		"userTrackerId", Long.class, Types.BIGINT);
	public final Column<UserTrackerTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<UserTrackerTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<UserTrackerTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<UserTrackerTable, String> sessionId = createColumn(
		"sessionId", String.class, Types.VARCHAR);
	public final Column<UserTrackerTable, String> remoteAddr = createColumn(
		"remoteAddr", String.class, Types.VARCHAR);
	public final Column<UserTrackerTable, String> remoteHost = createColumn(
		"remoteHost", String.class, Types.VARCHAR);
	public final Column<UserTrackerTable, String> userAgent = createColumn(
		"userAgent", String.class, Types.VARCHAR);

	private UserTrackerTable() {
		super("UserTracker", UserTrackerTable::new);
	}

}