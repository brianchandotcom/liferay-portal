/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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