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

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SegmentsExperiment&quot; database table.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperiment
 * @generated
 */
public class SegmentsExperimentTable
	extends BaseTable<SegmentsExperimentTable> {

	public static final SegmentsExperimentTable INSTANCE =
		new SegmentsExperimentTable();

	public final Column<SegmentsExperimentTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SegmentsExperimentTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Long> segmentsExperimentId =
		createColumn(
			"segmentsExperimentId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SegmentsExperimentTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Long> segmentsEntryId =
		createColumn(
			"segmentsEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Long> segmentsExperienceId =
		createColumn(
			"segmentsExperienceId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, String> segmentsExperimentKey =
		createColumn(
			"segmentsExperimentKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Clob> typeSettings =
		createColumn(
			"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperimentTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private SegmentsExperimentTable() {
		super("SegmentsExperiment", SegmentsExperimentTable::new);
	}

}