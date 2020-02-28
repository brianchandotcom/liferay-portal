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