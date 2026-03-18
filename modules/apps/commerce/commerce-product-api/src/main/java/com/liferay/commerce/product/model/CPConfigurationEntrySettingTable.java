/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPConfigurationEntrySetting&quot; database table.
 *
 * @author Marco Leo
 * @see CPConfigurationEntrySetting
 * @generated
 */
public class CPConfigurationEntrySettingTable
	extends BaseTable<CPConfigurationEntrySettingTable> {

	public static final CPConfigurationEntrySettingTable INSTANCE =
		new CPConfigurationEntrySettingTable();

	public final Column<CPConfigurationEntrySettingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPConfigurationEntrySettingTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPConfigurationEntrySettingTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPConfigurationEntrySettingTable, Long>
		CPConfigurationEntrySettingId = createColumn(
			"CPConfigurationEntrySettingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPConfigurationEntrySettingTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPConfigurationEntrySettingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPConfigurationEntrySettingTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPConfigurationEntrySettingTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPConfigurationEntrySettingTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPConfigurationEntrySettingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPConfigurationEntrySettingTable, Long>
		CPConfigurationEntryId = createColumn(
			"CPConfigurationEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPConfigurationEntrySettingTable, Integer> type =
		createColumn(
			"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CPConfigurationEntrySettingTable, Clob> value =
		createColumn("value", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CPConfigurationEntrySettingTable() {
		super(
			"CPConfigurationEntrySetting",
			CPConfigurationEntrySettingTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1567243876