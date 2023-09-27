/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectFieldSetting&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectFieldSetting
 * @generated
 */
public class ObjectFieldSettingTable
	extends BaseTable<ObjectFieldSettingTable> {

	public static final ObjectFieldSettingTable INSTANCE =
		new ObjectFieldSettingTable();

	public final Column<ObjectFieldSettingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectFieldSettingTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldSettingTable, Long> objectFieldSettingId =
		createColumn(
			"objectFieldSettingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectFieldSettingTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldSettingTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldSettingTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldSettingTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldSettingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldSettingTable, Long> objectFieldId =
		createColumn(
			"objectFieldId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldSettingTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldSettingTable, String> value = createColumn(
		"value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ObjectFieldSettingTable() {
		super("ObjectFieldSetting", ObjectFieldSettingTable::new);
	}

}