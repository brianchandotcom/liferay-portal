/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.qualifier.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceQualifierEntry&quot; database table.
 *
 * @author Riccardo Alberti
 * @see CommerceQualifierEntry
 * @generated
 */
public class CommerceQualifierEntryTable
	extends BaseTable<CommerceQualifierEntryTable> {

	public static final CommerceQualifierEntryTable INSTANCE =
		new CommerceQualifierEntryTable();

	public final Column<CommerceQualifierEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceQualifierEntryTable, Long>
		commerceQualifierEntryId = createColumn(
			"commerceQualifierEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceQualifierEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> sourceClassNameId =
		createColumn(
			"sourceClassNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> sourceClassPK =
		createColumn(
			"sourceClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, String>
		sourceCommerceQualifierMetadataKey = createColumn(
			"sourceCQualifierMetadataKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> targetClassNameId =
		createColumn(
			"targetClassNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> targetClassPK =
		createColumn(
			"targetClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, String>
		targetCommerceQualifierMetadataKey = createColumn(
			"targetCQualifierMetadataKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private CommerceQualifierEntryTable() {
		super("CommerceQualifierEntry", CommerceQualifierEntryTable::new);
	}

}