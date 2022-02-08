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