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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the LVEntryVersion.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryVersionTable extends Table<LVEntryVersionTable> {

	public static final LVEntryVersionTable INSTANCE =
		new LVEntryVersionTable();

	public final Column<LVEntryVersionTable, Long> lvEntryVersionId =
		createColumn("lvEntryVersionId", Long.class, Types.BIGINT);
	public final Column<LVEntryVersionTable, Integer> version = createColumn(
		"version", Integer.class, Types.INTEGER);
	public final Column<LVEntryVersionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<LVEntryVersionTable, String> defaultLanguageId =
		createColumn("defaultLanguageId", String.class, Types.VARCHAR);
	public final Column<LVEntryVersionTable, Long> lvEntryId = createColumn(
		"lvEntryId", Long.class, Types.BIGINT);
	public final Column<LVEntryVersionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<LVEntryVersionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<LVEntryVersionTable, String> uniqueGroupKey =
		createColumn("uniqueGroupKey", String.class, Types.VARCHAR);

	private LVEntryVersionTable() {
		super("LVEntryVersion", LVEntryVersionTable::new);
	}

}