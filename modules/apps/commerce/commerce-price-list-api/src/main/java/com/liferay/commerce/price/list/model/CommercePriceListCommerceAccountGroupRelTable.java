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

package com.liferay.commerce.price.list.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPLCommerceGroupAccountRel&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListCommerceAccountGroupRel
 * @generated
 */
public class CommercePriceListCommerceAccountGroupRelTable
	extends BaseTable<CommercePriceListCommerceAccountGroupRelTable> {

	public static final CommercePriceListCommerceAccountGroupRelTable INSTANCE =
		new CommercePriceListCommerceAccountGroupRelTable();

	public final Column<CommercePriceListCommerceAccountGroupRelTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, String>
		uuid = createColumn(
			"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Long>
		commercePriceListCommerceAccountGroupRelId = createColumn(
			"CPLCommerceAccountGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Long>
		userId = createColumn(
			"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Long>
		commercePriceListId = createColumn(
			"commercePriceListId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Long>
		commerceAccountGroupId = createColumn(
			"commerceAccountGroupId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Integer>
		order = createColumn(
			"order_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListCommerceAccountGroupRelTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private CommercePriceListCommerceAccountGroupRelTable() {
		super(
			"CPLCommerceGroupAccountRel",
			CommercePriceListCommerceAccountGroupRelTable::new);
	}

}