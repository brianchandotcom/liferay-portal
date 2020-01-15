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

package com.liferay.portal.workflow.kaleo.forms.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the KaleoProcess.
 *
 * @author Marcellus Tavares
 * @generated
 */
public class KaleoProcessTable extends Table<KaleoProcessTable> {

	public static final KaleoProcessTable INSTANCE = new KaleoProcessTable();

	public final Column<KaleoProcessTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<KaleoProcessTable, Long> kaleoProcessId = createColumn(
		"kaleoProcessId", Long.class, Types.BIGINT);
	public final Column<KaleoProcessTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<KaleoProcessTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<KaleoProcessTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<KaleoProcessTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<KaleoProcessTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoProcessTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoProcessTable, Long> DDLRecordSetId = createColumn(
		"DDLRecordSetId", Long.class, Types.BIGINT);
	public final Column<KaleoProcessTable, Long> DDMTemplateId = createColumn(
		"DDMTemplateId", Long.class, Types.BIGINT);
	public final Column<KaleoProcessTable, String> workflowDefinitionName =
		createColumn("workflowDefinitionName", String.class, Types.VARCHAR);
	public final Column<KaleoProcessTable, Integer> workflowDefinitionVersion =
		createColumn("workflowDefinitionVersion", Integer.class, Types.INTEGER);

	private KaleoProcessTable() {
		super("KaleoProcess", KaleoProcessTable::new);
	}

}