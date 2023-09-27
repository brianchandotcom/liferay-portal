/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;CacheFieldEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CacheFieldEntry
 * @generated
 */
public class CacheFieldEntryTable extends BaseTable<CacheFieldEntryTable> {

	public static final CacheFieldEntryTable INSTANCE =
		new CacheFieldEntryTable();

	public final Column<CacheFieldEntryTable, Long> cacheFieldEntryId =
		createColumn(
			"cacheFieldEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CacheFieldEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CacheFieldEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CacheFieldEntryTable() {
		super("CacheFieldEntry", CacheFieldEntryTable::new);
	}

}