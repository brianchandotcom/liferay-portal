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

package com.liferay.commerce.machine.learning.forecast.alert.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceMLForecastAlertEntry&quot; database table.
 *
 * @author Riccardo Ferrari
 * @see CommerceMLForecastAlertEntry
 * @generated
 */
public class CommerceMLForecastAlertEntryTable
	extends BaseTable<CommerceMLForecastAlertEntryTable> {

	public static final CommerceMLForecastAlertEntryTable INSTANCE =
		new CommerceMLForecastAlertEntryTable();

	public final Column<CommerceMLForecastAlertEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Long>
		commerceMLForecastAlertEntryId = createColumn(
			"commerceMLForecastAlertEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceMLForecastAlertEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Long>
		commerceAccountId = createColumn(
			"commerceAccountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Double> actual =
		createColumn("actual", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Double> forecast =
		createColumn(
			"forecast", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Date> timestamp =
		createColumn(
			"timestamp", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Double>
		relativeChange = createColumn(
			"relativeChange", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceMLForecastAlertEntryTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CommerceMLForecastAlertEntryTable() {
		super(
			"CommerceMLForecastAlertEntry",
			CommerceMLForecastAlertEntryTable::new);
	}

}