/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceShipment&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipment
 * @generated
 */
public class CommerceShipmentTable extends BaseTable<CommerceShipmentTable> {

	public static final CommerceShipmentTable INSTANCE =
		new CommerceShipmentTable();

	public final Column<CommerceShipmentTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceShipmentTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Long> commerceShipmentId =
		createColumn(
			"commerceShipmentId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceShipmentTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Long> commerceAccountId =
		createColumn(
			"commerceAccountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Long> commerceAddressId =
		createColumn(
			"commerceAddressId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Long> commerceShippingMethodId =
		createColumn(
			"commerceShippingMethodId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, String> carrier = createColumn(
		"carrier", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Date> expectedDate =
		createColumn(
			"expectedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Date> shippingDate =
		createColumn(
			"shippingDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Clob> shippingOptionName =
		createColumn(
			"shippingOptionName", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, String> trackingNumber =
		createColumn(
			"trackingNumber", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, String> trackingURL =
		createColumn(
			"trackingURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShipmentTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CommerceShipmentTable() {
		super("CommerceShipment", CommerceShipmentTable::new);
	}

}