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
 * The table class for the KaleoTimerInstanceToken.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTimerInstanceTokenTable
	extends Table<KaleoTimerInstanceTokenTable> {

	public static final KaleoTimerInstanceTokenTable INSTANCE =
		new KaleoTimerInstanceTokenTable();

	public final Column<KaleoTimerInstanceTokenTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Long>
		kaleoTimerInstanceTokenId = createColumn(
			"kaleoTimerInstanceTokenId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<KaleoTimerInstanceTokenTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoTimerInstanceTokenTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoTimerInstanceTokenTable, String> kaleoClassName =
		createColumn("kaleoClassName", String.class, Types.VARCHAR);
	public final Column<KaleoTimerInstanceTokenTable, Long> kaleoClassPK =
		createColumn("kaleoClassPK", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Long>
		kaleoDefinitionVersionId = createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Long> kaleoInstanceId =
		createColumn("kaleoInstanceId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Long>
		kaleoInstanceTokenId = createColumn(
			"kaleoInstanceTokenId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Long>
		kaleoTaskInstanceTokenId = createColumn(
			"kaleoTaskInstanceTokenId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Long> kaleoTimerId =
		createColumn("kaleoTimerId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, String> kaleoTimerName =
		createColumn("kaleoTimerName", String.class, Types.VARCHAR);
	public final Column<KaleoTimerInstanceTokenTable, Boolean> blocking =
		createColumn("blocking", Boolean.class, Types.BOOLEAN);
	public final Column<KaleoTimerInstanceTokenTable, Long> completionUserId =
		createColumn("completionUserId", Long.class, Types.BIGINT);
	public final Column<KaleoTimerInstanceTokenTable, Boolean> completed =
		createColumn("completed", Boolean.class, Types.BOOLEAN);
	public final Column<KaleoTimerInstanceTokenTable, Date> completionDate =
		createColumn("completionDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoTimerInstanceTokenTable, Clob> workflowContext =
		createColumn("workflowContext", Clob.class, Types.CLOB);

	private KaleoTimerInstanceTokenTable() {
		super("KaleoTimerInstanceToken", KaleoTimerInstanceTokenTable::new);
	}

}