/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceTaxFixedRate&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRate
 * @generated
 */
public class CommerceTaxFixedRateTable
	extends BaseTable<CommerceTaxFixedRateTable> {

	public static final CommerceTaxFixedRateTable INSTANCE =
		new CommerceTaxFixedRateTable();

	public final Column<CommerceTaxFixedRateTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceTaxFixedRateTable, Long>
		commerceTaxFixedRateId = createColumn(
			"commerceTaxFixedRateId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceTaxFixedRateTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateTable, Long> CPTaxCategoryId =
		createColumn(
			"CPTaxCategoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateTable, Long> commerceTaxMethodId =
		createColumn(
			"commerceTaxMethodId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTaxFixedRateTable, Double> rate = createColumn(
		"rate", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CommerceTaxFixedRateTable() {
		super("CommerceTaxFixedRate", CommerceTaxFixedRateTable::new);
	}

}