/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;LayoutPageTemplateStructureRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRel
 * @generated
 */
public class LayoutPageTemplateStructureRelTable
	extends BaseTable<LayoutPageTemplateStructureRelTable> {

	public static final LayoutPageTemplateStructureRelTable INSTANCE =
		new LayoutPageTemplateStructureRelTable();

	public final Column<LayoutPageTemplateStructureRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutPageTemplateStructureRelTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutPageTemplateStructureRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Long>
		layoutPageTemplateStructureRelId = createColumn(
			"lPageTemplateStructureRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<LayoutPageTemplateStructureRelTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Long>
		layoutPageTemplateStructureId = createColumn(
			"layoutPageTemplateStructureId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Long>
		segmentsExperienceId = createColumn(
			"segmentsExperienceId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Clob> data =
		createColumn("data_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Long>
		statusByUserId = createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, String>
		statusByUserName = createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LayoutPageTemplateStructureRelTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private LayoutPageTemplateStructureRelTable() {
		super(
			"LayoutPageTemplateStructureRel",
			LayoutPageTemplateStructureRelTable::new);
	}

}