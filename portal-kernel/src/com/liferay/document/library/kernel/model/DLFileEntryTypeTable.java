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

package com.liferay.document.library.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the DLFileEntryType.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryTypeTable extends Table<DLFileEntryTypeTable> {

	public static final DLFileEntryTypeTable INSTANCE =
		new DLFileEntryTypeTable();

	public final Column<DLFileEntryTypeTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<DLFileEntryTypeTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<DLFileEntryTypeTable, Long> fileEntryTypeId =
		createColumn("fileEntryTypeId", Long.class, Types.BIGINT);
	public final Column<DLFileEntryTypeTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<DLFileEntryTypeTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<DLFileEntryTypeTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<DLFileEntryTypeTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<DLFileEntryTypeTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<DLFileEntryTypeTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<DLFileEntryTypeTable, String> fileEntryTypeKey =
		createColumn("fileEntryTypeKey", String.class, Types.VARCHAR);
	public final Column<DLFileEntryTypeTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<DLFileEntryTypeTable, String> description =
		createColumn("description", String.class, Types.VARCHAR);
	public final Column<DLFileEntryTypeTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);

	private DLFileEntryTypeTable() {
		super("DLFileEntryType", DLFileEntryTypeTable::new);
	}

}