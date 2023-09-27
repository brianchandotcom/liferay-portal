/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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