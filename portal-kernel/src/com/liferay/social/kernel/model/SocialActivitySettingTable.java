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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the SocialActivitySetting.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivitySettingTable
	extends Table<SocialActivitySettingTable> {

	public static final SocialActivitySettingTable INSTANCE =
		new SocialActivitySettingTable();

	public final Column<SocialActivitySettingTable, Long> activitySettingId =
		createColumn("activitySettingId", Long.class, Types.BIGINT);
	public final Column<SocialActivitySettingTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<SocialActivitySettingTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<SocialActivitySettingTable, Long> classNameId =
		createColumn("classNameId", Long.class, Types.BIGINT);
	public final Column<SocialActivitySettingTable, Integer> activityType =
		createColumn("activityType", Integer.class, Types.INTEGER);
	public final Column<SocialActivitySettingTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<SocialActivitySettingTable, String> value =
		createColumn("value", String.class, Types.VARCHAR);

	private SocialActivitySettingTable() {
		super("SocialActivitySetting", SocialActivitySettingTable::new);
	}

}