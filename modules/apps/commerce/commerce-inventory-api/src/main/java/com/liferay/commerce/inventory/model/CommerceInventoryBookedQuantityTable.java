/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CIBookedQuantity&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryBookedQuantity
 * @generated
 */
public class CommerceInventoryBookedQuantityTable
	extends BaseTable<CommerceInventoryBookedQuantityTable> {

	public static final CommerceInventoryBookedQuantityTable INSTANCE =
		new CommerceInventoryBookedQuantityTable();

	public final Column<CommerceInventoryBookedQuantityTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceInventoryBookedQuantityTable, Long>
		commerceInventoryBookedQuantityId = createColumn(
			"CIBookedQuantityId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceInventoryBookedQuantityTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, String>
		bookedNote = createColumn(
			"bookedNote", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, Date>
		expirationDate = createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, BigDecimal>
		quantity = createColumn(
			"quantity", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, String> sku =
		createColumn("sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, String>
		unitOfMeasureKey = createColumn(
			"unitOfMeasureKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private CommerceInventoryBookedQuantityTable() {
		super("CIBookedQuantity", CommerceInventoryBookedQuantityTable::new);
	}

}