/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KaleoTaskAssignmentInstance&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignmentInstance
 * @generated
 */
public class KaleoTaskAssignmentInstanceTable
	extends BaseTable<KaleoTaskAssignmentInstanceTable> {

	public static final KaleoTaskAssignmentInstanceTable INSTANCE =
		new KaleoTaskAssignmentInstanceTable();

	public final Column<KaleoTaskAssignmentInstanceTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoTaskAssignmentInstanceTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoTaskAssignmentInstanceTable, Long>
		kaleoTaskAssignmentInstanceId = createColumn(
			"kaleoTaskAssignmentInstanceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<KaleoTaskAssignmentInstanceTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Long>
		kaleoDefinitionId = createColumn(
			"kaleoDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Long>
		kaleoDefinitionVersionId = createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Long>
		kaleoInstanceId = createColumn(
			"kaleoInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Long>
		kaleoInstanceTokenId = createColumn(
			"kaleoInstanceTokenId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Long>
		kaleoTaskInstanceTokenId = createColumn(
			"kaleoTaskInstanceTokenId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Long> kaleoTaskId =
		createColumn(
			"kaleoTaskId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, String>
		kaleoTaskName = createColumn(
			"kaleoTaskName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, String>
		assigneeClassName = createColumn(
			"assigneeClassName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Long>
		assigneeClassPK = createColumn(
			"assigneeClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Boolean> completed =
		createColumn(
			"completed", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentInstanceTable, Date> completionDate =
		createColumn(
			"completionDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private KaleoTaskAssignmentInstanceTable() {
		super(
			"KaleoTaskAssignmentInstance",
			KaleoTaskAssignmentInstanceTable::new);
	}

}