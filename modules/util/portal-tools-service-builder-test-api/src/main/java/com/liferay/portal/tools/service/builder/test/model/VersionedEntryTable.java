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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;VersionedEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntry
 * @generated
 */
public class VersionedEntryTable extends BaseTable<VersionedEntryTable> {

	public static final VersionedEntryTable INSTANCE =
		new VersionedEntryTable();

	public final Column<VersionedEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<VersionedEntryTable, Long> headId = createColumn(
		"headId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VersionedEntryTable, Boolean> head = createColumn(
		"head", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<VersionedEntryTable, Long> versionedEntryId =
		createColumn(
			"versionedEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<VersionedEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private VersionedEntryTable() {
		super("VersionedEntry", VersionedEntryTable::new);
	}

}