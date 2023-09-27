/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
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
	public final Column<CommercePaymentMethodGroupRelTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Long> imageId =
		createColumn("imageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, String>
		paymentIntegrationKey = createColumn(
			"paymentIntegrationKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommercePaymentMethodGroupRelTable, Clob> typeSettings =
		createColumn(
			"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CommercePaymentMethodGroupRelTable() {
		super(
			"CommercePaymentMethodGroupRel",
			CommercePaymentMethodGroupRelTable::new);
	}

}