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

package com.liferay.commerce.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceOrder&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrder
 * @generated
 */
public class CommerceOrderTable extends BaseTable<CommerceOrderTable> {

	public static final CommerceOrderTable INSTANCE = new CommerceOrderTable();

	public final Column<CommerceOrderTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> commerceOrderId =
		createColumn(
			"commerceOrderId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceOrderTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> commerceAccountId =
		createColumn(
			"commerceAccountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> commerceCurrencyId =
		createColumn(
			"commerceCurrencyId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> commerceOrderTypeId =
		createColumn(
			"commerceOrderTypeId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> billingAddressId =
		createColumn(
			"billingAddressId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> shippingAddressId =
		createColumn(
			"shippingAddressId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, String> commercePaymentMethodKey =
		createColumn(
			"commercePaymentMethodKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Clob> transactionId = createColumn(
		"transactionId", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> commerceShippingMethodId =
		createColumn(
			"commerceShippingMethodId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, String> shippingOptionName =
		createColumn(
			"shippingOptionName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, String> purchaseOrderNumber =
		createColumn(
			"purchaseOrderNumber", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, String> couponCode = createColumn(
		"couponCode", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Date> lastPriceUpdateDate =
		createColumn(
			"lastPriceUpdateDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> subtotal = createColumn(
		"subtotal", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> subtotalDiscountAmount =
		createColumn(
			"subtotalDiscountAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		subtotalDiscountPercentageLevel1 = createColumn(
			"subtotalDiscountPercentLevel1", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		subtotalDiscountPercentageLevel2 = createColumn(
			"subtotalDiscountPercentLevel2", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		subtotalDiscountPercentageLevel3 = createColumn(
			"subtotalDiscountPercentLevel3", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		subtotalDiscountPercentageLevel4 = createColumn(
			"subtotalDiscountPercentLevel4", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> shippingAmount =
		createColumn(
			"shippingAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> shippingDiscountAmount =
		createColumn(
			"shippingDiscountAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		shippingDiscountPercentageLevel1 = createColumn(
			"shippingDiscountPercentLevel1", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		shippingDiscountPercentageLevel2 = createColumn(
			"shippingDiscountPercentLevel2", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		shippingDiscountPercentageLevel3 = createColumn(
			"shippingDiscountPercentLevel3", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		shippingDiscountPercentageLevel4 = createColumn(
			"shippingDiscountPercentLevel4", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> taxAmount =
		createColumn(
			"taxAmount", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> total = createColumn(
		"total", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> totalDiscountAmount =
		createColumn(
			"totalDiscountAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		totalDiscountPercentageLevel1 = createColumn(
			"totalDiscountPercentageLevel1", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		totalDiscountPercentageLevel2 = createColumn(
			"totalDiscountPercentageLevel2", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		totalDiscountPercentageLevel3 = createColumn(
			"totalDiscountPercentageLevel3", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		totalDiscountPercentageLevel4 = createColumn(
			"totalDiscountPercentageLevel4", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> subtotalWithTaxAmount =
		createColumn(
			"subtotalWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		subtotalDiscountWithTaxAmount = createColumn(
			"subtotalDiscountWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		subtotalDiscountPercentageLevel1WithTaxAmount = createColumn(
			"subtotalDiscountPctLev1WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		subtotalDiscountPercentageLevel2WithTaxAmount = createColumn(
			"subtotalDiscountPctLev2WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		subtotalDiscountPercentageLevel3WithTaxAmount = createColumn(
			"subtotalDiscountPctLev3WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		subtotalDiscountPercentageLevel4WithTaxAmount = createColumn(
			"subtotalDiscountPctLev4WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> shippingWithTaxAmount =
		createColumn(
			"shippingWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		shippingDiscountWithTaxAmount = createColumn(
			"shippingDiscountWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		shippingDiscountPercentageLevel1WithTaxAmount = createColumn(
			"shippingDiscountPctLev1WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		shippingDiscountPercentageLevel2WithTaxAmount = createColumn(
			"shippingDiscountPctLev2WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		shippingDiscountPercentageLevel3WithTaxAmount = createColumn(
			"shippingDiscountPctLev3WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		shippingDiscountPercentageLevel4WithTaxAmount = createColumn(
			"shippingDiscountPctLev4WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal> totalWithTaxAmount =
		createColumn(
			"totalWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		totalDiscountWithTaxAmount = createColumn(
			"totalDiscountWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		totalDiscountPercentageLevel1WithTaxAmount = createColumn(
			"totalDiscountPctLev1WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		totalDiscountPercentageLevel2WithTaxAmount = createColumn(
			"totalDiscountPctLev2WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		totalDiscountPercentageLevel3WithTaxAmount = createColumn(
			"totalDiscountPctLev3WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, BigDecimal>
		totalDiscountPercentageLevel4WithTaxAmount = createColumn(
			"totalDiscountPctLev4WithTax", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, String> advanceStatus =
		createColumn(
			"advanceStatus", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Integer> paymentStatus =
		createColumn(
			"paymentStatus", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Date> orderDate = createColumn(
		"orderDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Integer> orderStatus = createColumn(
		"orderStatus", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, String> printedNote = createColumn(
		"printedNote", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Date> requestedDeliveryDate =
		createColumn(
			"requestedDeliveryDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Boolean> manuallyAdjusted =
		createColumn(
			"manuallyAdjusted", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceOrderTable() {
		super("CommerceOrder", CommerceOrderTable::new);
	}

}