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
 * The table class for the &quot;CommerceShipmentItem&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItem
 * @generated
 */
public class CommerceShipmentItemTable
	extends BaseTable<CommerceShipmentItemTable> {

	public static final CommerceShipmentItemTable INSTANCE =
		new CommerceShipmentItemTable();

	public final Column<CommerceShipmentItemTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceShipmentItemTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, Long>
		commerceShipmentItemId = createColumn(
			"commerceShipmentItemId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceShipmentItemTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, Long> commerceShipmentId =
		createColumn(
			"commerceShipmentId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, Long> commerceOrderItemId =
		createColumn(
			"commerceOrderItemId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, Long>
		commerceInventoryWarehouseId = createColumn(
			"commerceInventoryWarehouseId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, BigDecimal> quantity =
		createColumn(
			"quantity", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentItemTable, String> unitOfMeasureKey =
		createColumn(
			"unitOfMeasureKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private CommerceShipmentItemTable() {
		super("CommerceShipmentItem", CommerceShipmentItemTable::new);
	}

}