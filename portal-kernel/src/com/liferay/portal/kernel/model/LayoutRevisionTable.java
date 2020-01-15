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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the LayoutRevision.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutRevisionTable extends Table<LayoutRevisionTable> {

	public static final LayoutRevisionTable INSTANCE =
		new LayoutRevisionTable();

	public final Column<LayoutRevisionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, Long> layoutRevisionId =
		createColumn("layoutRevisionId", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<LayoutRevisionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<LayoutRevisionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<LayoutRevisionTable, Long> layoutSetBranchId =
		createColumn("layoutSetBranchId", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, Long> layoutBranchId =
		createColumn("layoutBranchId", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, Long> parentLayoutRevisionId =
		createColumn("parentLayoutRevisionId", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, Boolean> head = createColumn(
		"head", Boolean.class, Types.BOOLEAN);
	public final Column<LayoutRevisionTable, Boolean> major = createColumn(
		"major", Boolean.class, Types.BOOLEAN);
	public final Column<LayoutRevisionTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, Boolean> privateLayout =
		createColumn("privateLayout", Boolean.class, Types.BOOLEAN);
	public final Column<LayoutRevisionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<LayoutRevisionTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR);
	public final Column<LayoutRevisionTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<LayoutRevisionTable, String> keywords = createColumn(
		"keywords", String.class, Types.VARCHAR);
	public final Column<LayoutRevisionTable, String> robots = createColumn(
		"robots", String.class, Types.VARCHAR);
	public final Column<LayoutRevisionTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB);
	public final Column<LayoutRevisionTable, Long> iconImageId = createColumn(
		"iconImageId", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, String> themeId = createColumn(
		"themeId", String.class, Types.VARCHAR);
	public final Column<LayoutRevisionTable, String> colorSchemeId =
		createColumn("colorSchemeId", String.class, Types.VARCHAR);
	public final Column<LayoutRevisionTable, Clob> css = createColumn(
		"css", Clob.class, Types.CLOB);
	public final Column<LayoutRevisionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);
	public final Column<LayoutRevisionTable, Long> statusByUserId =
		createColumn("statusByUserId", Long.class, Types.BIGINT);
	public final Column<LayoutRevisionTable, String> statusByUserName =
		createColumn("statusByUserName", String.class, Types.VARCHAR);
	public final Column<LayoutRevisionTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP);

	private LayoutRevisionTable() {
		super("LayoutRevision", LayoutRevisionTable::new);
	}

}