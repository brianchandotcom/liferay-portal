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

package com.liferay.commerce.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPDAvailabilityEstimate&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimate
 * @generated
 */
public class CPDAvailabilityEstimateTable
	extends BaseTable<CPDAvailabilityEstimateTable> {

	public static final CPDAvailabilityEstimateTable INSTANCE =
		new CPDAvailabilityEstimateTable();

	public final Column<CPDAvailabilityEstimateTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPDAvailabilityEstimateTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDAvailabilityEstimateTable, Long>
		CPDAvailabilityEstimateId = createColumn(
			"CPDAvailabilityEstimateId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDAvailabilityEstimateTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDAvailabilityEstimateTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDAvailabilityEstimateTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDAvailabilityEstimateTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDAvailabilityEstimateTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDAvailabilityEstimateTable, Long>
		commerceAvailabilityEstimateId = createColumn(
			"commerceAvailabilityEstimateId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPDAvailabilityEstimateTable, Long> CProductId =
		createColumn(
			"CProductId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDAvailabilityEstimateTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private CPDAvailabilityEstimateTable() {
		super("CPDAvailabilityEstimate", CPDAvailabilityEstimateTable::new);
	}

}