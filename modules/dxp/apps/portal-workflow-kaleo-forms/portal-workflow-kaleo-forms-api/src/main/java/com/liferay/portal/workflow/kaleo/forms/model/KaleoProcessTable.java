/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KaleoProcess&quot; database table.
 *
 * @author Marcellus Tavares
 * @see KaleoProcess
 * @generated
 */
public class KaleoProcessTable extends BaseTable<KaleoProcessTable> {

	public static final KaleoProcessTable INSTANCE = new KaleoProcessTable();

	public final Column<KaleoProcessTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, Long> kaleoProcessId = createColumn(
		"kaleoProcessId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoProcessTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, Long> DDLRecordSetId = createColumn(
		"DDLRecordSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, Long> DDMTemplateId = createColumn(
		"DDMTemplateId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, String> workflowDefinitionName =
		createColumn(
			"workflowDefinitionName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoProcessTable, Integer> workflowDefinitionVersion =
		createColumn(
			"workflowDefinitionVersion", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);

	private KaleoProcessTable() {
		super("KaleoProcess", KaleoProcessTable::new);
	}

}