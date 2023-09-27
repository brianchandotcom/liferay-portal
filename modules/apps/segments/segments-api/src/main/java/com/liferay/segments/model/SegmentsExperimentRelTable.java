/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public final Column<SegmentsExperimentRelTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
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