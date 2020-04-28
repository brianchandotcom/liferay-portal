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
 * The table class for the &quot;DLFileEntryMetadata&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadata
 * @generated
 */
public class DLFileEntryMetadataTable
	extends BaseTable<DLFileEntryMetadataTable> {

	public static final DLFileEntryMetadataTable INSTANCE =
		new DLFileEntryMetadataTable();

	public final Column<DLFileEntryMetadataTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DLFileEntryMetadataTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLFileEntryMetadataTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLFileEntryMetadataTable, Long> fileEntryMetadataId =
		createColumn(
			"fileEntryMetadataId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DLFileEntryMetadataTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileEntryMetadataTable, Long> DDMStorageId =
		createColumn(
			"DDMStorageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileEntryMetadataTable, Long> DDMStructureId =
		createColumn(
			"DDMStructureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileEntryMetadataTable, Long> fileEntryId =
		createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileEntryMetadataTable, Long> fileVersionId =
		createColumn(
			"fileVersionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DLFileEntryMetadataTable() {
		super("DLFileEntryMetadata", DLFileEntryMetadataTable::new);
	}

}