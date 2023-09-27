/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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