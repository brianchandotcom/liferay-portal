/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public final Column<SocialActivityAchievementTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SocialActivityAchievementTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
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