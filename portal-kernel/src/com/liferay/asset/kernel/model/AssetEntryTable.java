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
 * The table class for the &quot;AssetEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntry
 * @generated
 */
public class AssetEntryTable extends BaseTable<AssetEntryTable> {

	public static final AssetEntryTable INSTANCE = new AssetEntryTable();

	public final Column<AssetEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetEntryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntryTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, String> classUuid = createColumn(
		"classUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Long> classTypeId = createColumn(
		"classTypeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Boolean> listable = createColumn(
		"listable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Boolean> visible = createColumn(
		"visible", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Date> startDate = createColumn(
		"startDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Date> endDate = createColumn(
		"endDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Date> publishDate = createColumn(
		"publishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, String> mimeType = createColumn(
		"mimeType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Clob> description = createColumn(
		"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Clob> summary = createColumn(
		"summary", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, String> url = createColumn(
		"url", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, String> layoutUuid = createColumn(
		"layoutUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Integer> height = createColumn(
		"height", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Integer> width = createColumn(
		"width", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AssetEntryTable, Double> priority = createColumn(
		"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private AssetEntryTable() {
		super("AssetEntry", AssetEntryTable::new);
	}

}