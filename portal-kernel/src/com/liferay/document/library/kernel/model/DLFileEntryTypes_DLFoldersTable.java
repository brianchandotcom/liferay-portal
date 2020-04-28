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
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DLFileEntryTypes_DLFolders&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryType
 * @see DLFolder
 * @generated
 */
public class DLFileEntryTypes_DLFoldersTable
	extends BaseTable<DLFileEntryTypes_DLFoldersTable> {

	public static final DLFileEntryTypes_DLFoldersTable INSTANCE =
		new DLFileEntryTypes_DLFoldersTable();

	public final Column<DLFileEntryTypes_DLFoldersTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLFileEntryTypes_DLFoldersTable, Long> fileEntryTypeId =
		createColumn(
			"fileEntryTypeId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLFileEntryTypes_DLFoldersTable, Long> folderId =
		createColumn("folderId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLFileEntryTypes_DLFoldersTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLFileEntryTypes_DLFoldersTable, Boolean> ctChangeType =
		createColumn(
			"ctChangeType", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private DLFileEntryTypes_DLFoldersTable() {
		super(
			"DLFileEntryTypes_DLFolders", DLFileEntryTypes_DLFoldersTable::new);
	}

}