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

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.math.BigDecimal;

import java.sql.Blob;
import java.sql.Types;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class DynamicObjectDefinitionTable
	extends BaseTable<DynamicObjectDefinitionTable> {

	/**
	 * @see com.liferay.portal.dao.db.BaseDB#alterTableAddColumn(
	 *      java.sql.Connection, String, String, String)
	 */
	public static String getAlterTableAddColumnSQL(
		String tableName, String columnName, String type) {

		String sql = StringBundler.concat(
			"alter table ", tableName, " add ", columnName, StringPool.SPACE,
			_getDataType(type), _getSQLColumnNull(type));

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		return sql;
	}

	/**
	 * @see com.liferay.portal.dao.db.BaseDB#alterTableDropColumn(
	 *      java.sql.Connection, String, String)
	 */
	public static String getAlterTableDropColumnSQL(
		String tableName, String columnName) {

		String sql = StringBundler.concat(
			"alter table ", tableName, " drop column ", columnName);

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		return sql;
	}

	public static Class<?> getJavaClass(String type) {
		Class<?> javaClass = _javaClasses.get(type);

		if (javaClass == null) {
			throw new IllegalArgumentException("Invalid type " + type);
		}

		return javaClass;
	}

	public static Integer getSQLType(String type) {
		Integer sqlType = _sqlTypes.get(type);

		if (sqlType == null) {
			throw new IllegalArgumentException("Invalid type " + type);
		}

		return sqlType;
	}

	public DynamicObjectDefinitionTable(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields,
		String tableName) {

		super(tableName, () -> null);

		_objectDefinition = objectDefinition;
		_objectFields = objectFields;
		_tableName = tableName;

		_primaryKeyColumnName = objectDefinition.getPKObjectFieldDBColumnName();

		createColumn(
			_primaryKeyColumnName, Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);

		for (ObjectField objectField : objectFields) {
			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

				continue;
			}

			createColumn(
				objectField.getDBColumnName(),
				getJavaClass(objectField.getDBType()),
				getSQLType(objectField.getDBType()), Column.FLAG_DEFAULT);
		}
	}

	/**
	 * @see com.liferay.portal.tools.service.builder.ServiceBuilder#_getCreateTableSQL(
	 *      com.liferay.portal.tools.service.builder.Entity)
	 */
	public String getCreateTableSQL() {
		StringBundler sb = new StringBundler();

		sb.append("create table ");
		sb.append(_tableName);
		sb.append(" (");
		sb.append(_primaryKeyColumnName);
		sb.append(" LONG not null primary key");

		for (ObjectField objectField : _objectFields) {
			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

				continue;
			}

			sb.append(", ");
			sb.append(objectField.getDBColumnName());
			sb.append(" ");
			sb.append(_getDataType(objectField.getDBType()));
			sb.append(_getSQLColumnNull(objectField.getDBType()));
		}

		sb.append(")");

		String sql = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		return sql;
	}

	public ObjectDefinition getObjectDefinition() {
		return _objectDefinition;
	}

	public List<ObjectField> getObjectFields() {
		return _objectFields;
	}

	public Column<DynamicObjectDefinitionTable, Long> getPrimaryKeyColumn() {
		return (Column<DynamicObjectDefinitionTable, Long>)getColumn(
			_primaryKeyColumnName);
	}

	public String getPrimaryKeyColumnName() {
		return _primaryKeyColumnName;
	}

	@Override
	protected <C> Column<DynamicObjectDefinitionTable, C> createColumn(
		String name, Class<C> javaClass, int sqlType, int flags) {

		return super.createColumn(name, javaClass, sqlType, flags);
	}

	private static String _getDataType(String type) {
		String dataType = _dataTypes.get(type);

		if (dataType == null) {
			throw new IllegalArgumentException("Invalid type " + type);
		}

		return dataType;
	}

	private static String _getSQLColumnNull(String type) {
		if (type.equals("BigDecimal") || type.equals("Double") ||
			type.equals("Integer") || type.equals("Long")) {

			return " default 0";
		}
		else if (type.equals("Boolean")) {
			return " default FALSE";
		}
		else if (type.equals("Date")) {
			return " null";
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicObjectDefinitionTable.class);

	private static final Map<String, String> _dataTypes = HashMapBuilder.put(
		"BigDecimal", "DECIMAL(30, 16)"
	).put(
		"Blob", "BLOB"
	).put(
		"Boolean", "BOOLEAN"
	).put(
		"Clob", "TEXT"
	).put(
		"Date", "DATE"
	).put(
		"Double", "DOUBLE"
	).put(
		"Integer", "INTEGER"
	).put(
		"Long", "LONG"
	).put(
		"String", "VARCHAR(280)"
	).build();
	private static final Map<String, Class<?>> _javaClasses =
		HashMapBuilder.<String, Class<?>>put(
			"BigDecimal", BigDecimal.class
		).put(
			"Blob", Blob.class
		).put(
			"Boolean", Boolean.class
		).put(
			"Clob", String.class
		).put(
			"Date", Date.class
		).put(
			"Double", Double.class
		).put(
			"Integer", Integer.class
		).put(
			"Long", Long.class
		).put(
			"String", String.class
		).build();
	private static final Map<String, Integer> _sqlTypes = HashMapBuilder.put(
		"BigDecimal", Types.DECIMAL
	).put(
		"Blob", Types.BLOB
	).put(
		"Boolean", Types.BOOLEAN
	).put(
		"Clob", Types.CLOB
	).put(
		"Date", Types.DATE
	).put(
		"Double", Types.DOUBLE
	).put(
		"Integer", Types.INTEGER
	).put(
		"Long", Types.BIGINT
	).put(
		"String", Types.VARCHAR
	).build();

	private final ObjectDefinition _objectDefinition;
	private final List<ObjectField> _objectFields;
	private final String _primaryKeyColumnName;
	private final String _tableName;

}