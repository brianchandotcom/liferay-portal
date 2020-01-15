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
 * The table class for the KaleoNotification.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoNotificationTable extends Table<KaleoNotificationTable> {

	public static final KaleoNotificationTable INSTANCE =
		new KaleoNotificationTable();

	public final Column<KaleoNotificationTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationTable, Long> kaleoNotificationId =
		createColumn("kaleoNotificationId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoNotificationTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<KaleoNotificationTable, String> kaleoClassName =
		createColumn("kaleoClassName", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationTable, Long> kaleoClassPK =
		createColumn("kaleoClassPK", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationTable, Long> kaleoDefinitionVersionId =
		createColumn("kaleoDefinitionVersionId", Long.class, Types.BIGINT);
	public final Column<KaleoNotificationTable, String> kaleoNodeName =
		createColumn("kaleoNodeName", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationTable, String> description =
		createColumn("description", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationTable, String> executionType =
		createColumn("executionType", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationTable, Clob> template = createColumn(
		"template", Clob.class, Types.CLOB);
	public final Column<KaleoNotificationTable, String> templateLanguage =
		createColumn("templateLanguage", String.class, Types.VARCHAR);
	public final Column<KaleoNotificationTable, String> notificationTypes =
		createColumn("notificationTypes", String.class, Types.VARCHAR);

	private KaleoNotificationTable() {
		super("KaleoNotification", KaleoNotificationTable::new);
	}

}