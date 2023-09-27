/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceVirtualOrderItem&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItem
 * @generated
 */
public class CommerceVirtualOrderItemTable
	extends BaseTable<CommerceVirtualOrderItemTable> {

	public static final CommerceVirtualOrderItemTable INSTANCE =
		new CommerceVirtualOrderItemTable();

	public final Column<CommerceVirtualOrderItemTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceVirtualOrderItemTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Long>
		commerceVirtualOrderItemId = createColumn(
			"commerceVirtualOrderItemId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceVirtualOrderItemTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Long>
		commerceOrderItemId = createColumn(
			"commerceOrderItemId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Long> fileEntryId =
		createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, String> url =
		createColumn("url", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Integer>
		activationStatus = createColumn(
			"activationStatus", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Long> duration =
		createColumn("duration", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Integer> usages =
		createColumn(
			"usages", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Integer> maxUsages =
		createColumn(
			"maxUsages", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Date> startDate =
		createColumn(
			"startDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemTable, Date> endDate =
		createColumn(
			"endDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceVirtualOrderItemTable() {
		super("CommerceVirtualOrderItem", CommerceVirtualOrderItemTable::new);
	}

}