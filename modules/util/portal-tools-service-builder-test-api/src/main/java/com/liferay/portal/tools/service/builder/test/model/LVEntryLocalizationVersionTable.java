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
 * The table class for the LVEntryLocalizationVersion.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryLocalizationVersionTable
	extends Table<LVEntryLocalizationVersionTable> {

	public static final LVEntryLocalizationVersionTable INSTANCE =
		new LVEntryLocalizationVersionTable();

	public final Column<LVEntryLocalizationVersionTable, Long>
		lvEntryLocalizationVersionId = createColumn(
			"lvEntryLocalizationVersionId", Long.class, Types.BIGINT);
	public final Column<LVEntryLocalizationVersionTable, Integer> version =
		createColumn("version", Integer.class, Types.INTEGER);
	public final Column<LVEntryLocalizationVersionTable, Long>
		lvEntryLocalizationId = createColumn(
			"lvEntryLocalizationId", Long.class, Types.BIGINT);
	public final Column<LVEntryLocalizationVersionTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<LVEntryLocalizationVersionTable, Long> lvEntryId =
		createColumn("lvEntryId", Long.class, Types.BIGINT);
	public final Column<LVEntryLocalizationVersionTable, String> languageId =
		createColumn("languageId", String.class, Types.VARCHAR);
	public final Column<LVEntryLocalizationVersionTable, String> title =
		createColumn("title", String.class, Types.VARCHAR);
	public final Column<LVEntryLocalizationVersionTable, String> content =
		createColumn("content", String.class, Types.VARCHAR);

	private LVEntryLocalizationVersionTable() {
		super(
			"LVEntryLocalizationVersion", LVEntryLocalizationVersionTable::new);
	}

}