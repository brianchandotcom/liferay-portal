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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the AssetTag.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetTagTable extends Table<AssetTagTable> {

	public static final AssetTagTable INSTANCE = new AssetTagTable();

	public final Column<AssetTagTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<AssetTagTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT);
	public final Column<AssetTagTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<AssetTagTable, Long> tagId = createColumn(
		"tagId", Long.class, Types.BIGINT);
	public final Column<AssetTagTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<AssetTagTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<AssetTagTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<AssetTagTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<AssetTagTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetTagTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetTagTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<AssetTagTable, Integer> assetCount = createColumn(
		"assetCount", Integer.class, Types.INTEGER);
	public final Column<AssetTagTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);

	private AssetTagTable() {
		super("AssetTag", AssetTagTable::new);
	}

}