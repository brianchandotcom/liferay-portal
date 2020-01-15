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
 * The table class for the LVEntryLocalization.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryLocalizationTable extends Table<LVEntryLocalizationTable> {

	public static final LVEntryLocalizationTable INSTANCE =
		new LVEntryLocalizationTable();

	public final Column<LVEntryLocalizationTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<LVEntryLocalizationTable, Long> headId = createColumn(
		"headId", Long.class, Types.BIGINT);
	public final Column<LVEntryLocalizationTable, Boolean> head = createColumn(
		"head", Boolean.class, Types.BOOLEAN);
	public final Column<LVEntryLocalizationTable, Long> lvEntryLocalizationId =
		createColumn("lvEntryLocalizationId", Long.class, Types.BIGINT);
	public final Column<LVEntryLocalizationTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<LVEntryLocalizationTable, Long> lvEntryId =
		createColumn("lvEntryId", Long.class, Types.BIGINT);
	public final Column<LVEntryLocalizationTable, String> languageId =
		createColumn("languageId", String.class, Types.VARCHAR);
	public final Column<LVEntryLocalizationTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR);
	public final Column<LVEntryLocalizationTable, String> content =
		createColumn("content", String.class, Types.VARCHAR);

	private LVEntryLocalizationTable() {
		super("LVEntryLocalization", LVEntryLocalizationTable::new);
	}

}