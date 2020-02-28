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

import java.util.Date;

/**
 * The table class for the &quot;UserTrackerPath&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPath
 * @generated
 */
public class UserTrackerPathTable extends BaseTable<UserTrackerPathTable> {

	public static final UserTrackerPathTable INSTANCE =
		new UserTrackerPathTable();

	public final Column<UserTrackerPathTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<UserTrackerPathTable, Long> userTrackerPathId =
		createColumn(
			"userTrackerPathId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserTrackerPathTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserTrackerPathTable, Long> userTrackerId =
		createColumn(
			"userTrackerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserTrackerPathTable, String> path = createColumn(
		"path_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTrackerPathTable, Date> pathDate = createColumn(
		"pathDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private UserTrackerPathTable() {
		super("UserTrackerPath", UserTrackerPathTable::new);
	}

}