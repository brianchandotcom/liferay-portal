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
 * The table class for the &quot;SocialActivityCounter&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounter
 * @generated
 */
public class SocialActivityCounterTable
	extends BaseTable<SocialActivityCounterTable> {

	public static final SocialActivityCounterTable INSTANCE =
		new SocialActivityCounterTable();

	public final Column<SocialActivityCounterTable, Long> activityCounterId =
		createColumn(
			"activityCounterId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivityCounterTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> ownerType =
		createColumn(
			"ownerType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> currentValue =
		createColumn(
			"currentValue", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> totalValue =
		createColumn(
			"totalValue", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> graceValue =
		createColumn(
			"graceValue", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> startPeriod =
		createColumn(
			"startPeriod", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Integer> endPeriod =
		createColumn(
			"endPeriod", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityCounterTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private SocialActivityCounterTable() {
		super("SocialActivityCounter", SocialActivityCounterTable::new);
	}

}