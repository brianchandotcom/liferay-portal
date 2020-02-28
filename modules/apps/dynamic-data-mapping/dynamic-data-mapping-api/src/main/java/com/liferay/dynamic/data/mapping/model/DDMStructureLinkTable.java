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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DDMStructureLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLink
 * @generated
 */
public class DDMStructureLinkTable extends BaseTable<DDMStructureLinkTable> {

	public static final DDMStructureLinkTable INSTANCE =
		new DDMStructureLinkTable();

	public final Column<DDMStructureLinkTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMStructureLinkTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMStructureLinkTable, Long> structureLinkId =
		createColumn(
			"structureLinkId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMStructureLinkTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLinkTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLinkTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLinkTable, Long> structureId = createColumn(
		"structureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DDMStructureLinkTable() {
		super("DDMStructureLink", DDMStructureLinkTable::new);
	}

}