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

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the KaleoNode.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoNodeTable extends Table<KaleoNodeTable> {

	public static final KaleoNodeTable INSTANCE = new KaleoNodeTable();

	public final Column<KaleoNodeTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<KaleoNodeTable, Long> kaleoNodeId = createColumn(
		"kaleoNodeId", Long.class, Types.BIGINT);
	public final Column<KaleoNodeTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<KaleoNodeTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<KaleoNodeTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<KaleoNodeTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<KaleoNodeTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoNodeTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoNodeTable, Long> kaleoDefinitionVersionId =
		createColumn("kaleoDefinitionVersionId", Long.class, Types.BIGINT);
	public final Column<KaleoNodeTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<KaleoNodeTable, String> metadata = createColumn(
		"metadata", String.class, Types.VARCHAR);
	public final Column<KaleoNodeTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<KaleoNodeTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR);
	public final Column<KaleoNodeTable, Boolean> initial = createColumn(
		"initial_", Boolean.class, Types.BOOLEAN);
	public final Column<KaleoNodeTable, Boolean> terminal = createColumn(
		"terminal", Boolean.class, Types.BOOLEAN);

	private KaleoNodeTable() {
		super("KaleoNode", KaleoNodeTable::new);
	}

}