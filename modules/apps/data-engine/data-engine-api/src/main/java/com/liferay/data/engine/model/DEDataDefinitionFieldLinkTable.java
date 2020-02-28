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

package com.liferay.data.engine.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DEDataDefinitionFieldLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DEDataDefinitionFieldLink
 * @generated
 */
public class DEDataDefinitionFieldLinkTable
	extends BaseTable<DEDataDefinitionFieldLinkTable> {

	public static final DEDataDefinitionFieldLinkTable INSTANCE =
		new DEDataDefinitionFieldLinkTable();

	public final Column<DEDataDefinitionFieldLinkTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DEDataDefinitionFieldLinkTable, Long>
		deDataDefinitionFieldLinkId = createColumn(
			"deDataDefinitionFieldLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DEDataDefinitionFieldLinkTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DEDataDefinitionFieldLinkTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DEDataDefinitionFieldLinkTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DEDataDefinitionFieldLinkTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DEDataDefinitionFieldLinkTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DEDataDefinitionFieldLinkTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DEDataDefinitionFieldLinkTable, Long> ddmStructureId =
		createColumn(
			"ddmStructureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DEDataDefinitionFieldLinkTable, String> fieldName =
		createColumn(
			"fieldName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DEDataDefinitionFieldLinkTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private DEDataDefinitionFieldLinkTable() {
		super("DEDataDefinitionFieldLink", DEDataDefinitionFieldLinkTable::new);
	}

}