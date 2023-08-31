/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;LayoutPageTemplateCollection&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollection
 * @generated
 */
public class LayoutPageTemplateCollectionTable
	extends BaseTable<LayoutPageTemplateCollectionTable> {

	public static final LayoutPageTemplateCollectionTable INSTANCE =
		new LayoutPageTemplateCollectionTable();

	public final Column<LayoutPageTemplateCollectionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutPageTemplateCollectionTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutPageTemplateCollectionTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, Long>
		layoutPageTemplateCollectionId = createColumn(
			"layoutPageTemplateCollectionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<LayoutPageTemplateCollectionTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, Long>
		parentLayoutPageTemplateCollectionId = createColumn(
			"parentLPTCollectionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, String>
		layoutPageTemplateCollectionKey = createColumn(
			"lptCollectionKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, Integer> type =
		createColumn(
			"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateCollectionTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private LayoutPageTemplateCollectionTable() {
		super(
			"LayoutPageTemplateCollection",
			LayoutPageTemplateCollectionTable::new);
	}

}