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
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;

import java.util.List;
import java.util.Objects;

/**
 * @author Marcela Cunha
 */
public class SystemObjectMtoMObjectRelatedModelsProviderImpl
	<T extends BaseModel<T>>
		implements ObjectRelatedModelsProvider<T> {

	public SystemObjectMtoMObjectRelatedModelsProviderImpl(
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry,
		SystemObjectDefinitionManager systemObjectDefinitionManager,
		SystemObjectDefinitionManagerRegistry
			systemObjectDefinitionManagerRegistry) {

		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
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

		List<T> relatedModels = getRelatedModels(
			groupId, objectRelationshipId, primaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		if (relatedModels.isEmpty()) {
			return;
		}

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		if (Objects.equals(
				deletionType,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT) &&
			!objectRelationship.isReverse()) {

			throw new RequiredObjectRelationshipException(
				StringBundler.concat(
					"Object relationship ",
					objectRelationship.getObjectRelationshipId(),
					" does not allow deletes"));
		}

		_objectRelationshipLocalService.
			deleteObjectRelationshipMappingTableValues(
				objectRelationshipId, primaryKey);

		if (Objects.equals(
				deletionType,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE) &&
			!objectRelationship.isReverse()) {

			SystemObjectDefinitionManager systemObjectDefinitionManager =
				_systemObjectDefinitionManagerRegistry.
					getSystemObjectDefinitionManager(
						_objectDefinition.getName());

			for (BaseModel<T> baseModel : relatedModels) {
				systemObjectDefinitionManager.deleteBaseModel(baseModel);
			}
		}
	}

	@Override
	public void disassociateRelatedModels(
			long userId, long objectRelationshipId, long primaryKey1,
			long primaryKey2)
		throws PortalException {

		_objectRelationshipLocalService.
			deleteObjectRelationshipMappingTableValues(
				objectRelationshipId, primaryKey1, primaryKey2);
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
		return ObjectRelationshipConstants.TYPE_MANY_TO_MANY;
	}

	@Override
	public List<T> getRelatedModels(
			long groupId, long objectRelationshipId, long primaryKey, int start,
			int end)
		throws PortalException {

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				_systemObjectDefinitionManager.getModelClassName());

		return persistedModelLocalService.dslQuery(
			_getGroupByStep(
				groupId, objectRelationshipId, primaryKey,
				DSLQueryFactoryUtil.selectDistinct(_table)
			).limit(
				start, end
			));
	}

	@Override
	public int getRelatedModelsCount(
			long groupId, long objectRelationshipId, long primaryKey)
		throws PortalException {

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				_systemObjectDefinitionManager.getModelClassName());

		return persistedModelLocalService.dslQueryCount(
			_getGroupByStep(
				groupId, objectRelationshipId, primaryKey,
				DSLQueryFactoryUtil.countDistinct(
					_table.getColumn(
						_objectDefinition.getPKObjectFieldDBColumnName()))));
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

		DynamicObjectRelationshipMappingTable
			dynamicObjectRelationshipMappingTable =
				new DynamicObjectRelationshipMappingTable(
					objectDefinition1.getPKObjectFieldDBColumnName(),
					objectDefinition.getPKObjectFieldDBColumnName(),
					objectRelationship.getDBTableName());

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
						String primaryKeyColumnName1 =
							objectDefinition1.getPKObjectFieldDBColumnName();

						Column<DynamicObjectRelationshipMappingTable, Long>
							primaryKeyColumn1 =
								(Column
									<DynamicObjectRelationshipMappingTable,
									 Long>)
										 dynamicObjectRelationshipMappingTable.
											 getColumn(primaryKeyColumnName1);

						String primaryKeyColumnName2 =
							objectDefinition.getPKObjectFieldDBColumnName();

						Column<?, Long> primaryKeyColumn2 = _table.getColumn(
							primaryKeyColumnName2);

						return primaryKeyColumn2.notIn(
							DSLQueryFactoryUtil.select(
								dynamicObjectRelationshipMappingTable.getColumn(
									objectDefinition.
										getPKObjectFieldDBColumnName())
							).from(
								dynamicObjectRelationshipMappingTable
							).where(
								primaryKeyColumn1.eq(objectEntryId)
							));
					}
				)
			));
	}

	private GroupByStep _getGroupByStep(
			long groupId, long objectRelationshipId, long primaryKey,
			FromStep fromStep)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		ObjectDefinition objectDefinition2 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			new DynamicObjectDefinitionTable(
				objectDefinition2,
				_objectFieldLocalService.getObjectFields(
					objectRelationship.getObjectDefinitionId2(),
					objectDefinition2.getDBTableName()),
				objectDefinition2.getDBTableName());

		DynamicObjectRelationshipMappingTable
			dynamicObjectRelationshipMappingTable =
				new DynamicObjectRelationshipMappingTable(
					objectDefinition1.getPKObjectFieldDBColumnName(),
					objectDefinition2.getPKObjectFieldDBColumnName(),
					objectRelationship.getDBTableName());

		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn1 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn1();
		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn2 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn2();

		return fromStep.from(
			dynamicObjectDefinitionTable
		).innerJoinON(
			dynamicObjectRelationshipMappingTable,
			primaryKeyColumn2.eq(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn())
		).where(
			primaryKeyColumn1.eq(
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

					return companyIdColumn.eq(
						objectRelationship.getCompanyId());
				}
			)
		);
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
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