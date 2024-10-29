/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;IndexEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntry
 * @generated
 */
public class IndexEntryTable extends BaseTable<IndexEntryTable> {

	public static final IndexEntryTable INSTANCE = new IndexEntryTable();

	public final Column<IndexEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<IndexEntryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<IndexEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<IndexEntryTable, Long> indexEntryId = createColumn(
		"indexEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<IndexEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<IndexEntryTable, Long> ownerId = createColumn(
		"ownerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<IndexEntryTable, Integer> ownerType = createColumn(
		"ownerType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<IndexEntryTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<IndexEntryTable, String> portletId = createColumn(
		"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private IndexEntryTable() {
		super("IndexEntry", IndexEntryTable::new);
	}

}