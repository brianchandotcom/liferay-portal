/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CVirtualOrderItemFileEntry&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemFileEntry
 * @generated
 */
public class CommerceVirtualOrderItemFileEntryTable
	extends BaseTable<CommerceVirtualOrderItemFileEntryTable> {

	public static final CommerceVirtualOrderItemFileEntryTable INSTANCE =
		new CommerceVirtualOrderItemFileEntryTable();

	public final Column<CommerceVirtualOrderItemFileEntryTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceVirtualOrderItemFileEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, Long>
		commerceVirtualOrderItemFileEntryId = createColumn(
			"cVirtualOrderItemFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceVirtualOrderItemFileEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, Long>
		commerceVirtualOrderItemId = createColumn(
			"commerceVirtualOrderItemId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, Long>
		fileEntryId = createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, String> url =
		createColumn("url", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, Integer>
		usages = createColumn(
			"usages", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceVirtualOrderItemFileEntryTable, String>
		version = createColumn(
			"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CommerceVirtualOrderItemFileEntryTable() {
		super(
			"CVirtualOrderItemFileEntry",
			CommerceVirtualOrderItemFileEntryTable::new);
	}

}