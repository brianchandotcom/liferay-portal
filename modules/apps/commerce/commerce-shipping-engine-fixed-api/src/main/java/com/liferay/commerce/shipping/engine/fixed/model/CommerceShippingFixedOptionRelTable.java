/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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