/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;KeywordsEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KeywordsEntry
 * @generated
 */
public class KeywordsEntryTable extends BaseTable<KeywordsEntryTable> {

	public static final KeywordsEntryTable INSTANCE = new KeywordsEntryTable();

	public final Column<KeywordsEntryTable, Long> keywordsEntryId =
		createColumn(
			"keywordsEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KeywordsEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KeywordsEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private KeywordsEntryTable() {
		super("KeywordsEntry", KeywordsEntryTable::new);
	}

}