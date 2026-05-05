/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;CacheReplicatorEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CacheReplicatorEntry
 * @generated
 */
public class CacheReplicatorEntryTable
	extends BaseTable<CacheReplicatorEntryTable> {

	public static final CacheReplicatorEntryTable INSTANCE =
		new CacheReplicatorEntryTable();

	public final Column<CacheReplicatorEntryTable, Long>
		cacheReplicatorEntryId = createColumn(
			"cacheReplicatorEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CacheReplicatorEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CacheReplicatorEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CacheReplicatorEntryTable() {
		super("CacheReplicatorEntry", CacheReplicatorEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:788061295