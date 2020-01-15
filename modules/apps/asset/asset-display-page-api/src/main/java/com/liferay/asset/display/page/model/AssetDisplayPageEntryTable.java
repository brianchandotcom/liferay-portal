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

package com.liferay.asset.display.page.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the AssetDisplayPageEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetDisplayPageEntryTable
	extends Table<AssetDisplayPageEntryTable> {

	public static final AssetDisplayPageEntryTable INSTANCE =
		new AssetDisplayPageEntryTable();

	public final Column<AssetDisplayPageEntryTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<AssetDisplayPageEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<AssetDisplayPageEntryTable, Long>
		assetDisplayPageEntryId = createColumn(
			"assetDisplayPageEntryId", Long.class, Types.BIGINT);
	public final Column<AssetDisplayPageEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<AssetDisplayPageEntryTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<AssetDisplayPageEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<AssetDisplayPageEntryTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<AssetDisplayPageEntryTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetDisplayPageEntryTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetDisplayPageEntryTable, Long> classNameId =
		createColumn("classNameId", Long.class, Types.BIGINT);
	public final Column<AssetDisplayPageEntryTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT);
	public final Column<AssetDisplayPageEntryTable, Long>
		layoutPageTemplateEntryId = createColumn(
			"layoutPageTemplateEntryId", Long.class, Types.BIGINT);
	public final Column<AssetDisplayPageEntryTable, Integer> type =
		createColumn("type_", Integer.class, Types.INTEGER);
	public final Column<AssetDisplayPageEntryTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT);

	private AssetDisplayPageEntryTable() {
		super("AssetDisplayPageEntry", AssetDisplayPageEntryTable::new);
	}

}