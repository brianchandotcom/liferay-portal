/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;CacheMissEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CacheMissEntry
 * @generated
 */
public class CacheMissEntryTable extends BaseTable<CacheMissEntryTable> {

	public static final CacheMissEntryTable INSTANCE =
		new CacheMissEntryTable();

	public final Column<CacheMissEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CacheMissEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CacheMissEntryTable, Long> cacheMissEntryId =
		createColumn(
			"cacheMissEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);

	private CacheMissEntryTable() {
		super("CacheMissEntry", CacheMissEntryTable::new);
	}

}