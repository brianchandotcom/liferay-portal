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

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectRelationship&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectRelationship
 * @generated
 */
public class ObjectRelationshipTable
	extends BaseTable<ObjectRelationshipTable> {

	public static final ObjectRelationshipTable INSTANCE =
		new ObjectRelationshipTable();

	public final Column<ObjectRelationshipTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectRelationshipTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, Long> objectRelationshipId =
		createColumn(
			"objectRelationshipId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectRelationshipTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, Long> objectDefinitionId1 =
		createColumn(
			"objectDefinitionId1", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, Long> objectDefinitionId2 =
		createColumn(
			"objectDefinitionId2", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, Long> objectFieldId2 =
		createColumn(
			"objectFieldId2", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, String> dbTableName =
		createColumn(
			"dbTableName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, String> label = createColumn(
		"label", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectRelationshipTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ObjectRelationshipTable() {
		super("ObjectRelationship", ObjectRelationshipTable::new);
	}

}