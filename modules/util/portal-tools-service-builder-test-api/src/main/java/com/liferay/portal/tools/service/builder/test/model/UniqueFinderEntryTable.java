/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;UniqueFinderEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UniqueFinderEntry
 * @generated
 */
public class UniqueFinderEntryTable extends BaseTable<UniqueFinderEntryTable> {

	public static final UniqueFinderEntryTable INSTANCE =
		new UniqueFinderEntryTable();

	public final Column<UniqueFinderEntryTable, Long> uniqueFinderEntryId =
		createColumn(
			"uniqueFinderEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<UniqueFinderEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<UniqueFinderEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private UniqueFinderEntryTable() {
		super("UniqueFinderEntry", UniqueFinderEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:64945006