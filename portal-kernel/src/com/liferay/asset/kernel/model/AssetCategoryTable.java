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

package com.liferay.asset.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AssetCategory&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategory
 * @generated
 */
public class AssetCategoryTable extends BaseTable<AssetCategoryTable> {

	public static final AssetCategoryTable INSTANCE = new AssetCategoryTable();

	public final Column<AssetCategoryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetCategoryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetCategoryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Long> categoryId = createColumn(
		"categoryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetCategoryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Long> parentCategoryId =
		createColumn(
			"parentCategoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Clob> title = createColumn(
		"title", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Clob> description = createColumn(
		"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Long> vocabularyId = createColumn(
		"vocabularyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetCategoryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private AssetCategoryTable() {
		super("AssetCategory", AssetCategoryTable::new);
	}

}