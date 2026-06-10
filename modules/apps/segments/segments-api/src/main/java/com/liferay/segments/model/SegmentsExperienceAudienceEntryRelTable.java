/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SExperienceAudienceEntryRel&quot; database table.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceAudienceEntryRel
 * @generated
 */
public class SegmentsExperienceAudienceEntryRelTable
	extends BaseTable<SegmentsExperienceAudienceEntryRelTable> {

	public static final SegmentsExperienceAudienceEntryRelTable INSTANCE =
		new SegmentsExperienceAudienceEntryRelTable();

	public final Column<SegmentsExperienceAudienceEntryRelTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SegmentsExperienceAudienceEntryRelTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SegmentsExperienceAudienceEntryRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperienceAudienceEntryRelTable, Long>
		segmentsExperienceAudienceEntryRelId = createColumn(
			"sExperienceAudienceEntryRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SegmentsExperienceAudienceEntryRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperienceAudienceEntryRelTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperienceAudienceEntryRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperienceAudienceEntryRelTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperienceAudienceEntryRelTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperienceAudienceEntryRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperienceAudienceEntryRelTable, String>
		audienceEntryERC = createColumn(
			"audienceEntryERC", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SegmentsExperienceAudienceEntryRelTable, Integer>
		priority = createColumn(
			"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SegmentsExperienceAudienceEntryRelTable, String>
		segmentsExperienceERC = createColumn(
			"segmentsExperienceERC", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private SegmentsExperienceAudienceEntryRelTable() {
		super(
			"SExperienceAudienceEntryRel",
			SegmentsExperienceAudienceEntryRelTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:653045694