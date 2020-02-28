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
 * The table class for the &quot;SocialActivity&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivity
 * @generated
 */
public class SocialActivityTable extends BaseTable<SocialActivityTable> {

	public static final SocialActivityTable INSTANCE =
		new SocialActivityTable();

	public final Column<SocialActivityTable, Long> activityId = createColumn(
		"activityId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivityTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> createDate = createColumn(
		"createDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> activitySetId = createColumn(
		"activitySetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> mirrorActivityId =
		createColumn(
			"mirrorActivityId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> parentClassNameId =
		createColumn(
			"parentClassNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> parentClassPK = createColumn(
		"parentClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, String> extraData = createColumn(
		"extraData", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialActivityTable, Long> receiverUserId =
		createColumn(
			"receiverUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private SocialActivityTable() {
		super("SocialActivity", SocialActivityTable::new);
	}

}