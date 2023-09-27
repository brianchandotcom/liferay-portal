/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPDefinitionInventory&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventory
 * @generated
 */
public class CPDefinitionInventoryTable
	extends BaseTable<CPDefinitionInventoryTable> {

	public static final CPDefinitionInventoryTable INSTANCE =
		new CPDefinitionInventoryTable();

	public final Column<CPDefinitionInventoryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPDefinitionInventoryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPDefinitionInventoryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, Long>
		CPDefinitionInventoryId = createColumn(
			"CPDefinitionInventoryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDefinitionInventoryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, Long> CPDefinitionId =
		createColumn(
			"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, String>
		CPDefinitionInventoryEngine = createColumn(
			"CPDefinitionInventoryEngine", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, String> lowStockActivity =
		createColumn(
			"lowStockActivity", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, Boolean>
		displayAvailability = createColumn(
			"displayAvailability", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, Boolean>
		displayStockQuantity = createColumn(
			"displayStockQuantity", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, BigDecimal>
		minStockQuantity = createColumn(
			"minStockQuantity", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, Boolean> backOrders =
		createColumn(
			"backOrders", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, BigDecimal>
		minOrderQuantity = createColumn(
			"minOrderQuantity", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, BigDecimal>
		maxOrderQuantity = createColumn(
			"maxOrderQuantity", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, String>
		allowedOrderQuantities = createColumn(
			"allowedOrderQuantities", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionInventoryTable, BigDecimal>
		multipleOrderQuantity = createColumn(
			"multipleOrderQuantity", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);

	private CPDefinitionInventoryTable() {
		super("CPDefinitionInventory", CPDefinitionInventoryTable::new);
	}

}