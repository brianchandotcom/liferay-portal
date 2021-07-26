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

package com.liferay.remote.app.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;RemoteCustomElementEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteCustomElementEntry
 * @generated
 */
public class RemoteCustomElementEntryTable
	extends BaseTable<RemoteCustomElementEntryTable> {

	public static final RemoteCustomElementEntryTable INSTANCE =
		new RemoteCustomElementEntryTable();

	public final Column<RemoteCustomElementEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RemoteCustomElementEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteCustomElementEntryTable, Long>
		remoteCustomElementEntryId = createColumn(
			"remoteCustomElementEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<RemoteCustomElementEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RemoteCustomElementEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RemoteCustomElementEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteCustomElementEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RemoteCustomElementEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RemoteCustomElementEntryTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteCustomElementEntryTable, String> tagName =
		createColumn(
			"tagName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteCustomElementEntryTable, String> url =
		createColumn("url", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RemoteCustomElementEntryTable() {
		super("RemoteCustomElementEntry", RemoteCustomElementEntryTable::new);
	}

}