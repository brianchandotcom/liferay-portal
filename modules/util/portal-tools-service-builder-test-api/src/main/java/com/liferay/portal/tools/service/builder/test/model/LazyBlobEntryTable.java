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

import java.sql.Blob;
import java.sql.Types;

/**
 * The table class for the &quot;LazyBlobEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntry
 * @generated
 */
public class LazyBlobEntryTable extends BaseTable<LazyBlobEntryTable> {

	public static final LazyBlobEntryTable INSTANCE = new LazyBlobEntryTable();

	public final Column<LazyBlobEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LazyBlobEntryTable, Long> lazyBlobEntryId =
		createColumn(
			"lazyBlobEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LazyBlobEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LazyBlobEntryTable, Blob> blob1 = createColumn(
		"blob1", Blob.class, Types.BLOB, Column.FLAG_DEFAULT);
	public final Column<LazyBlobEntryTable, Blob> blob2 = createColumn(
		"blob2", Blob.class, Types.BLOB, Column.FLAG_DEFAULT);

	private LazyBlobEntryTable() {
		super("LazyBlobEntry", LazyBlobEntryTable::new);
	}

}