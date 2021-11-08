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

package com.liferay.commerce.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceShippingMethod&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethod
 * @generated
 */
public class CommerceShippingMethodTable
	extends BaseTable<CommerceShippingMethodTable> {

	public static final CommerceShippingMethodTable INSTANCE =
		new CommerceShippingMethodTable();

	public final Column<CommerceShippingMethodTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceShippingMethodTable, Long>
		commerceShippingMethodId = createColumn(
			"commerceShippingMethodId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceShippingMethodTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, Long> imageId =
		createColumn("imageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, String> engineKey =
		createColumn(
			"engineKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingMethodTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private CommerceShippingMethodTable() {
		super("CommerceShippingMethod", CommerceShippingMethodTable::new);
	}

}