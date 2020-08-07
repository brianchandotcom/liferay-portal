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

package com.liferay.commerce.payment.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommercePaymentMethodGroupRel&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRel
 * @generated
 */
public class CommercePaymentMethodGroupRelTable
	extends BaseTable<CommercePaymentMethodGroupRelTable> {

	public static final CommercePaymentMethodGroupRelTable INSTANCE =
		new CommercePaymentMethodGroupRelTable();

	public final Column<CommercePaymentMethodGroupRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePaymentMethodGroupRelTable, Long>
		commercePaymentMethodGroupRelId = createColumn(
			"CPaymentMethodGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePaymentMethodGroupRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, String>
		description = createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Long> imageId =
		createColumn("imageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, String> engineKey =
		createColumn(
			"engineKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private CommercePaymentMethodGroupRelTable() {
		super(
			"CommercePaymentMethodGroupRel",
			CommercePaymentMethodGroupRelTable::new);
	}

}