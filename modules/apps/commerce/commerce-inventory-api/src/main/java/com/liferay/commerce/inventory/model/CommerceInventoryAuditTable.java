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

package com.liferay.commerce.inventory.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CIAudit&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryAudit
 * @generated
 */
public class CommerceInventoryAuditTable
	extends BaseTable<CommerceInventoryAuditTable> {

	public static final CommerceInventoryAuditTable INSTANCE =
		new CommerceInventoryAuditTable();

	public final Column<CommerceInventoryAuditTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceInventoryAuditTable, Long>
		commerceInventoryAuditId = createColumn(
			"CIAuditId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceInventoryAuditTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, String> sku = createColumn(
		"sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, String> logType =
		createColumn(
			"logType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, Clob> logTypeSettings =
		createColumn(
			"logTypeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, Integer> quantity =
		createColumn(
			"quantity", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CommerceInventoryAuditTable() {
		super("CIAudit", CommerceInventoryAuditTable::new);
	}

}