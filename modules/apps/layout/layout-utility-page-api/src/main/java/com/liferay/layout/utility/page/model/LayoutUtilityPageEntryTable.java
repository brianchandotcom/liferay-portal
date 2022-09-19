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

package com.liferay.layout.utility.page.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;LayoutUtilityPageEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutUtilityPageEntry
 * @generated
 */
public class LayoutUtilityPageEntryTable
	extends BaseTable<LayoutUtilityPageEntryTable> {

	public static final LayoutUtilityPageEntryTable INSTANCE =
		new LayoutUtilityPageEntryTable();

	public final Column<LayoutUtilityPageEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutUtilityPageEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutUtilityPageEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, Long>
		LayoutUtilityPageEntryId = createColumn(
			"LayoutUtilityPageEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<LayoutUtilityPageEntryTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, Boolean>
		defaultLayoutUtilityPageEntry = createColumn(
			"defaultLayoutUtilityPageEntry", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, Integer> type =
		createColumn(
			"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<LayoutUtilityPageEntryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private LayoutUtilityPageEntryTable() {
		super("LayoutUtilityPageEntry", LayoutUtilityPageEntryTable::new);
	}

}