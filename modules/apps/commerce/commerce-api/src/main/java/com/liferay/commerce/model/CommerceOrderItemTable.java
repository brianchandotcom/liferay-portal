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
 * The table class for the &quot;CommerceOrderItem&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItem
 * @generated
 */
public class CommerceOrderItemTable extends BaseTable<CommerceOrderItemTable> {

	public static final CommerceOrderItemTable INSTANCE =
		new CommerceOrderItemTable();

	public final Column<CommerceOrderItemTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> commerceOrderItemId =
		createColumn(
			"commerceOrderItemId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceOrderItemTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> bookedQuantityId =
		createColumn(
			"bookedQuantityId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> commerceOrderId =
		createColumn(
			"commerceOrderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> commercePriceListId =
		createColumn(
			"commercePriceListId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> CPInstanceId =
		createColumn(
			"CPInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> CProductId = createColumn(
		"CProductId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long>
		parentCommerceOrderItemId = createColumn(
			"parentCommerceOrderItemId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> shippingAddressId =
		createColumn(
			"shippingAddressId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, String> deliveryGroup =
		createColumn(
			"deliveryGroup", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long>
		deliveryMaxSubscriptionCycles = createColumn(
			"deliveryMaxSubscriptionCycles", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Integer>
		deliverySubscriptionLength = createColumn(
			"deliverySubscriptionLength", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, String>
		deliverySubscriptionType = createColumn(
			"deliverySubscriptionType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, String>
		deliverySubscriptionTypeSettings = createColumn(
			"deliverySubTypeSettings", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Double> depth = createColumn(
		"depth", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal> discountAmount =
		createColumn(
			"discountAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		discountPercentageLevel1 = createColumn(
			"discountPercentageLevel1", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		discountPercentageLevel2 = createColumn(
			"discountPercentageLevel2", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		discountPercentageLevel3 = createColumn(
			"discountPercentageLevel3", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		discountPercentageLevel4 = createColumn(
			"discountPercentageLevel4", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		discountPercentageLevel1WithTaxAmount = createColumn(
			"discountPctLevel1WithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		discountPercentageLevel2WithTaxAmount = createColumn(
			"discountPctLevel2WithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		discountPercentageLevel3WithTaxAmount = createColumn(
			"discountPctLevel3WithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		discountPercentageLevel4WithTaxAmount = createColumn(
			"discountPctLevel4WithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		discountWithTaxAmount = createColumn(
			"discountWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal> finalPrice =
		createColumn(
			"finalPrice", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		finalPriceWithTaxAmount = createColumn(
			"finalPriceWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Boolean> freeShipping =
		createColumn(
			"freeShipping", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Double> height = createColumn(
		"height", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Clob> json = createColumn(
		"json", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Boolean> manuallyAdjusted =
		createColumn(
			"manuallyAdjusted", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Long> maxSubscriptionCycles =
		createColumn(
			"maxSubscriptionCycles", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, String> printedNote =
		createColumn(
			"printedNote", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal> promoPrice =
		createColumn(
			"promoPrice", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		promoPriceWithTaxAmount = createColumn(
			"promoPriceWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Integer> quantity =
		createColumn(
			"quantity", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Date> requestedDeliveryDate =
		createColumn(
			"requestedDeliveryDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Boolean> shipSeparately =
		createColumn(
			"shipSeparately", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Boolean> shippable =
		createColumn(
			"shippable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Integer> shippedQuantity =
		createColumn(
			"shippedQuantity", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Double> shippingExtraPrice =
		createColumn(
			"shippingExtraPrice", Double.class, Types.DOUBLE,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, String> sku = createColumn(
		"sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Boolean> subscription =
		createColumn(
			"subscription", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Integer> subscriptionLength =
		createColumn(
			"subscriptionLength", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, String> subscriptionType =
		createColumn(
			"subscriptionType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, String>
		subscriptionTypeSettings = createColumn(
			"subscriptionTypeSettings", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal> unitPrice =
		createColumn(
			"unitPrice", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, BigDecimal>
		unitPriceWithTaxAmount = createColumn(
			"unitPriceWithTaxAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Double> weight = createColumn(
		"weight", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderItemTable, Double> width = createColumn(
		"width", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CommerceOrderItemTable() {
		super("CommerceOrderItem", CommerceOrderItemTable::new);
	}

}