/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KaleoNotification&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotification
 * @generated
 */
public class KaleoNotificationTable extends BaseTable<KaleoNotificationTable> {

	public static final KaleoNotificationTable INSTANCE =
		new KaleoNotificationTable();

	public final Column<KaleoNotificationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoNotificationTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoNotificationTable, Long> kaleoNotificationId =
		createColumn(
			"kaleoNotificationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<KaleoNotificationTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, String> kaleoClassName =
		createColumn(
			"kaleoClassName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, Long> kaleoClassPK =
		createColumn(
			"kaleoClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, Long> kaleoDefinitionId =
		createColumn(
			"kaleoDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, Long> kaleoDefinitionVersionId =
		createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, String> kaleoNodeName =
		createColumn(
			"kaleoNodeName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, String> executionType =
		createColumn(
			"executionType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, Clob> template = createColumn(
		"template", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, String> templateLanguage =
		createColumn(
			"templateLanguage", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoNotificationTable, String> notificationTypes =
		createColumn(
			"notificationTypes", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private KaleoNotificationTable() {
		super("KaleoNotification", KaleoNotificationTable::new);
	}

}