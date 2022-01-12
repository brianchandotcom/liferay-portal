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

package com.liferay.commerce.price.list.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommercePriceEntry&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntry
 * @generated
 */
public class CommercePriceEntryTable
	extends BaseTable<CommercePriceEntryTable> {

	public static final CommercePriceEntryTable INSTANCE =
		new CommercePriceEntryTable();

	public final Column<CommercePriceEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePriceEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommercePriceEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Long> commercePriceEntryId =
		createColumn(
			"commercePriceEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePriceEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Long> commercePriceListId =
		createColumn(
			"commercePriceListId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, String> CPInstanceUuid =
		createColumn(
			"CPInstanceUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Long> CProductId =
		createColumn(
			"CProductId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, BigDecimal> price =
		createColumn(
			"price", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, BigDecimal> promoPrice =
		createColumn(
			"promoPrice", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Boolean> discountDiscovery =
		createColumn(
			"discountDiscovery", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, BigDecimal> discountLevel1 =
		createColumn(
			"discountLevel1", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, BigDecimal> discountLevel2 =
		createColumn(
			"discountLevel2", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, BigDecimal> discountLevel3 =
		createColumn(
			"discountLevel3", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, BigDecimal> discountLevel4 =
		createColumn(
			"discountLevel4", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Boolean> hasTierPrice =
		createColumn(
			"hasTierPrice", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Boolean> bulkPricing =
		createColumn(
			"bulkPricing", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Date> displayDate =
		createColumn(
			"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceEntryTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommercePriceEntryTable() {
		super("CommercePriceEntry", CommercePriceEntryTable::new);
	}

}