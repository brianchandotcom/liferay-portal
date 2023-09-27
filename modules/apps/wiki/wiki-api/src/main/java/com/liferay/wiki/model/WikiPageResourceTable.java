/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;WikiPageResource&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageResource
 * @generated
 */
public class WikiPageResourceTable extends BaseTable<WikiPageResourceTable> {

	public static final WikiPageResourceTable INSTANCE =
		new WikiPageResourceTable();

	public final Column<WikiPageResourceTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<WikiPageResourceTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WikiPageResourceTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WikiPageResourceTable, Long> resourcePrimKey =
		createColumn(
			"resourcePrimKey", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WikiPageResourceTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WikiPageResourceTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WikiPageResourceTable, Long> nodeId = createColumn(
		"nodeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WikiPageResourceTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private WikiPageResourceTable() {
		super("WikiPageResource", WikiPageResourceTable::new);
	}

}