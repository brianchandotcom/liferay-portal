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
 * The table class for the &quot;CommercePaymentEntry&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntry
 * @generated
 */
public class CommercePaymentEntryTable
	extends BaseTable<CommercePaymentEntryTable> {

	public static final CommercePaymentEntryTable INSTANCE =
		new CommercePaymentEntryTable();

	public final Column<CommercePaymentEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePaymentEntryTable, Long>
		commercePaymentEntryId = createColumn(
			"commercePaymentEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePaymentEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Long> commerceChannelId =
		createColumn(
			"commerceChannelId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, BigDecimal> amount =
		createColumn(
			"amount", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Clob> callbackURL =
		createColumn(
			"callbackURL", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, String> currencyCode =
		createColumn(
			"currencyCode", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, String>
		paymentIntegrationKey = createColumn(
			"paymentIntegrationKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Integer>
		paymentIntegrationType = createColumn(
			"paymentIntegrationType", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Integer> paymentStatus =
		createColumn(
			"paymentStatus", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, Clob> redirectURL =
		createColumn(
			"redirectURL", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentEntryTable, String> transactionCode =
		createColumn(
			"transactionCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private CommercePaymentEntryTable() {
		super("CommercePaymentEntry", CommercePaymentEntryTable::new);
	}

}