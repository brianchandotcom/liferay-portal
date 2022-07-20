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
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.spi.expression.DSLFunction;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.math.BigDecimal;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class DynamicObjectDefinitionTable
	extends BaseTable<DynamicObjectDefinitionTable> {

	/**
	 * @see com.liferay.portal.kernel.upgrade.UpgradeProcess#AlterTableAddColumn
	 */
	public static String getAlterTableAddColumnSQL(
		String tableName, String columnName, String type) {

		String sql = StringBundler.concat(
			"alter table ", tableName, " add ", columnName, StringPool.SPACE,
			_getDataType(type), _getSQLColumnNull(type), StringPool.SEMICOLON);

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		return sql;
	}

	/**
	 * @see com.liferay.portal.kernel.upgrade.UpgradeProcess#AlterTableDropColumn
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

	public DynamicObjectDefinitionTable(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields,
		String tableName) {

		super(tableName, () -> null);

		_objectFields = objectFields;
		_tableName = tableName;

		_primaryKeyColumnName = objectDefinition.getPKObjectFieldDBColumnName();

		List<Expression<?>> selectExpressions = new ArrayList<>();

		selectExpressions.add(
			createColumn(
				_primaryKeyColumnName, Long.class, Types.BIGINT,
				Column.FLAG_PRIMARY));

		//TODO Use a factory class DynamicObjectDefinitionTableFactory that get the definitionId and inizialize everything
		
		for (ObjectField objectField : objectFields) {

			if(Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

				List<ObjectFieldSetting> objectFieldSettings =
					ObjectFieldSettingLocalServiceUtil.
						getObjectFieldObjectFieldSettings(
							objectField.getObjectFieldId());

				Stream<ObjectFieldSetting> stream =
					objectFieldSettings.stream();

				Map<String, String> objectFieldSettingsValuesMap =
					stream.collect(
						Collectors.toMap(
							ObjectFieldSetting::getName,
							ObjectFieldSetting::getValue));

				String function = GetterUtil.getString(
					objectFieldSettingsValuesMap.get("function"));

				String summarizeField = GetterUtil.getString(
					objectFieldSettingsValuesMap.get("summarizeField"));

				String relationshipName = GetterUtil.getString(
					objectFieldSettingsValuesMap.get("relationship"));



				Expression column = null;

				Expression relatedColumn = null;

				DynamicObjectDefinitionTable relatedTable = null;

				try {

					ObjectRelationship relationship =
						ObjectRelationshipLocalServiceUtil.getObjectRelationship(
							objectDefinition.getObjectDefinitionId(), relationshipName);

					ObjectDefinition objectDefinition2 =
						ObjectDefinitionLocalServiceUtil.getObjectDefinition(
							relationship.getObjectDefinitionId2());

					ObjectField summarizeObjectField =
						ObjectFieldLocalServiceUtil.fetchObjectField(
							objectDefinition2.getObjectDefinitionId(),
							summarizeField);

					relatedTable =
						new DynamicObjectDefinitionTable(
							objectDefinition2,
							ObjectFieldLocalServiceUtil.getObjectFields(
								objectDefinition2.getObjectDefinitionId()),
							summarizeObjectField.getDBTableName());

					ObjectField relatedField =
						ObjectFieldLocalServiceUtil.getObjectField(
							relationship.getObjectFieldId2());

					relatedColumn = relatedTable.getColumn(relatedField.getDBColumnName());

					column = relatedTable.getColumn(summarizeObjectField.getDBColumnName());
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				Expression<?> expression = null;

				if (function.equals("SUM")) {
					expression = DSLFunctionFactoryUtil.sum(column);
				}
				else if (function.equals("COUNT")) {
					expression = DSLFunctionFactoryUtil.count(column);
				}
				else if (function.equals("AVERAGE")) {
					expression = DSLFunctionFactoryUtil.avg(column);
				}
				else if (function.equals("MAX")) {
					expression = DSLFunctionFactoryUtil.max(column);
				}
				else if (function.equals("MIN")) {
					expression = DSLFunctionFactoryUtil.min(column);
				}

				selectExpressions.add(DSLQueryFactoryUtil.scalarSubDSLQuery(

					DSLQueryFactoryUtil.select(expression).from(relatedTable).where(relatedColumn.eq(getPrimaryKeyColumn())),

					_javaClasses.get(objectField.getDBType()),
					objectField.getName(),
					_sqlTypes.get(objectField.getDBType())));
			}
			else {
				selectExpressions.add(
					createColumn(
						objectField.getDBColumnName(),
						_javaClasses.get(objectField.getDBType()),
						_sqlTypes.get(objectField.getDBType()),
						Column.FLAG_DEFAULT));
			}
		}

		_selectExpressions = selectExpressions.toArray(new Expression<?>[0]);
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
			if(Objects.equals(
				objectField.getBusinessType(),
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

	public Expression<?>[] getSelectExpressions() {
		return _selectExpressions;
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
			"Clob", Clob.class
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

	private final List<ObjectField> _objectFields;
	private final String _primaryKeyColumnName;
	private final Expression<?>[] _selectExpressions;
	private final String _tableName;

}