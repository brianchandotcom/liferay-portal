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
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KaleoAction&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoAction
 * @generated
 */
public class KaleoActionTable extends BaseTable<KaleoActionTable> {

	public static final KaleoActionTable INSTANCE = new KaleoActionTable();

	public final Column<KaleoActionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoActionTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoActionTable, Long> kaleoActionId = createColumn(
		"kaleoActionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoActionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, String> kaleoClassName = createColumn(
		"kaleoClassName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, Long> kaleoClassPK = createColumn(
		"kaleoClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, Long> kaleoDefinitionId =
		createColumn(
			"kaleoDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, Long> kaleoDefinitionVersionId =
		createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, String> kaleoNodeName = createColumn(
		"kaleoNodeName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, String> executionType = createColumn(
		"executionType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, Clob> script = createColumn(
		"script", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, String> scriptLanguage = createColumn(
		"scriptLanguage", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, String> scriptRequiredContexts =
		createColumn(
			"scriptRequiredContexts", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoActionTable, Integer> priority = createColumn(
		"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private KaleoActionTable() {
		super("KaleoAction", KaleoActionTable::new);
	}

}