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

import java.math.BigDecimal;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommercePaymentEntryAudit&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryAudit
 * @generated
 */
public class CommercePaymentEntryAuditTable
	extends BaseTable<CommercePaymentEntryAuditTable> {

	public static final CommercePaymentEntryAuditTable INSTANCE =
		new CommercePaymentEntryAuditTable();

	public final Column<CommercePaymentEntryAuditTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePaymentEntryAuditTable, Long>
		commercePaymentEntryAuditId = createColumn(
			"commercePaymentEntryAuditId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePaymentEntryAuditTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryAuditTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryAuditTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryAuditTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryAuditTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryAuditTable, Long>
		commercePaymentEntryId = createColumn(
			"commercePaymentEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryAuditTable, BigDecimal> amount =
		createColumn(
			"amount", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryAuditTable, String> currencyCode =
		createColumn(
			"currencyCode", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryAuditTable, String> logType =
		createColumn(
			"logType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryAuditTable, Clob> logTypeSettings =
		createColumn(
			"logTypeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CommercePaymentEntryAuditTable() {
		super("CommercePaymentEntryAudit", CommercePaymentEntryAuditTable::new);
	}

}