/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;LVEntryLocalizationVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationVersion
 * @generated
 */
public class LVEntryLocalizationVersionTable
	extends BaseTable<LVEntryLocalizationVersionTable> {

	public static final LVEntryLocalizationVersionTable INSTANCE =
		new LVEntryLocalizationVersionTable();

	public final Column<LVEntryLocalizationVersionTable, Long>
		lvEntryLocalizationVersionId = createColumn(
			"lvEntryLocalizationVersionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<LVEntryLocalizationVersionTable, Integer> version =
		createColumn(
			"version", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<LVEntryLocalizationVersionTable, Long>
		lvEntryLocalizationId = createColumn(
			"lvEntryLocalizationId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<LVEntryLocalizationVersionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryLocalizationVersionTable, Long> lvEntryId =
		createColumn(
			"lvEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryLocalizationVersionTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LVEntryLocalizationVersionTable, String> title =
		createColumn("title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LVEntryLocalizationVersionTable, String> content =
		createColumn(
			"content", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private LVEntryLocalizationVersionTable() {
		super(
			"LVEntryLocalizationVersion", LVEntryLocalizationVersionTable::new);
	}

}