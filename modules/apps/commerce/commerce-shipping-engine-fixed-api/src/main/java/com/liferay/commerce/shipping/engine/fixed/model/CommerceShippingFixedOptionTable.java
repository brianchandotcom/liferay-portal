/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceShippingFixedOption&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOption
 * @generated
 */
public class CommerceShippingFixedOptionTable
	extends BaseTable<CommerceShippingFixedOptionTable> {

	public static final CommerceShippingFixedOptionTable INSTANCE =
		new CommerceShippingFixedOptionTable();

	public final Column<CommerceShippingFixedOptionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceShippingFixedOptionTable, Long>
		commerceShippingFixedOptionId = createColumn(
			"commerceShippingFixedOptionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceShippingFixedOptionTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, Long>
		commerceShippingMethodId = createColumn(
			"commerceShippingMethodId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, BigDecimal> amount =
		createColumn(
			"amount", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, String> key =
		createColumn("key_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CommerceShippingFixedOptionTable() {
		super(
			"CommerceShippingFixedOption",
			CommerceShippingFixedOptionTable::new);
	}

}