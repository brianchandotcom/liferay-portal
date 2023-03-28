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

package com.liferay.object.dsl;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * @author Marco Leo
 */
public class DynamicObjectRelationshipMappingTable
	extends BaseTable<DynamicObjectRelationshipMappingTable> {

	public DynamicObjectRelationshipMappingTable(
		String primaryKeyColumnName1, String primaryKeyColumnName2,
		String tableName) {

		super(tableName, () -> null);

		_primaryKeyColumnName1 = primaryKeyColumnName1;
		_primaryKeyColumnName2 = primaryKeyColumnName2;

		createColumn(
			_primaryKeyColumnName1, Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
		createColumn(
			_primaryKeyColumnName2, Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	}

	public Column<DynamicObjectRelationshipMappingTable, Long>
		getPrimaryKeyColumn1() {

		return (Column<DynamicObjectRelationshipMappingTable, Long>)getColumn(
			_primaryKeyColumnName1);
	}

	public Column<DynamicObjectRelationshipMappingTable, Long>
		getPrimaryKeyColumn2() {

		return (Column<DynamicObjectRelationshipMappingTable, Long>)getColumn(
			_primaryKeyColumnName2);
	}

	private final String _primaryKeyColumnName1;
	private final String _primaryKeyColumnName2;

}