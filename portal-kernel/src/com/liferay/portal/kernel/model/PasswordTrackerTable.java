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
 * The table class for the &quot;PasswordTracker&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTracker
 * @generated
 */
public class PasswordTrackerTable extends BaseTable<PasswordTrackerTable> {

	public static final PasswordTrackerTable INSTANCE =
		new PasswordTrackerTable();

	public final Column<PasswordTrackerTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PasswordTrackerTable, Long> passwordTrackerId =
		createColumn(
			"passwordTrackerId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PasswordTrackerTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordTrackerTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordTrackerTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PasswordTrackerTable, String> password = createColumn(
		"password_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private PasswordTrackerTable() {
		super("PasswordTracker", PasswordTrackerTable::new);
	}

}