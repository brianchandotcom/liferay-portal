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

package com.liferay.document.library.content.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Blob;
import java.sql.Types;

/**
 * The table class for the DLContent.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLContentTable extends Table<DLContentTable> {

	public static final DLContentTable INSTANCE = new DLContentTable();

	public final Column<DLContentTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<DLContentTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT);
	public final Column<DLContentTable, Long> contentId = createColumn(
		"contentId", Long.class, Types.BIGINT);
	public final Column<DLContentTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<DLContentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<DLContentTable, Long> repositoryId = createColumn(
		"repositoryId", Long.class, Types.BIGINT);
	public final Column<DLContentTable, String> path = createColumn(
		"path_", String.class, Types.VARCHAR);
	public final Column<DLContentTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR);
	public final Column<DLContentTable, Blob> data = createColumn(
		"data_", Blob.class, Types.BLOB);
	public final Column<DLContentTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT);

	private DLContentTable() {
		super("DLContent", DLContentTable::new);
	}

}