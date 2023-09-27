/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Blob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AnalyticsMessage&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessage
 * @generated
 */
public class AnalyticsMessageTable extends BaseTable<AnalyticsMessageTable> {

	public static final AnalyticsMessageTable INSTANCE =
		new AnalyticsMessageTable();

	public final Column<AnalyticsMessageTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AnalyticsMessageTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AnalyticsMessageTable, Long> analyticsMessageId =
		createColumn(
			"analyticsMessageId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AnalyticsMessageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnalyticsMessageTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnalyticsMessageTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AnalyticsMessageTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AnalyticsMessageTable, Blob> body = createColumn(
		"body", Blob.class, Types.BLOB, Column.FLAG_DEFAULT);

	private AnalyticsMessageTable() {
		super("AnalyticsMessage", AnalyticsMessageTable::new);
	}

}