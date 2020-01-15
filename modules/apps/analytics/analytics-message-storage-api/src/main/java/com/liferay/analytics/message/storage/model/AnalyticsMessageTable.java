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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Blob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the AnalyticsMessage.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnalyticsMessageTable extends Table<AnalyticsMessageTable> {

	public static final AnalyticsMessageTable INSTANCE =
		new AnalyticsMessageTable();

	public final Column<AnalyticsMessageTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<AnalyticsMessageTable, Long> analyticsMessageId =
		createColumn("analyticsMessageId", Long.class, Types.BIGINT);
	public final Column<AnalyticsMessageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<AnalyticsMessageTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<AnalyticsMessageTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<AnalyticsMessageTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<AnalyticsMessageTable, Blob> body = createColumn(
		"body", Blob.class, Types.BLOB);

	private AnalyticsMessageTable() {
		super("AnalyticsMessage", AnalyticsMessageTable::new);
	}

}