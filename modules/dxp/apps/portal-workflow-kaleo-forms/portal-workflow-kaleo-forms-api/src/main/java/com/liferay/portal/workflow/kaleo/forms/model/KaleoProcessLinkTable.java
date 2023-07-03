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
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;KaleoProcessLink&quot; database table.
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLink
 * @generated
 */
public class KaleoProcessLinkTable extends BaseTable<KaleoProcessLinkTable> {

	public static final KaleoProcessLinkTable INSTANCE =
		new KaleoProcessLinkTable();

	public final Column<KaleoProcessLinkTable, Long> kaleoProcessLinkId =
		createColumn(
			"kaleoProcessLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<KaleoProcessLinkTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessLinkTable, Long> kaleoProcessId =
		createColumn(
			"kaleoProcessId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoProcessLinkTable, String> workflowTaskName =
		createColumn(
			"workflowTaskName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoProcessLinkTable, Long> DDMTemplateId =
		createColumn(
			"DDMTemplateId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private KaleoProcessLinkTable() {
		super("KaleoProcessLink", KaleoProcessLinkTable::new);
	}

}