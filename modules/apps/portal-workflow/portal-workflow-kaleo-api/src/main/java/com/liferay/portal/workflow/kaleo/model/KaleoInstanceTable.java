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

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the KaleoInstance.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoInstanceTable extends Table<KaleoInstanceTable> {

	public static final KaleoInstanceTable INSTANCE = new KaleoInstanceTable();

	public final Column<KaleoInstanceTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTable, Long> kaleoInstanceId =
		createColumn("kaleoInstanceId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<KaleoInstanceTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoInstanceTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoInstanceTable, Long> kaleoDefinitionVersionId =
		createColumn("kaleoDefinitionVersionId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTable, String> kaleoDefinitionName =
		createColumn("kaleoDefinitionName", String.class, Types.VARCHAR);
	public final Column<KaleoInstanceTable, Integer> kaleoDefinitionVersion =
		createColumn("kaleoDefinitionVersion", Integer.class, Types.INTEGER);
	public final Column<KaleoInstanceTable, Long> rootKaleoInstanceTokenId =
		createColumn("rootKaleoInstanceTokenId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTable, String> className = createColumn(
		"className", String.class, Types.VARCHAR);
	public final Column<KaleoInstanceTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTable, Boolean> completed = createColumn(
		"completed", Boolean.class, Types.BOOLEAN);
	public final Column<KaleoInstanceTable, Date> completionDate = createColumn(
		"completionDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoInstanceTable, Clob> workflowContext =
		createColumn("workflowContext", Clob.class, Types.CLOB);

	private KaleoInstanceTable() {
		super("KaleoInstance", KaleoInstanceTable::new);
	}

}