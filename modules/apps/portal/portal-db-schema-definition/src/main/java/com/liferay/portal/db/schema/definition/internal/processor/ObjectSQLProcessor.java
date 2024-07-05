/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.processor;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionLocalizationTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionLocalizationTableFactory;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTableFactory;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

/**
 * @author Mariano Álvaro Sáiz
 */
public class ObjectSQLProcessor {

	public ObjectSQLProcessor(DB db) throws Exception {
		_db = db;

		_generateSQL();
	}

	public String getIndexesSQL() {
		return _indexesSQLSB.toString();
	}

	public String getTablesSQL() {
		return _tablesSQLSB.toString();
	}

	private void _generateIndexesSQL() throws Exception {
		DB db = DBManagerUtil.getDB();

		Method method = _getMethod(
			db.getClass(), "getIndexes", Connection.class, String.class,
			String.class, boolean.class);

		DataSource dataSource = InfrastructureUtil.getDataSource();

		try (Connection connection = dataSource.getConnection()) {
			for (String tableName : _tableNames) {
				for (IndexMetadata indexMetadata :
						(List<IndexMetadata>)method.invoke(
							db, connection, tableName, null, false)) {

					_indexesSQLSB.append(indexMetadata.getCreateSQL(null));
					_indexesSQLSB.append(StringPool.NEW_LINE);
				}
			}
		}
	}

	private void _generateRegularTables(ObjectDefinition objectDefinition)
		throws Exception {

		DynamicObjectDefinitionLocalizationTable
			dynamicObjectDefinitionLocalizationTable =
				DynamicObjectDefinitionLocalizationTableFactory.create(
					objectDefinition, ObjectFieldLocalServiceUtil.getService());

		if (dynamicObjectDefinitionLocalizationTable != null) {
			_saveTableSQL(
				dynamicObjectDefinitionLocalizationTable.getCreateTableSQL(),
				dynamicObjectDefinitionLocalizationTable.getTableName());
		}

		if (!objectDefinition.isUnmodifiableSystemObject()) {
			DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
				DynamicObjectDefinitionTableFactory.create(
					objectDefinition, ObjectFieldLocalServiceUtil.getService());

			_saveTableSQL(
				dynamicObjectDefinitionTable.getCreateTableSQL(),
				dynamicObjectDefinitionTable.getTableName());
		}

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			DynamicObjectDefinitionTableFactory.createExtension(
				objectDefinition, ObjectFieldLocalServiceUtil.getService());

		_saveTableSQL(
			dynamicObjectDefinitionTable.getCreateTableSQL(),
			dynamicObjectDefinitionTable.getTableName());
	}

	private void _generateRelationshipTables(ObjectDefinition objectDefinition)
		throws Exception {

		List<ObjectRelationship> objectRelationships =
			ObjectRelationshipLocalServiceUtil.getAllObjectRelationships(
				objectDefinition.getObjectDefinitionId());

		for (ObjectRelationship objectRelationship : objectRelationships) {
			if (!StringUtil.equalsIgnoreCase(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY) ||
				_tableNames.contains(objectRelationship.getDBTableName())) {

				continue;
			}

			Map<String, String> pkObjectFieldDBColumnNames =
				ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
					ObjectDefinitionLocalServiceUtil.getObjectDefinition(
						objectRelationship.getObjectDefinitionId1()),
					ObjectDefinitionLocalServiceUtil.getObjectDefinition(
						objectRelationship.getObjectDefinitionId2()),
					false);

			String pkObjectFieldDBColumnName1 = pkObjectFieldDBColumnNames.get(
				"pkObjectFieldDBColumnName1");
			String pkObjectFieldDBColumnName2 = pkObjectFieldDBColumnNames.get(
				"pkObjectFieldDBColumnName2");

			DynamicObjectRelationshipMappingTable
				dynamicObjectRelationshipMappingTable =
					new DynamicObjectRelationshipMappingTable(
						pkObjectFieldDBColumnName1, pkObjectFieldDBColumnName2,
						objectRelationship.getDBTableName());

			_saveTableSQL(
				dynamicObjectRelationshipMappingTable.getCreateTableSQL(),
				dynamicObjectRelationshipMappingTable.getTableName());
		}
	}

	private void _generateSQL() throws Exception {
		List<ObjectDefinition> objectDefinitions =
			ObjectDefinitionLocalServiceUtil.getObjectDefinitions(
				PortalInstancePool.getDefaultCompanyId(),
				WorkflowConstants.STATUS_APPROVED);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			_generateRegularTables(objectDefinition);

			_generateRelationshipTables(objectDefinition);
		}

		_generateIndexesSQL();
	}

	private Method _getMethod(
		Class<?> clazz, String methodName, Class<?>... parameterTypes) {

		while ((clazz != null) && (clazz != Object.class)) {
			try {
				Method method = clazz.getDeclaredMethod(
					methodName, parameterTypes);

				method.setAccessible(true);

				return method;
			}
			catch (NoSuchMethodException noSuchMethodException) {
				clazz = clazz.getSuperclass();
			}
		}

		return null;
	}

	private void _saveTableSQL(String sql, String tableName) throws Exception {
		_tablesSQLSB.append(_db.buildSQL(sql));
		_tablesSQLSB.append(StringPool.NEW_LINE);
		_tableNames.add(tableName);
	}

	private final DB _db;
	private final StringBundler _indexesSQLSB = new StringBundler();
	private final Set<String> _tableNames = new HashSet<>();
	private final StringBundler _tablesSQLSB = new StringBundler();

}