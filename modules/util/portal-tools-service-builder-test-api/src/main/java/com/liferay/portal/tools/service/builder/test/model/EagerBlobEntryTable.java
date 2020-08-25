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
 * The table class for the &quot;EagerBlobEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntry
 * @generated
 */
public class EagerBlobEntryTable extends BaseTable<EagerBlobEntryTable> {

	public static final EagerBlobEntryTable INSTANCE =
		new EagerBlobEntryTable();

	public final Column<EagerBlobEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<EagerBlobEntryTable, Long> eagerBlobEntryId =
		createColumn(
			"eagerBlobEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<EagerBlobEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<EagerBlobEntryTable, Blob> blob = createColumn(
		"blob_", Blob.class, Types.BLOB, Column.FLAG_DEFAULT);

	private EagerBlobEntryTable() {
		super("EagerBlobEntry", EagerBlobEntryTable::new);
	}

}