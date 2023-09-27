/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KaleoTimer&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTimer
 * @generated
 */
public class KaleoTimerTable extends BaseTable<KaleoTimerTable> {

	public static final KaleoTimerTable INSTANCE = new KaleoTimerTable();

	public final Column<KaleoTimerTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoTimerTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoTimerTable, Long> kaleoTimerId = createColumn(
		"kaleoTimerId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoTimerTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, String> kaleoClassName = createColumn(
		"kaleoClassName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Long> kaleoClassPK = createColumn(
		"kaleoClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Long> kaleoDefinitionId = createColumn(
		"kaleoDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Long> kaleoDefinitionVersionId =
		createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Boolean> blocking = createColumn(
		"blocking", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Double> duration = createColumn(
		"duration", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, String> scale = createColumn(
		"scale", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, Double> recurrenceDuration =
		createColumn(
			"recurrenceDuration", Double.class, Types.DOUBLE,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTimerTable, String> recurrenceScale = createColumn(
		"recurrenceScale", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private KaleoTimerTable() {
		super("KaleoTimer", KaleoTimerTable::new);
	}

}