/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ConvertNullEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ConvertNullEntry
 * @generated
 */
public class ConvertNullEntryTable extends BaseTable<ConvertNullEntryTable> {

	public static final ConvertNullEntryTable INSTANCE =
		new ConvertNullEntryTable();

	public final Column<ConvertNullEntryTable, Long> convertNullEntryId =
		createColumn(
			"convertNullEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ConvertNullEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ConvertNullEntryTable() {
		super("ConvertNullEntry", ConvertNullEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1527411632