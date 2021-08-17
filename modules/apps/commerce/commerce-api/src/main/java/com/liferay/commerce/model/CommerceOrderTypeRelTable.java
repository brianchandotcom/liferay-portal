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
 * The table class for the &quot;CommerceOrderTypeRel&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRel
 * @generated
 */
public class CommerceOrderTypeRelTable
	extends BaseTable<CommerceOrderTypeRelTable> {

	public static final CommerceOrderTypeRelTable INSTANCE =
		new CommerceOrderTypeRelTable();

	public final Column<CommerceOrderTypeRelTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTypeRelTable, Long>
		commerceOrderTypeRelId = createColumn(
			"commerceOrderTypeRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceOrderTypeRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTypeRelTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTypeRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTypeRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTypeRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTypeRelTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTypeRelTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceOrderTypeRelTable, Long> commerceOrderTypeId =
		createColumn(
			"commerceOrderTypeId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CommerceOrderTypeRelTable() {
		super("CommerceOrderTypeRel", CommerceOrderTypeRelTable::new);
	}

}