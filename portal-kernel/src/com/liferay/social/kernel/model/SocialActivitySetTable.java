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
 * The table class for the SocialActivitySet.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SocialActivitySetTable extends Table<SocialActivitySetTable> {

	public static final SocialActivitySetTable INSTANCE =
		new SocialActivitySetTable();

	public final Column<SocialActivitySetTable, Long> activitySetId =
		createColumn("activitySetId", Long.class, Types.BIGINT);
	public final Column<SocialActivitySetTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<SocialActivitySetTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SocialActivitySetTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SocialActivitySetTable, Long> createDate = createColumn(
		"createDate", Long.class, Types.BIGINT);
	public final Column<SocialActivitySetTable, Long> modifiedDate =
		createColumn("modifiedDate", Long.class, Types.BIGINT);
	public final Column<SocialActivitySetTable, Long> classNameId =
		createColumn("classNameId", Long.class, Types.BIGINT);
	public final Column<SocialActivitySetTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<SocialActivitySetTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER);
	public final Column<SocialActivitySetTable, String> extraData =
		createColumn("extraData", String.class, Types.VARCHAR);
	public final Column<SocialActivitySetTable, Integer> activityCount =
		createColumn("activityCount", Integer.class, Types.INTEGER);

	private SocialActivitySetTable() {
		super("SocialActivitySet", SocialActivitySetTable::new);
	}

}