/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CSFixedOptionQualifier&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionQualifier
 * @generated
 */
public class CommerceShippingFixedOptionQualifierTable
	extends BaseTable<CommerceShippingFixedOptionQualifierTable> {

	public static final CommerceShippingFixedOptionQualifierTable INSTANCE =
		new CommerceShippingFixedOptionQualifierTable();

	public final Column<CommerceShippingFixedOptionQualifierTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceShippingFixedOptionQualifierTable, Long>
		commerceShippingFixedOptionQualifierId = createColumn(
			"CSFixedOptionQualifierId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceShippingFixedOptionQualifierTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionQualifierTable, Long>
		userId = createColumn(
			"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionQualifierTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionQualifierTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionQualifierTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionQualifierTable, Long>
		classNameId = createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionQualifierTable, Long>
		classPK = createColumn(
			"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingFixedOptionQualifierTable, Long>
		commerceShippingFixedOptionId = createColumn(
			"commerceShippingFixedOptionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CommerceShippingFixedOptionQualifierTable() {
		super(
			"CSFixedOptionQualifier",
			CommerceShippingFixedOptionQualifierTable::new);
	}

}