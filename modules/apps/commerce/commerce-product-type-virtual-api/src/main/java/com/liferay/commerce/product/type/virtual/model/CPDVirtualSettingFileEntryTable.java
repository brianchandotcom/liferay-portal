/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPDVirtualSettingFileEntry&quot; database table.
 *
 * @author Marco Leo
 * @see CPDVirtualSettingFileEntry
 * @generated
 */
public class CPDVirtualSettingFileEntryTable
	extends BaseTable<CPDVirtualSettingFileEntryTable> {

	public static final CPDVirtualSettingFileEntryTable INSTANCE =
		new CPDVirtualSettingFileEntryTable();

	public final Column<CPDVirtualSettingFileEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPDVirtualSettingFileEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, Long>
		CPDefinitionVirtualSettingFileEntryId = createColumn(
			"CPDVirtualSettingFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDVirtualSettingFileEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, Long>
		CPDefinitionVirtualSettingId = createColumn(
			"CPDefinitionVirtualSettingId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, Long> fileEntryId =
		createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, String> url =
		createColumn("url", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDVirtualSettingFileEntryTable, String> version =
		createColumn(
			"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CPDVirtualSettingFileEntryTable() {
		super(
			"CPDVirtualSettingFileEntry", CPDVirtualSettingFileEntryTable::new);
	}

}