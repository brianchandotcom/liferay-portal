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
 * The table class for the KaleoTransition.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTransitionTable extends Table<KaleoTransitionTable> {

	public static final KaleoTransitionTable INSTANCE =
		new KaleoTransitionTable();

	public final Column<KaleoTransitionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<KaleoTransitionTable, Long> kaleoTransitionId =
		createColumn("kaleoTransitionId", Long.class, Types.BIGINT);
	public final Column<KaleoTransitionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<KaleoTransitionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<KaleoTransitionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<KaleoTransitionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<KaleoTransitionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoTransitionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoTransitionTable, Long> kaleoDefinitionVersionId =
		createColumn("kaleoDefinitionVersionId", Long.class, Types.BIGINT);
	public final Column<KaleoTransitionTable, Long> kaleoNodeId = createColumn(
		"kaleoNodeId", Long.class, Types.BIGINT);
	public final Column<KaleoTransitionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<KaleoTransitionTable, String> description =
		createColumn("description", String.class, Types.VARCHAR);
	public final Column<KaleoTransitionTable, Long> sourceKaleoNodeId =
		createColumn("sourceKaleoNodeId", Long.class, Types.BIGINT);
	public final Column<KaleoTransitionTable, String> sourceKaleoNodeName =
		createColumn("sourceKaleoNodeName", String.class, Types.VARCHAR);
	public final Column<KaleoTransitionTable, Long> targetKaleoNodeId =
		createColumn("targetKaleoNodeId", Long.class, Types.BIGINT);
	public final Column<KaleoTransitionTable, String> targetKaleoNodeName =
		createColumn("targetKaleoNodeName", String.class, Types.VARCHAR);
	public final Column<KaleoTransitionTable, Boolean> defaultTransition =
		createColumn("defaultTransition", Boolean.class, Types.BOOLEAN);

	private KaleoTransitionTable() {
		super("KaleoTransition", KaleoTransitionTable::new);
	}

}