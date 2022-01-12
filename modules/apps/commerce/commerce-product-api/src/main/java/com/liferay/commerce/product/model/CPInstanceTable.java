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

package com.liferay.commerce.product.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPInstance&quot; database table.
 *
 * @author Marco Leo
 * @see CPInstance
 * @generated
 */
public class CPInstanceTable extends BaseTable<CPInstanceTable> {

	public static final CPInstanceTable INSTANCE = new CPInstanceTable();

	public final Column<CPInstanceTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPInstanceTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPInstanceTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Long> CPInstanceId = createColumn(
		"CPInstanceId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPInstanceTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Long> CPDefinitionId = createColumn(
		"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> CPInstanceUuid = createColumn(
		"CPInstanceUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> sku = createColumn(
		"sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> gtin = createColumn(
		"gtin", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> manufacturerPartNumber =
		createColumn(
			"manufacturerPartNumber", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Boolean> purchasable = createColumn(
		"purchasable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Double> width = createColumn(
		"width", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Double> height = createColumn(
		"height", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Double> depth = createColumn(
		"depth", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Double> weight = createColumn(
		"weight", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, BigDecimal> price = createColumn(
		"price", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, BigDecimal> promoPrice = createColumn(
		"promoPrice", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, BigDecimal> cost = createColumn(
		"cost", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Boolean> published = createColumn(
		"published", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Date> displayDate = createColumn(
		"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Boolean> overrideSubscriptionInfo =
		createColumn(
			"overrideSubscriptionInfo", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Boolean> subscriptionEnabled =
		createColumn(
			"subscriptionEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Integer> subscriptionLength =
		createColumn(
			"subscriptionLength", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> subscriptionType =
		createColumn(
			"subscriptionType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Clob> subscriptionTypeSettings =
		createColumn(
			"subscriptionTypeSettings", Clob.class, Types.CLOB,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Long> maxSubscriptionCycles =
		createColumn(
			"maxSubscriptionCycles", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Boolean> deliverySubscriptionEnabled =
		createColumn(
			"deliverySubscriptionEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Integer> deliverySubscriptionLength =
		createColumn(
			"deliverySubscriptionLength", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> deliverySubscriptionType =
		createColumn(
			"deliverySubscriptionType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String>
		deliverySubscriptionTypeSettings = createColumn(
			"deliverySubTypeSettings", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Long> deliveryMaxSubscriptionCycles =
		createColumn(
			"deliveryMaxSubscriptionCycles", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> unspsc = createColumn(
		"unspsc", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Boolean> discontinued = createColumn(
		"discontinued", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Date> discontinuedDate = createColumn(
		"discontinuedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> replacementCPInstanceUuid =
		createColumn(
			"replacementCPInstanceUuid", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Long> replacementCProductId =
		createColumn(
			"replacementCProductId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CPInstanceTable() {
		super("CPInstance", CPInstanceTable::new);
	}

}