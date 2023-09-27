/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;LocalizedEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntry
 * @generated
 */
public class LocalizedEntryTable extends BaseTable<LocalizedEntryTable> {

	public static final LocalizedEntryTable INSTANCE =
		new LocalizedEntryTable();

	public final Column<LocalizedEntryTable, String> defaultLanguageId =
		createColumn(
			"defaultLanguageId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LocalizedEntryTable, Long> localizedEntryId =
		createColumn(
			"localizedEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);

	private LocalizedEntryTable() {
		super("LocalizedEntry", LocalizedEntryTable::new);
	}

}