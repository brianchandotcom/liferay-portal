/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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