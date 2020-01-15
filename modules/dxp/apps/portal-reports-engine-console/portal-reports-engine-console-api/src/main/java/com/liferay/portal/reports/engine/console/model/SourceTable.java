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

package com.liferay.portal.reports.engine.console.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the Source.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SourceTable extends Table<SourceTable> {

	public static final SourceTable INSTANCE = new SourceTable();

	public final Column<SourceTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<SourceTable, Long> sourceId = createColumn(
		"sourceId", Long.class, Types.BIGINT);
	public final Column<SourceTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<SourceTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SourceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SourceTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<SourceTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<SourceTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<SourceTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);
	public final Column<SourceTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<SourceTable, String> driverClassName = createColumn(
		"driverClassName", String.class, Types.VARCHAR);
	public final Column<SourceTable, String> driverUrl = createColumn(
		"driverUrl", String.class, Types.VARCHAR);
	public final Column<SourceTable, String> driverUserName = createColumn(
		"driverUserName", String.class, Types.VARCHAR);
	public final Column<SourceTable, String> driverPassword = createColumn(
		"driverPassword", String.class, Types.VARCHAR);

	private SourceTable() {
		super("Reports_Source", SourceTable::new);
	}

}