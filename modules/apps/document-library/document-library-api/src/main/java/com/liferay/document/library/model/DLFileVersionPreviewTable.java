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

package com.liferay.document.library.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DLFileVersionPreview&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPreview
 * @generated
 */
public class DLFileVersionPreviewTable
	extends BaseTable<DLFileVersionPreviewTable> {

	public static final DLFileVersionPreviewTable INSTANCE =
		new DLFileVersionPreviewTable();

	public final Column<DLFileVersionPreviewTable, Long>
		dlFileVersionPreviewId = createColumn(
			"dlFileVersionPreviewId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DLFileVersionPreviewTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileVersionPreviewTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileVersionPreviewTable, Long> fileEntryId =
		createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileVersionPreviewTable, Long> fileVersionId =
		createColumn(
			"fileVersionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileVersionPreviewTable, Integer> previewStatus =
		createColumn(
			"previewStatus", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private DLFileVersionPreviewTable() {
		super("DLFileVersionPreview", DLFileVersionPreviewTable::new);
	}

}