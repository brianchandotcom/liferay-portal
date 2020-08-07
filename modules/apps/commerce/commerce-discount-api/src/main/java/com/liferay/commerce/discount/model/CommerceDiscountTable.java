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

package com.liferay.commerce.discount.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceDiscount&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceDiscount
 * @generated
 */
public class CommerceDiscountTable extends BaseTable<CommerceDiscountTable> {

	public static final CommerceDiscountTable INSTANCE =
		new CommerceDiscountTable();

	public final Column<CommerceDiscountTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceDiscountTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Long> commerceDiscountId =
		createColumn(
			"commerceDiscountId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceDiscountTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, String> target = createColumn(
		"target", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Boolean> useCouponCode =
		createColumn(
			"useCouponCode", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, String> couponCode =
		createColumn(
			"couponCode", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Boolean> usePercentage =
		createColumn(
			"usePercentage", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, BigDecimal>
		maximumDiscountAmount = createColumn(
			"maximumDiscountAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, String> level = createColumn(
		"levelType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, BigDecimal> level1 =
		createColumn(
			"level1", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, BigDecimal> level2 =
		createColumn(
			"level2", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, BigDecimal> level3 =
		createColumn(
			"level3", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, BigDecimal> level4 =
		createColumn(
			"level4", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, String> limitationType =
		createColumn(
			"limitationType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Integer> limitationTimes =
		createColumn(
			"limitationTimes", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Integer>
		limitationTimesPerAccount = createColumn(
			"limitationTimesPerAccount", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Integer> numberOfUse =
		createColumn(
			"numberOfUse", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Boolean> rulesConjunction =
		createColumn(
			"rulesConjunction", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Date> displayDate = createColumn(
		"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceDiscountTable() {
		super("CommerceDiscount", CommerceDiscountTable::new);
	}

}