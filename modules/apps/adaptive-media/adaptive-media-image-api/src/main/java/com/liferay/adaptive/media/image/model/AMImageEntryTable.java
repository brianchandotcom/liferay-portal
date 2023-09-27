/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AMImageEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AMImageEntry
 * @generated
 */
public class AMImageEntryTable extends BaseTable<AMImageEntryTable> {

	public static final AMImageEntryTable INSTANCE = new AMImageEntryTable();

	public final Column<AMImageEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AMImageEntryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AMImageEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Long> amImageEntryId = createColumn(
		"amImageEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AMImageEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, String> configurationUuid =
		createColumn(
			"configurationUuid", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Long> fileVersionId = createColumn(
		"fileVersionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, String> mimeType = createColumn(
		"mimeType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Integer> height = createColumn(
		"height", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Integer> width = createColumn(
		"width", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AMImageEntryTable() {
		super("AMImageEntry", AMImageEntryTable::new);
	}

}