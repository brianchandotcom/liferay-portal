/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;OSBFaro_FaroPreferences&quot; database table.
 *
 * @author Matthew Kong
 * @see FaroPreferences
 * @generated
 */
public class FaroPreferencesTable extends BaseTable<FaroPreferencesTable> {

	public static final FaroPreferencesTable INSTANCE =
		new FaroPreferencesTable();

	public final Column<FaroPreferencesTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FaroPreferencesTable, Long> faroPreferencesId =
		createColumn(
			"faroPreferencesId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FaroPreferencesTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroPreferencesTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroPreferencesTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroPreferencesTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FaroPreferencesTable, Long> createTime = createColumn(
		"createTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroPreferencesTable, Long> modifiedTime = createColumn(
		"modifiedTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroPreferencesTable, Long> ownerId = createColumn(
		"ownerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroPreferencesTable, String> preferences =
		createColumn(
			"preferences", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private FaroPreferencesTable() {
		super("OSBFaro_FaroPreferences", FaroPreferencesTable::new);
	}

}