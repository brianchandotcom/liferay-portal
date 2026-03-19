/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KaleoNodeSetting&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeSetting
 * @generated
 */
public class KaleoNodeSettingTable extends BaseTable<KaleoNodeSettingTable> {

	public static final KaleoNodeSettingTable INSTANCE =
		new KaleoNodeSettingTable();

	public final Column<KaleoNodeSettingTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoNodeSettingTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoNodeSettingTable, Long> kaleoNodeSettingId =
		createColumn(
			"kaleoNodeSettingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<KaleoNodeSettingTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNodeSettingTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNodeSettingTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNodeSettingTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoNodeSettingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoNodeSettingTable, Long> kaleoNodeId = createColumn(
		"kaleoNodeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNodeSettingTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNodeSettingTable, Clob> value = createColumn(
		"value", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private KaleoNodeSettingTable() {
		super("KaleoNodeSetting", KaleoNodeSettingTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-333266536