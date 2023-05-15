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

package com.liferay.object.petra.sql.dsl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Feliphe Marinho
 */
public class DynamicObjectDefinitionLocalizationTable
	extends BaseTable<DynamicObjectDefinitionLocalizationTable> {

	public DynamicObjectDefinitionLocalizationTable(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		super(objectDefinition.getLocalizationDBTableName(), () -> null);

		_objectDefinition = objectDefinition;
		_objectFields = objectFields;

		_tableName = objectDefinition.getLocalizationDBTableName();

		String primaryKeyColumnName = TextFormatter.format(
			objectDefinition.getShortName() + "_l10nId", TextFormatter.I);

		_primaryKeyColumnName = "c_" + primaryKeyColumnName;

		_foreignKeyColumnName = objectDefinition.getPKObjectFieldDBColumnName();

		createColumn(
			_foreignKeyColumnName, Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

		createColumn(
			_primaryKeyColumnName, Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);

		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

		for (ObjectField objectField : objectFields) {
			createColumn(
				objectField.getDBColumnName(),
				DataType.getJavaClass(objectField.getDBType()),
				DataType.getSQLType(objectField.getDBType()),
				Column.FLAG_DEFAULT);
		}

		_objectFieldColumns = ListUtil.filter(
			new ArrayList<>(getColumns()),
			column -> {
				if (column.equals(getForeignKeyColumn()) ||
					column.equals(getLanguageIdColumn()) ||
					column.equals(_getPrimaryKeyColumn())) {

					return false;
				}

				return true;
			});
	}

	public String getCreateTableSQL() {
		StringBundler sb = new StringBundler();

		sb.append("create table ");
		sb.append(_tableName);
		sb.append(" (");
		sb.append(_primaryKeyColumnName);
		sb.append(" LONG not null primary key, ");
		sb.append(_foreignKeyColumnName);
		sb.append(" LONG not null, languageId VARCHAR(10) not null");

		for (ObjectField objectField : _objectFields) {
			sb.append(", ");
			sb.append(objectField.getDBColumnName());
			sb.append(" ");
			sb.append(DataType.getDBType(objectField.getDBType()));
		}

		sb.append(")");

		String sql = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		return sql;
	}

	public Column<DynamicObjectDefinitionLocalizationTable, Long>
		getForeignKeyColumn() {

		return (Column<DynamicObjectDefinitionLocalizationTable, Long>)
			getColumn(_foreignKeyColumnName);
	}

	public String getForeignKeyColumnName() {
		Column<DynamicObjectDefinitionLocalizationTable, Long>
			foreignKeyColumn = getForeignKeyColumn();

		return foreignKeyColumn.getName();
	}

	public Column<DynamicObjectDefinitionLocalizationTable, String>
		getLanguageIdColumn() {

		return (Column<DynamicObjectDefinitionLocalizationTable, String>)
			getColumn("languageId");
	}

	public ObjectDefinition getObjectDefinition() {
		return _objectDefinition;
	}

	public List<Column<DynamicObjectDefinitionLocalizationTable, ?>>
		getObjectFieldColumns() {

		return _objectFieldColumns;
	}

	public List<ObjectField> getObjectFields() {
		return _objectFields;
	}

	public String getPrimaryKeyColumnName() {
		Column<DynamicObjectDefinitionLocalizationTable, Long>
			primaryKeyColumn = _getPrimaryKeyColumn();

		return primaryKeyColumn.getName();
	}

	private Column<DynamicObjectDefinitionLocalizationTable, Long>
		_getPrimaryKeyColumn() {

		return (Column<DynamicObjectDefinitionLocalizationTable, Long>)
			getColumn(_primaryKeyColumnName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicObjectDefinitionLocalizationTable.class);

	private final String _foreignKeyColumnName;
	private final ObjectDefinition _objectDefinition;
	private final List<Column<DynamicObjectDefinitionLocalizationTable, ?>>
		_objectFieldColumns;
	private final List<ObjectField> _objectFields;
	private final String _primaryKeyColumnName;
	private final String _tableName;

}