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
 * The table class for the &quot;CShippingFixedOptionRel&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRel
 * @generated
 */
public class CommerceShippingFixedOptionRelTable
	extends BaseTable<CommerceShippingFixedOptionRelTable> {

	public static final CommerceShippingFixedOptionRelTable INSTANCE =
		new CommerceShippingFixedOptionRelTable();

	public final Column<CommerceShippingFixedOptionRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceShippingFixedOptionRelTable, Long>
		commerceShippingFixedOptionRelId = createColumn(
			"CShippingFixedOptionRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceShippingFixedOptionRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Long>
		commerceShippingMethodId = createColumn(
			"commerceShippingMethodId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Long>
		commerceShippingFixedOptionId = createColumn(
			"commerceShippingFixedOptionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Long>
		commerceInventoryWarehouseId = createColumn(
			"commerceInventoryWarehouseId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Long> countryId =
		createColumn(
			"countryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Long> regionId =
		createColumn("regionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, String> zip =
		createColumn("zip", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Double>
		weightFrom = createColumn(
			"weightFrom", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Double> weightTo =
		createColumn(
			"weightTo", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, BigDecimal>
		fixedPrice = createColumn(
			"fixedPrice", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, BigDecimal>
		rateUnitWeightPrice = createColumn(
			"rateUnitWeightPrice", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionRelTable, Double>
		ratePercentage = createColumn(
			"ratePercentage", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CommerceShippingFixedOptionRelTable() {
		super(
			"CShippingFixedOptionRel",
			CommerceShippingFixedOptionRelTable::new);
	}

}