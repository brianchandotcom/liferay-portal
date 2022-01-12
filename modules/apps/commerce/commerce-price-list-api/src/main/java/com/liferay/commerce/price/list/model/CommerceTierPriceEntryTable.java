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
 * The table class for the &quot;CommerceTierPriceEntry&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntry
 * @generated
 */
public class CommerceTierPriceEntryTable
	extends BaseTable<CommerceTierPriceEntryTable> {

	public static final CommerceTierPriceEntryTable INSTANCE =
		new CommerceTierPriceEntryTable();

	public final Column<CommerceTierPriceEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceTierPriceEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceTierPriceEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Long>
		commerceTierPriceEntryId = createColumn(
			"commerceTierPriceEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceTierPriceEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Long>
		commercePriceEntryId = createColumn(
			"commercePriceEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, BigDecimal> price =
		createColumn(
			"price", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, BigDecimal> promoPrice =
		createColumn(
			"promoPrice", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Boolean>
		discountDiscovery = createColumn(
			"discountDiscovery", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, BigDecimal>
		discountLevel1 = createColumn(
			"discountLevel1", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, BigDecimal>
		discountLevel2 = createColumn(
			"discountLevel2", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, BigDecimal>
		discountLevel3 = createColumn(
			"discountLevel3", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, BigDecimal>
		discountLevel4 = createColumn(
			"discountLevel4", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Integer> minQuantity =
		createColumn(
			"minQuantity", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Date> displayDate =
		createColumn(
			"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceTierPriceEntryTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceTierPriceEntryTable() {
		super("CommerceTierPriceEntry", CommerceTierPriceEntryTable::new);
	}

}