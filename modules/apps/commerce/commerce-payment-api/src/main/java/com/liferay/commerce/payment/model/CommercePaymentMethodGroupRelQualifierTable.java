/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		commercePaymentMethodGroupRelId = createColumn(
			"CPaymentMethodGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CommercePaymentMethodGroupRelQualifierTable() {
		super(
			"CPMethodGroupRelQualifier",
			CommercePaymentMethodGroupRelQualifierTable::new);
	}

}