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

package com.liferay.social.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;SocialActivityLimit&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimit
 * @generated
 */
public class SocialActivityLimitTable
	extends BaseTable<SocialActivityLimitTable> {

	public static final SocialActivityLimitTable INSTANCE =
		new SocialActivityLimitTable();

	public final Column<SocialActivityLimitTable, Long> activityLimitId =
		createColumn(
			"activityLimitId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivityLimitTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, Integer> activityType =
		createColumn(
			"activityType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, String> activityCounterName =
		createColumn(
			"activityCounterName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SocialActivityLimitTable, String> value = createColumn(
		"value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private SocialActivityLimitTable() {
		super("SocialActivityLimit", SocialActivityLimitTable::new);
	}

}