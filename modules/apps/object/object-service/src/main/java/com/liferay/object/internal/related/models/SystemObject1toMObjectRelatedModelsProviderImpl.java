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

package com.liferay.object.internal.related.models;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.RequiredObjectRelationshipException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class SystemObject1toMObjectRelatedModelsProviderImpl
	<T extends BaseModel<T>>
		implements ObjectRelatedModelsProvider<T> {

	public SystemObject1toMObjectRelatedModelsProviderImpl(
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry,
		SystemObjectDefinitionManager systemObjectDefinitionManager,
		SystemObjectDefinitionManagerRegistry
			systemObjectDefinitionManagerRegistry) {

		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_persistedModelLocalServiceRegistry =
			persistedModelLocalServiceRegistry;
		_systemObjectDefinitionManager = systemObjectDefinitionManager;
		_systemObjectDefinitionManagerRegistry =
			systemObjectDefinitionManagerRegistry;

		_table = systemObjectDefinitionManager.getTable();
	}

	@Override
	public void deleteRelatedModel(
			long userId, long groupId, long objectRelationshipId,
			long primaryKey, String deletionType)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		List<T> relatedModels = getRelatedModels(
			groupId, objectRelationshipId, primaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		if (relatedModels.isEmpty()) {
			return;
		}

		if (Objects.equals(
				deletionType,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE)) {

			SystemObjectDefinitionManager systemObjectDefinitionManager =
				_systemObjectDefinitionManagerRegistry.
					getSystemObjectDefinitionManager(
						_objectDefinition.getName());

			for (BaseModel<T> baseModel : relatedModels) {
				systemObjectDefinitionManager.deleteBaseModel(baseModel);
			}
		}
		else if (Objects.equals(
					deletionType,
					ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE)) {

			ObjectField objectField = _objectFieldLocalService.getObjectField(
				objectRelationship.getObjectFieldId2());

			for (BaseModel<T> baseModel : relatedModels) {
				_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
					userId, objectRelationship.getObjectDefinitionId2(),
					GetterUtil.getLong(baseModel.getPrimaryKeyObj()),
					HashMapBuilder.<String, Serializable>put(
						objectField.getName(), 0
					).build());
			}
		}
		else if (Objects.equals(
					deletionType,
					ObjectRelationshipConstants.DELETION_TYPE_PREVENT)) {

			throw new RequiredObjectRelationshipException(
				StringBundler.concat(
					"Object relationship ",
					objectRelationship.getObjectRelationshipId(),
					" does not allow deletes"));
		}
	}

	@Override
	public void disassociateRelatedModels(
			long userId, long objectRelationshipId, long primaryKey1,
			long primaryKey2)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
			userId, objectRelationship.getObjectDefinitionId2(),
			GetterUtil.getLong(primaryKey2),
			HashMapBuilder.<String, Serializable>put(
				() -> {
					ObjectField objectField =
						_objectFieldLocalService.getObjectField(
							objectRelationship.getObjectFieldId2());

					return objectField.getName();
				},
				0
			).build());
	}

	@Override
	public String getClassName() {
		return _systemObjectDefinitionManager.getModelClassName();
	}

	@Override
	public long getCompanyId() {
		return _objectDefinition.getCompanyId();
	}

	@Override
	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_ONE_TO_MANY;
	}

	@Override
	public List<T> getRelatedModels(
			long groupId, long objectRelationshipId, long primaryKey, int start,
			int end)
		throws PortalException {

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				_systemObjectDefinitionManager.getModelClassName());

		DSLQuery dslQuery = _getGroupByStep(
			_getDynamicObjectDefinitionTable(), groupId, objectRelationshipId,
			primaryKey, DSLQueryFactoryUtil.selectDistinct(_table)
		).limit(
			start, end
		);

		return persistedModelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int getRelatedModelsCount(
			long groupId, long objectRelationshipId, long primaryKey)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable();

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				_systemObjectDefinitionManager.getModelClassName());

		return persistedModelLocalService.dslQueryCount(
			_getGroupByStep(
				dynamicObjectDefinitionTable, groupId, objectRelationshipId,
				primaryKey,
				DSLQueryFactoryUtil.countDistinct(
					dynamicObjectDefinitionTable.getPrimaryKeyColumn())));
	}

	@Override
	public List<T> getUnrelatedModels(
			long companyId, long groupId, ObjectDefinition objectDefinition,
			long objectEntryId, long objectRelationshipId)
		throws PortalException {

		Column<?, Long> companyIdColumn = (Column<?, Long>)_table.getColumn(
			"companyId");

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				objectDefinition.getClassName());

		return persistedModelLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				_table
			).from(
				_table
			).where(
				companyIdColumn.eq(
					companyId
				).and(
					() -> {
						Column<?, Long> groupIdColumn = _table.getColumn(
							"groupId");

						if ((groupIdColumn == null) ||
							Objects.equals(
								ObjectDefinitionConstants.SCOPE_COMPANY,
								objectDefinition1.getScope())) {

							return null;
						}

						return groupIdColumn.eq(groupId);
					}
				).and(
					() -> {
						Column<?, Long> primaryKeyColumn = _table.getColumn(
							objectDefinition.getPKObjectFieldDBColumnName());

						DynamicObjectDefinitionTable
							dynamicObjectDefinitionTable =
								_getDynamicObjectDefinitionTable();
						ObjectField objectField =
							_objectFieldLocalService.getObjectField(
								objectRelationship.getObjectFieldId2());

						Column<DynamicObjectDefinitionTable, Long>
							foreignKeyColumn =
								(Column<DynamicObjectDefinitionTable, Long>)
									dynamicObjectDefinitionTable.getColumn(
										objectField.getDBColumnName());

						return primaryKeyColumn.notIn(
							DSLQueryFactoryUtil.select(
								dynamicObjectDefinitionTable.
									getPrimaryKeyColumn()
							).from(
								dynamicObjectDefinitionTable
							).where(
								foreignKeyColumn.neq(0L)
							));
					}
				)
			));
	}

	private DynamicObjectDefinitionTable _getDynamicObjectDefinitionTable()
		throws PortalException {

		// TODO Cache this across the cluster with proper invalidation when the
		// object definition or its object fields are updated

		return new DynamicObjectDefinitionTable(
			_objectDefinition,
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId(),
				_objectDefinition.getExtensionDBTableName()),
			_objectDefinition.getExtensionDBTableName());
	}

	private GroupByStep _getGroupByStep(
			DynamicObjectDefinitionTable dynamicObjectDefinitionTable,
			long groupId, long objectRelationshipId, long primaryKey,
			FromStep fromStep)
		throws PortalException {

		Column<?, Long> primaryKeyColumn = null;

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		if (Objects.equals(objectField.getDBTableName(), _table)) {
			primaryKeyColumn = (Column<?, Long>)_table.getColumn(
				objectField.getDBColumnName());
		}
		else {
			primaryKeyColumn =
				(Column<DynamicObjectDefinitionTable, Long>)
					dynamicObjectDefinitionTable.getColumn(
						objectField.getDBColumnName());
		}

		return fromStep.from(
			_table
		).innerJoinON(
			dynamicObjectDefinitionTable,
			dynamicObjectDefinitionTable.getPrimaryKeyColumn(
			).eq(
				_systemObjectDefinitionManager.getPrimaryKeyColumn()
			)
		).where(
			primaryKeyColumn.eq(
				primaryKey
			).and(
				() -> {
					Column<?, Long> groupIdColumn = _table.getColumn("groupId");

					if ((groupIdColumn == null) ||
						Objects.equals(
							ObjectDefinitionConstants.SCOPE_COMPANY,
							objectDefinition1.getScope())) {

						return null;
					}

					return groupIdColumn.eq(groupId);
				}
			).and(
				() -> {
					Column<?, Long> companyIdColumn = _table.getColumn(
						"companyId");

					if (companyIdColumn == null) {
						return null;
					}

					return companyIdColumn.eq(objectField.getCompanyId());
				}
			)
		);
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;
	private final SystemObjectDefinitionManager _systemObjectDefinitionManager;
	private final SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;
	private final Table _table;

}