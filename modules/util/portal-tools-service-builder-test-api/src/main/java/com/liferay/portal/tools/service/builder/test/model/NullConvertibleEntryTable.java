/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;NullConvertibleEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see NullConvertibleEntry
 * @generated
 */
public class NullConvertibleEntryTable
	extends BaseTable<NullConvertibleEntryTable> {

	public static final NullConvertibleEntryTable INSTANCE =
		new NullConvertibleEntryTable();

	public final Column<NullConvertibleEntryTable, Long>
		nullConvertibleEntryId = createColumn(
			"nullConvertibleEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NullConvertibleEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private NullConvertibleEntryTable() {
		super("NullConvertibleEntry", NullConvertibleEntryTable::new);
	}

}