/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CDiscountCAccountGroupRel&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceDiscountCommerceAccountGroupRel
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelTable
	extends BaseTable<CommerceDiscountCommerceAccountGroupRelTable> {

	public static final CommerceDiscountCommerceAccountGroupRelTable INSTANCE =
		new CommerceDiscountCommerceAccountGroupRelTable();

	public final Column<CommerceDiscountCommerceAccountGroupRelTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceDiscountCommerceAccountGroupRelTable, Long>
		commerceDiscountCommerceAccountGroupRelId = createColumn(
			"CDiscountCAccountGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceDiscountCommerceAccountGroupRelTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountCommerceAccountGroupRelTable, Long>
		userId = createColumn(
			"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountCommerceAccountGroupRelTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountCommerceAccountGroupRelTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountCommerceAccountGroupRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountCommerceAccountGroupRelTable, Long>
		commerceDiscountId = createColumn(
			"commerceDiscountId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountCommerceAccountGroupRelTable, Long>
		commerceAccountGroupId = createColumn(
			"commerceAccountGroupId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CommerceDiscountCommerceAccountGroupRelTable() {
		super(
			"CDiscountCAccountGroupRel",
			CommerceDiscountCommerceAccountGroupRelTable::new);
	}

}