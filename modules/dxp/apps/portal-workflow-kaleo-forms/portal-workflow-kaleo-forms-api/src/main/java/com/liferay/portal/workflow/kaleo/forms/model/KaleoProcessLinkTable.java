/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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