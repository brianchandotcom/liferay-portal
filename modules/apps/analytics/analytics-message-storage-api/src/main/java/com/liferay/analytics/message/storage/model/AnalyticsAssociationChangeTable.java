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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AnalyticsAssociationChange&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsAssociationChange
 * @generated
 */
public class AnalyticsAssociationChangeTable
	extends BaseTable<AnalyticsAssociationChangeTable> {

	public static final AnalyticsAssociationChangeTable INSTANCE =
		new AnalyticsAssociationChangeTable();

	public final Column<AnalyticsAssociationChangeTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AnalyticsAssociationChangeTable, Long>
		analyticsAssociationChangeId = createColumn(
			"analyticsAssociationChangeId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AnalyticsAssociationChangeTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnalyticsAssociationChangeTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AnalyticsAssociationChangeTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AnalyticsAssociationChangeTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnalyticsAssociationChangeTable, String>
		associationClassName = createColumn(
			"associationClassName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<AnalyticsAssociationChangeTable, Long>
		associationClassPK = createColumn(
			"associationClassPK", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AnalyticsAssociationChangeTable, String> className =
		createColumn(
			"className", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AnalyticsAssociationChangeTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AnalyticsAssociationChangeTable() {
		super(
			"AnalyticsAssociationChange", AnalyticsAssociationChangeTable::new);
	}

}