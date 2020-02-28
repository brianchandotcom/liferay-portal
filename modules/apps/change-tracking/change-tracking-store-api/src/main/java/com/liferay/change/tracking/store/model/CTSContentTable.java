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

package com.liferay.change.tracking.store.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Blob;
import java.sql.Types;

/**
 * The table class for the &quot;CTSContent&quot; database table.
 *
 * @author Shuyang Zhou
 * @see CTSContent
 * @generated
 */
public class CTSContentTable extends BaseTable<CTSContentTable> {

	public static final CTSContentTable INSTANCE = new CTSContentTable();

	public final Column<CTSContentTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTSContentTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTSContentTable, Long> ctsContentId = createColumn(
		"ctsContentId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTSContentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTSContentTable, Long> repositoryId = createColumn(
		"repositoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTSContentTable, String> path = createColumn(
		"path_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CTSContentTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CTSContentTable, Blob> data = createColumn(
		"data_", Blob.class, Types.BLOB, Column.FLAG_DEFAULT);
	public final Column<CTSContentTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTSContentTable, String> storeType = createColumn(
		"storeType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CTSContentTable() {
		super("CTSContent", CTSContentTable::new);
	}

}