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

package com.liferay.segments.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SegmentsExperimentRel&quot; database table.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentRel
 * @generated
 */
public class SegmentsExperimentRelTable
	extends BaseTable<SegmentsExperimentRelTable> {

	public static final SegmentsExperimentRelTable INSTANCE =
		new SegmentsExperimentRelTable();

	public final Column<SegmentsExperimentRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SegmentsExperimentRelTable, Long>
		segmentsExperimentRelId = createColumn(
			"segmentsExperimentRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SegmentsExperimentRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentRelTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentRelTable, Long> segmentsExperimentId =
		createColumn(
			"segmentsExperimentId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentRelTable, Long> segmentsExperienceId =
		createColumn(
			"segmentsExperienceId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentRelTable, Double> split =
		createColumn("split", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private SegmentsExperimentRelTable() {
		super("SegmentsExperimentRel", SegmentsExperimentRelTable::new);
	}

}