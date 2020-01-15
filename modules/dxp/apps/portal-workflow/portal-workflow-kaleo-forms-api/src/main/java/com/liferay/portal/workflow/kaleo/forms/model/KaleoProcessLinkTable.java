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

/**
 * The table class for the KaleoProcessLink.
 *
 * @author Marcellus Tavares
 * @generated
 */
public class KaleoProcessLinkTable extends Table<KaleoProcessLinkTable> {

	public static final KaleoProcessLinkTable INSTANCE =
		new KaleoProcessLinkTable();

	public final Column<KaleoProcessLinkTable, Long> kaleoProcessLinkId =
		createColumn("kaleoProcessLinkId", Long.class, Types.BIGINT);
	public final Column<KaleoProcessLinkTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<KaleoProcessLinkTable, Long> kaleoProcessId =
		createColumn("kaleoProcessId", Long.class, Types.BIGINT);
	public final Column<KaleoProcessLinkTable, String> workflowTaskName =
		createColumn("workflowTaskName", String.class, Types.VARCHAR);
	public final Column<KaleoProcessLinkTable, Long> DDMTemplateId =
		createColumn("DDMTemplateId", Long.class, Types.BIGINT);

	private KaleoProcessLinkTable() {
		super("KaleoProcessLink", KaleoProcessLinkTable::new);
	}

}