/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;LVEntryVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryVersion
 * @generated
 */
public class LVEntryVersionTable extends BaseTable<LVEntryVersionTable> {

	public static final LVEntryVersionTable INSTANCE =
		new LVEntryVersionTable();

	public final Column<LVEntryVersionTable, Long> lvEntryVersionId =
		createColumn(
			"lvEntryVersionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LVEntryVersionTable, Integer> version = createColumn(
		"version", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<LVEntryVersionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LVEntryVersionTable, String> defaultLanguageId =
		createColumn(
			"defaultLanguageId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LVEntryVersionTable, Long> lvEntryId = createColumn(
		"lvEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryVersionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryVersionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryVersionTable, String> uniqueGroupKey =
		createColumn(
			"uniqueGroupKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private LVEntryVersionTable() {
		super("LVEntryVersion", LVEntryVersionTable::new);
	}

}