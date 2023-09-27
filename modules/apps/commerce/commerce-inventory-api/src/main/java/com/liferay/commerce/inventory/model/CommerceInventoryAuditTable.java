/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

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
	public final Column<CommerceInventoryAuditTable, String> logType =
		createColumn(
			"logType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, Clob> logTypeSettings =
		createColumn(
			"logTypeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, BigDecimal> quantity =
		createColumn(
			"quantity", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, String> sku = createColumn(
		"sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryAuditTable, String> unitOfMeasureKey =
		createColumn(
			"unitOfMeasureKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private CommerceInventoryAuditTable() {
		super("CIAudit", CommerceInventoryAuditTable::new);
	}

}