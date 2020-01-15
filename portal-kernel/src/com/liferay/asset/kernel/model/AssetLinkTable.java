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
 * The table class for the AssetLink.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetLinkTable extends Table<AssetLinkTable> {

	public static final AssetLinkTable INSTANCE = new AssetLinkTable();

	public final Column<AssetLinkTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<AssetLinkTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT);
	public final Column<AssetLinkTable, Long> linkId = createColumn(
		"linkId", Long.class, Types.BIGINT);
	public final Column<AssetLinkTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<AssetLinkTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<AssetLinkTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<AssetLinkTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetLinkTable, Long> entryId1 = createColumn(
		"entryId1", Long.class, Types.BIGINT);
	public final Column<AssetLinkTable, Long> entryId2 = createColumn(
		"entryId2", Long.class, Types.BIGINT);
	public final Column<AssetLinkTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER);
	public final Column<AssetLinkTable, Integer> weight = createColumn(
		"weight", Integer.class, Types.INTEGER);

	private AssetLinkTable() {
		super("AssetLink", AssetLinkTable::new);
	}

}