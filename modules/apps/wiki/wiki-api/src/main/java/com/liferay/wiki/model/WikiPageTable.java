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

package com.liferay.wiki.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;WikiPage&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPage
 * @generated
 */
public class WikiPageTable extends BaseTable<WikiPageTable> {

	public static final WikiPageTable INSTANCE = new WikiPageTable();

	public final Column<WikiPageTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<WikiPageTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Long> pageId = createColumn(
		"pageId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WikiPageTable, Long> resourcePrimKey = createColumn(
		"resourcePrimKey", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Long> nodeId = createColumn(
		"nodeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Double> version = createColumn(
		"version", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Boolean> minorEdit = createColumn(
		"minorEdit", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Clob> content = createColumn(
		"content", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, String> summary = createColumn(
		"summary", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, String> format = createColumn(
		"format", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Boolean> head = createColumn(
		"head", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, String> parentTitle = createColumn(
		"parentTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, String> redirectTitle = createColumn(
		"redirectTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, String> statusByUserName = createColumn(
		"statusByUserName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WikiPageTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private WikiPageTable() {
		super("WikiPage", WikiPageTable::new);
	}

}