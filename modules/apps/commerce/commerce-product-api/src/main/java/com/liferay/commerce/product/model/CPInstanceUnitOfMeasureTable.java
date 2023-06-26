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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPInstanceUOM&quot; database table.
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasure
 * @generated
 */
public class CPInstanceUnitOfMeasureTable
	extends BaseTable<CPInstanceUnitOfMeasureTable> {

	public static final CPInstanceUnitOfMeasureTable INSTANCE =
		new CPInstanceUnitOfMeasureTable();

	public final Column<CPInstanceUnitOfMeasureTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPInstanceUnitOfMeasureTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPInstanceUnitOfMeasureTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, Long>
		CPInstanceUnitOfMeasureId = createColumn(
			"CPInstanceUOMId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPInstanceUnitOfMeasureTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, Long> CPInstanceId =
		createColumn(
			"CPInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, BigDecimal>
		incrementalOrderQuantity = createColumn(
			"incrementalOrderQuantity", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, String> key =
		createColumn("key_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, Integer> precision =
		createColumn(
			"precision_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, Boolean> primary =
		createColumn(
			"primary_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, BigDecimal> rate =
		createColumn(
			"rate", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CPInstanceUnitOfMeasureTable, String> sku =
		createColumn("sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CPInstanceUnitOfMeasureTable() {
		super("CPInstanceUOM", CPInstanceUnitOfMeasureTable::new);
	}

}