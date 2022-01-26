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
 * The table class for the &quot;CPMethodGroupRelQualifier&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelQualifier
 * @generated
 */
public class CommercePaymentMethodGroupRelQualifierTable
	extends BaseTable<CommercePaymentMethodGroupRelQualifierTable> {

	public static final CommercePaymentMethodGroupRelQualifierTable INSTANCE =
		new CommercePaymentMethodGroupRelQualifierTable();

	public final Column<CommercePaymentMethodGroupRelQualifierTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePaymentMethodGroupRelQualifierTable, Long>
		commercePaymentMethodGroupRelQualifierId = createColumn(
			"CPMethodGroupRelQualifierId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePaymentMethodGroupRelQualifierTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelQualifierTable, Long>
		userId = createColumn(
			"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelQualifierTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelQualifierTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelQualifierTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelQualifierTable, Long>
		classNameId = createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelQualifierTable, Long>
		classPK = createColumn(
			"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelQualifierTable, Long>
		CommercePaymentMethodGroupRelId = createColumn(
			"CPaymentMethodGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CommercePaymentMethodGroupRelQualifierTable() {
		super(
			"CPMethodGroupRelQualifier",
			CommercePaymentMethodGroupRelQualifierTable::new);
	}

}