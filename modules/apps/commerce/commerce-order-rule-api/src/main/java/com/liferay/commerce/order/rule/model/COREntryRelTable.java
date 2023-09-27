/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;COREntryRel&quot; database table.
 *
 * @author Luca Pellizzon
 * @see COREntryRel
 * @generated
 */
public class COREntryRelTable extends BaseTable<COREntryRelTable> {

	public static final COREntryRelTable INSTANCE = new COREntryRelTable();

	public final Column<COREntryRelTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<COREntryRelTable, Long> COREntryRelId = createColumn(
		"COREntryRelId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<COREntryRelTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<COREntryRelTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<COREntryRelTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<COREntryRelTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<COREntryRelTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<COREntryRelTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<COREntryRelTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<COREntryRelTable, Long> COREntryId = createColumn(
		"COREntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private COREntryRelTable() {
		super("COREntryRel", COREntryRelTable::new);
	}

}