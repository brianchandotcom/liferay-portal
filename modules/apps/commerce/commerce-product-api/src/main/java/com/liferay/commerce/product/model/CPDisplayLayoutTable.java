/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPDisplayLayout&quot; database table.
 *
 * @author Marco Leo
 * @see CPDisplayLayout
 * @generated
 */
public class CPDisplayLayoutTable extends BaseTable<CPDisplayLayoutTable> {

	public static final CPDisplayLayoutTable INSTANCE =
		new CPDisplayLayoutTable();

	public final Column<CPDisplayLayoutTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPDisplayLayoutTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPDisplayLayoutTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, Long> CPDisplayLayoutId =
		createColumn(
			"CPDisplayLayoutId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPDisplayLayoutTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, String>
		layoutPageTemplateEntryUuid = createColumn(
			"layoutPageTemplateEntryUuid", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPDisplayLayoutTable, String> layoutUuid = createColumn(
		"layoutUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CPDisplayLayoutTable() {
		super("CPDisplayLayout", CPDisplayLayoutTable::new);
	}

}