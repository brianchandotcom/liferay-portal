/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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