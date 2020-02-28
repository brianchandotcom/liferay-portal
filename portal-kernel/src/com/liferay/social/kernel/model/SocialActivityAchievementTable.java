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
 * The table class for the &quot;SocialActivityAchievement&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityAchievement
 * @generated
 */
public class SocialActivityAchievementTable
	extends BaseTable<SocialActivityAchievementTable> {

	public static final SocialActivityAchievementTable INSTANCE =
		new SocialActivityAchievementTable();

	public final Column<SocialActivityAchievementTable, Long>
		activityAchievementId = createColumn(
			"activityAchievementId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SocialActivityAchievementTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityAchievementTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityAchievementTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityAchievementTable, Long> createDate =
		createColumn(
			"createDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivityAchievementTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialActivityAchievementTable, Boolean> firstInGroup =
		createColumn(
			"firstInGroup", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private SocialActivityAchievementTable() {
		super("SocialActivityAchievement", SocialActivityAchievementTable::new);
	}

}