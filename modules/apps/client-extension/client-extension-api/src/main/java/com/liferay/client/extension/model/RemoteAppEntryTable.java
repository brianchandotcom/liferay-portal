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

package com.liferay.remote.app.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;RemoteAppEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntry
 * @generated
 */
public class RemoteAppEntryTable extends BaseTable<RemoteAppEntryTable> {

	public static final RemoteAppEntryTable INSTANCE =
		new RemoteAppEntryTable();

	public final Column<RemoteAppEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RemoteAppEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Long> remoteAppEntryId =
		createColumn(
			"remoteAppEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RemoteAppEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Clob> customElementCSSURLs =
		createColumn(
			"customElementCSSURLs", Clob.class, Types.CLOB,
			Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String>
		customElementHTMLElementName = createColumn(
			"customElementHTMLElementName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Clob> customElementURLs =
		createColumn(
			"customElementURLs", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Boolean> customElementUseESM =
		createColumn(
			"customElementUseESM", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Clob> description = createColumn(
		"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String> friendlyURLMapping =
		createColumn(
			"friendlyURLMapping", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String> iFrameURL = createColumn(
		"iFrameURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Boolean> instanceable =
		createColumn(
			"instanceable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String> portletCategoryName =
		createColumn(
			"portletCategoryName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Clob> properties = createColumn(
		"properties", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String> sourceCodeURL =
		createColumn(
			"sourceCodeURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<RemoteAppEntryTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private RemoteAppEntryTable() {
		super("RemoteAppEntry", RemoteAppEntryTable::new);
	}

}