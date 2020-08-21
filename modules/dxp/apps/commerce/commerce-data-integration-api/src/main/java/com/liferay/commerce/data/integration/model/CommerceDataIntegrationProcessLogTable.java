/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CDataIntegrationProcessLog&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLog
 * @generated
 */
public class CommerceDataIntegrationProcessLogTable
	extends BaseTable<CommerceDataIntegrationProcessLogTable> {

	public static final CommerceDataIntegrationProcessLogTable INSTANCE =
		new CommerceDataIntegrationProcessLogTable();

	public final Column<CommerceDataIntegrationProcessLogTable, Long>
		commerceDataIntegrationProcessLogId = createColumn(
			"CDataIntegrationProcessLogId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceDataIntegrationProcessLogTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Long>
		CDataIntegrationProcessId = createColumn(
			"CDataIntegrationProcessId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Clob> error =
		createColumn("error", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Clob> output =
		createColumn("output_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Date>
		startDate = createColumn(
			"startDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Date> endDate =
		createColumn(
			"endDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Integer>
		status = createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CommerceDataIntegrationProcessLogTable() {
		super(
			"CDataIntegrationProcessLog",
			CommerceDataIntegrationProcessLogTable::new);
	}

}