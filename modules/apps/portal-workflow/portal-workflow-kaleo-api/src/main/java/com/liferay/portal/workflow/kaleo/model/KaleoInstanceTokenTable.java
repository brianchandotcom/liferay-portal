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
 * The table class for the KaleoInstanceToken.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoInstanceTokenTable extends Table<KaleoInstanceTokenTable> {

	public static final KaleoInstanceTokenTable INSTANCE =
		new KaleoInstanceTokenTable();

	public final Column<KaleoInstanceTokenTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, Long> kaleoInstanceTokenId =
		createColumn("kaleoInstanceTokenId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<KaleoInstanceTokenTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoInstanceTokenTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoInstanceTokenTable, Long>
		kaleoDefinitionVersionId = createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, Long> kaleoInstanceId =
		createColumn("kaleoInstanceId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, Long>
		parentKaleoInstanceTokenId = createColumn(
			"parentKaleoInstanceTokenId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, Long> currentKaleoNodeId =
		createColumn("currentKaleoNodeId", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, String> currentKaleoNodeName =
		createColumn("currentKaleoNodeName", String.class, Types.VARCHAR);
	public final Column<KaleoInstanceTokenTable, String> className =
		createColumn("className", String.class, Types.VARCHAR);
	public final Column<KaleoInstanceTokenTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<KaleoInstanceTokenTable, Boolean> completed =
		createColumn("completed", Boolean.class, Types.BOOLEAN);
	public final Column<KaleoInstanceTokenTable, Date> completionDate =
		createColumn("completionDate", Date.class, Types.TIMESTAMP);

	private KaleoInstanceTokenTable() {
		super("KaleoInstanceToken", KaleoInstanceTokenTable::new);
	}

}