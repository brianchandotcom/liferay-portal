/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.db;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.impl.ObjectDefinitionImpl;
import com.liferay.object.model.impl.ObjectRelationshipImpl;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionLocalizationTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionLocalizationTableFactory;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.DBResourceProvider;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(service = DBResourceProvider.class)
public class ObjectDBResourceProvider implements DBResourceProvider {

	@Override
	public List<String> getTableNames(long companyId) throws PortalException {
		Map<String, String[]> tablesPrimaryKeyColumnNames =
			getTablesPrimaryKeyColumnNames(companyId);

		return new ArrayList<>(tablesPrimaryKeyColumnNames.keySet());
	}

	@Override
	public Map<String, String[]> getTablesPrimaryKeyColumnNames(long companyId)
		throws PortalException {

		try {
			return _getTablesPrimaryKeyColumnNames(companyId);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private List<ObjectRelationship> _getAllObjectRelationships(
			Connection connection, ObjectDefinition objectDefinition)
		throws PortalException {

		try {
			ObjectRelationshipLocalService objectRelationshipLocalService =
				_objectRelationshipLocalServiceSnapshot.get();

			if (objectRelationshipLocalService != null) {
				return objectRelationshipLocalService.getAllObjectRelationships(
					objectDefinition.getObjectDefinitionId());
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select dbTableName, objectDefinitionId1, ",
					"objectDefinitionId2, objectRelationshipId, type_ from ",
					"ObjectRelationship where companyId = ? and ",
					"(objectDefinitionId1 = ? or objectDefinitionId2 = ?) and ",
					"reverse = ?"))) {

			preparedStatement.setLong(1, objectDefinition.getCompanyId());
			preparedStatement.setLong(
				2, objectDefinition.getObjectDefinitionId());
			preparedStatement.setLong(
				3, objectDefinition.getObjectDefinitionId());
			preparedStatement.setBoolean(4, false);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				List<ObjectRelationship> objectRelationships =
					new ArrayList<>();

				while (resultSet.next()) {
					ObjectRelationship objectRelationship =
						new ObjectRelationshipImpl() {
							{
								setDBTableName(
									resultSet.getString("dbTableName"));
								setObjectDefinitionId1(
									resultSet.getLong("objectDefinitionId1"));
								setObjectDefinitionId2(
									resultSet.getLong("objectDefinitionId2"));
								setObjectRelationshipId(
									resultSet.getLong("objectRelationshipId"));
								setReverse(false);
								setType(resultSet.getString("type_"));
							}
						};

					objectRelationships.add(objectRelationship);
				}

				return objectRelationships;
			}
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private Map<Long, ObjectDefinition> _getObjectDefinitions(long companyId)
		throws PortalException {

		try {
			ObjectDefinitionLocalService objectDefinitionLocalService =
				_objectDefinitionLocalServiceSnapshot.get();

			if (objectDefinitionLocalService != null) {
				Map<Long, ObjectDefinition> objectDefinitions = new HashMap<>();

				for (ObjectDefinition objectDefinition :
						objectDefinitionLocalService.getObjectDefinitions(
							companyId, WorkflowConstants.STATUS_APPROVED)) {

					objectDefinitions.put(
						objectDefinition.getObjectDefinitionId(),
						objectDefinition);
				}

				return objectDefinitions;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		try (Connection connection = DataAccess.getConnection()) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select dbTableName, modifiable, ",
							"objectDefinitionId, pkObjectFieldDBColumnName, ",
							"system_ from ObjectDefinition where companyId = ",
							"? and status = ?"))) {

				preparedStatement.setLong(1, companyId);
				preparedStatement.setInt(2, WorkflowConstants.STATUS_APPROVED);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					Map<Long, ObjectDefinition> objectDefinitions =
						new HashMap<>();

					while (resultSet.next()) {
						ObjectDefinition objectDefinition =
							new ObjectDefinitionImpl() {
								{
									setCompanyId(companyId);
									setDBTableName(
										resultSet.getString("dbTableName"));
									setModifiable(
										resultSet.getBoolean("modifiable"));
									setObjectDefinitionId(
										resultSet.getLong(
											"objectDefinitionId"));
									setPKObjectFieldDBColumnName(
										resultSet.getString(
											"pkObjectFieldDBColumnName"));
									setSystem(resultSet.getBoolean("system_"));
								}
							};

						objectDefinitions.put(
							objectDefinition.getObjectDefinitionId(),
							objectDefinition);
					}

					return objectDefinitions;
				}
			}
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private Map<String, String[]>
			_getObjectLocalizationTablePrimaryKeyColumnNames(
				DBInspector dbInspector, ObjectDefinition objectDefinition)
		throws Exception {

		try {
			ObjectFieldLocalService objectFieldLocalService =
				_objectFieldLocalServiceSnapshot.get();

			if (objectFieldLocalService != null) {
				DynamicObjectDefinitionLocalizationTable
					dynamicObjectDefinitionLocalizationTable =
						DynamicObjectDefinitionLocalizationTableFactory.create(
							objectDefinition, objectFieldLocalService);

				if (dynamicObjectDefinitionLocalizationTable != null) {
					return Collections.singletonMap(
						objectDefinition.getLocalizationDBTableName(),
						dynamicObjectDefinitionLocalizationTable.
							getPrimaryKeyColumnNames());
				}

				return Collections.emptyMap();
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		if (dbInspector.hasTable(
				objectDefinition.getLocalizationDBTableName())) {

			return Collections.singletonMap(
				objectDefinition.getLocalizationDBTableName(),
				new String[] {
					objectDefinition.getPKObjectFieldDBColumnName(),
					"languageId"
				});
		}

		return Collections.emptyMap();
	}

	private Map<String, String[]>
			_getObjectRelationshipTablesPrimaryKeyColumnNames(
				Connection connection, ObjectDefinition objectDefinition,
				Map<Long, ObjectDefinition> objectDefinitions)
		throws PortalException {

		Map<String, String[]> objectRelationshipTablesPrimaryKeyColumnNames =
			new HashMap<>();

		List<ObjectRelationship> objectRelationships =
			_getAllObjectRelationships(connection, objectDefinition);

		for (ObjectRelationship objectRelationship : objectRelationships) {
			if (!StringUtil.equalsIgnoreCase(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

				continue;
			}

			Map<String, String> pkObjectFieldDBColumnNames =
				ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
					objectDefinitions.get(
						objectRelationship.getObjectDefinitionId1()),
					objectDefinitions.get(
						objectRelationship.getObjectDefinitionId2()),
					false);

			String pkObjectFieldDBColumnName1 = pkObjectFieldDBColumnNames.get(
				"pkObjectFieldDBColumnName1");
			String pkObjectFieldDBColumnName2 = pkObjectFieldDBColumnNames.get(
				"pkObjectFieldDBColumnName2");

			objectRelationshipTablesPrimaryKeyColumnNames.put(
				objectRelationship.getDBTableName(),
				new String[] {
					pkObjectFieldDBColumnName1, pkObjectFieldDBColumnName2
				});
		}

		return objectRelationshipTablesPrimaryKeyColumnNames;
	}

	private Map<String, String[]> _getTablesPrimaryKeyColumnNames(
			long companyId)
		throws Exception {

		Map<String, String[]> tablesPrimaryKeyColumnNames = new HashMap<>();

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			if (!dbInspector.hasTable("ObjectDefinition")) {
				return tablesPrimaryKeyColumnNames;
			}

			Map<Long, ObjectDefinition> objectDefinitions =
				_getObjectDefinitions(companyId);

			for (ObjectDefinition objectDefinition :
					objectDefinitions.values()) {

				if (!objectDefinition.isUnmodifiableSystemObject()) {
					tablesPrimaryKeyColumnNames.put(
						objectDefinition.getDBTableName(),
						new String[] {
							objectDefinition.getPKObjectFieldDBColumnName()
						});
				}

				tablesPrimaryKeyColumnNames.put(
					objectDefinition.getExtensionDBTableName(),
					new String[] {
						objectDefinition.getPKObjectFieldDBColumnName()
					});
				tablesPrimaryKeyColumnNames.putAll(
					_getObjectLocalizationTablePrimaryKeyColumnNames(
						dbInspector, objectDefinition));
				tablesPrimaryKeyColumnNames.putAll(
					_getObjectRelationshipTablesPrimaryKeyColumnNames(
						connection, objectDefinition, objectDefinitions));
			}
		}

		return tablesPrimaryKeyColumnNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDBResourceProvider.class);

	private final Snapshot<ObjectDefinitionLocalService>
		_objectDefinitionLocalServiceSnapshot = new Snapshot<>(
			ObjectDBResourceProvider.class, ObjectDefinitionLocalService.class,
			null, true);
	private final Snapshot<ObjectFieldLocalService>
		_objectFieldLocalServiceSnapshot = new Snapshot<>(
			ObjectDBResourceProvider.class, ObjectFieldLocalService.class, null,
			true);
	private final Snapshot<ObjectRelationshipLocalService>
		_objectRelationshipLocalServiceSnapshot = new Snapshot<>(
			ObjectDBResourceProvider.class,
			ObjectRelationshipLocalService.class, null, true);

}