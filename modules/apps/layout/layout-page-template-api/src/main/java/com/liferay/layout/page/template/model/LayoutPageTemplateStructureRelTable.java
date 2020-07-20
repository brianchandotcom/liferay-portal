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

	private LayoutPageTemplateStructureRelTable() {
		super(
			"LayoutPageTemplateStructureRel",
			LayoutPageTemplateStructureRelTable::new);
	}

}