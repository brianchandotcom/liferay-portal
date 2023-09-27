/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AnalyticsDeleteMessage&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsDeleteMessage
 * @generated
 */
public class AnalyticsDeleteMessageTable
	extends BaseTable<AnalyticsDeleteMessageTable> {

	public static final AnalyticsDeleteMessageTable INSTANCE =
		new AnalyticsDeleteMessageTable();

	public final Column<AnalyticsDeleteMessageTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AnalyticsDeleteMessageTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AnalyticsDeleteMessageTable, Long>
		analyticsDeleteMessageId = createColumn(
			"analyticsDeleteMessageId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AnalyticsDeleteMessageTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnalyticsDeleteMessageTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnalyticsDeleteMessageTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AnalyticsDeleteMessageTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AnalyticsDeleteMessageTable, String> className =
		createColumn(
			"className", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AnalyticsDeleteMessageTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AnalyticsDeleteMessageTable() {
		super("AnalyticsDeleteMessage", AnalyticsDeleteMessageTable::new);
	}

}