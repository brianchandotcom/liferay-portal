/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;XMLSitemapRegenerationEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see XMLSitemapRegenerationEntry
 * @generated
 */
public class XMLSitemapRegenerationEntryTable
	extends BaseTable<XMLSitemapRegenerationEntryTable> {

	public static final XMLSitemapRegenerationEntryTable INSTANCE =
		new XMLSitemapRegenerationEntryTable();

	public final Column<XMLSitemapRegenerationEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<XMLSitemapRegenerationEntryTable, Long>
		xmlSitemapRegenerationEntryId = createColumn(
			"xmlSitemapRegenerationEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<XMLSitemapRegenerationEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<XMLSitemapRegenerationEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<XMLSitemapRegenerationEntryTable, String> assetTypeKey =
		createColumn(
			"assetTypeKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private XMLSitemapRegenerationEntryTable() {
		super(
			"XMLSitemapRegenerationEntry",
			XMLSitemapRegenerationEntryTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1754799663