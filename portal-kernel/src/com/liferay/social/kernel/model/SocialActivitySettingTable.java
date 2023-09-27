/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;SocialActivitySetting&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySetting
 * @generated
 */
public class SocialActivitySettingTable
	extends BaseTable<SocialActivitySettingTable> {

	public static final SocialActivitySettingTable INSTANCE =
		new SocialActivitySettingTable();

	public final Column<SocialActivitySettingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SocialActivitySettingTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivitySettingTable, Long> activitySettingId =
		createColumn(
			"activitySettingId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialActivitySettingTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySettingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySettingTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySettingTable, Integer> activityType =
		createColumn(
			"activityType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySettingTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialActivitySettingTable, String> value =
		createColumn("value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private SocialActivitySettingTable() {
		super("SocialActivitySetting", SocialActivitySettingTable::new);
	}

}