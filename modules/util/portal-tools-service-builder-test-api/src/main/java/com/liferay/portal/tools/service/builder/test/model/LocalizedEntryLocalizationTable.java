/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;LocalizedEntryLocalization&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntryLocalization
 * @generated
 */
public class LocalizedEntryLocalizationTable
	extends BaseTable<LocalizedEntryLocalizationTable> {

	public static final LocalizedEntryLocalizationTable INSTANCE =
		new LocalizedEntryLocalizationTable();

	public final Column<LocalizedEntryLocalizationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LocalizedEntryLocalizationTable, Long>
		localizedEntryLocalizationId = createColumn(
			"localizedEntryLocalizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<LocalizedEntryLocalizationTable, Long>
		localizedEntryId = createColumn(
			"localizedEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LocalizedEntryLocalizationTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LocalizedEntryLocalizationTable, String> title =
		createColumn("title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LocalizedEntryLocalizationTable, String> content =
		createColumn(
			"content", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private LocalizedEntryLocalizationTable() {
		super(
			"LocalizedEntryLocalization", LocalizedEntryLocalizationTable::new);
	}

}