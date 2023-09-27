/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.petra.sql.dsl;

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