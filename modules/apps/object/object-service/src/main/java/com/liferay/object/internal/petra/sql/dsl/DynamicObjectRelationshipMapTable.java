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

package com.liferay.object.internal.petra.sql.dsl;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * @author Marco Leo
 */
public class DynamicObjectRelationshipMapTable
	extends BaseTable<DynamicObjectRelationshipMapTable> {

	public DynamicObjectRelationshipMapTable(
		String pkObjectFieldDBColumnName1, String pkObjectFieldDBColumnName2,
		String tableName) {

		super(tableName, () -> null);

		_pkObjectFieldDBColumnName1 = pkObjectFieldDBColumnName1;
		_pkObjectFieldDBColumnName2 = pkObjectFieldDBColumnName2;

		createColumn(
			_pkObjectFieldDBColumnName1, Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
		createColumn(
			_pkObjectFieldDBColumnName2, Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	}

	public Column<DynamicObjectRelationshipMapTable, Long>
		getPrimaryKeyColumn1() {

		return (Column<DynamicObjectRelationshipMapTable, Long>)getColumn(
			_pkObjectFieldDBColumnName1);
	}

	public Column<DynamicObjectRelationshipMapTable, Long>
		getPrimaryKeyColumn2() {

		return (Column<DynamicObjectRelationshipMapTable, Long>)getColumn(
			_pkObjectFieldDBColumnName2);
	}

	private final String _pkObjectFieldDBColumnName1;
	private final String _pkObjectFieldDBColumnName2;

}