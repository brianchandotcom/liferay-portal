/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	public final Column<FaroPreferencesTable, Long> createTime = createColumn(
		"createTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroPreferencesTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroPreferencesTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
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