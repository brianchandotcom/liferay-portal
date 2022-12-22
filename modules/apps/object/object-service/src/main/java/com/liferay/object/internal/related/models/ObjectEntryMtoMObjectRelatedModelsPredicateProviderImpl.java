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

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProvider;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectEntryMtoMObjectRelatedModelsPredicateProviderImpl
	implements ObjectRelatedModelsPredicateProvider {

	public ObjectEntryMtoMObjectRelatedModelsPredicateProviderImpl(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		_objectDefinition = objectDefinition;
		_objectFieldLocalService = objectFieldLocalService;
	}

	@Override
	public String getClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_MANY_TO_MANY;
	}

	@Override
	public Predicate getPredicate(
			ObjectRelationship objectRelationship, Predicate predicate)
		throws PortalException {

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionLocalServiceUtil.getObjectDefinition(
				_getRelatedObjectDefinitionId(
					_objectDefinition.getObjectDefinitionId(),
					objectRelationship));

		DynamicObjectDefinitionTable relatedObjectTable = _getTable(
			relatedObjectDefinition);

		Column<?, ?> relatedObjectDefinitionTableColumn =
			relatedObjectTable.getColumn(
				relatedObjectDefinition.getPKObjectFieldDBColumnName());

		Map<String, String> pkObjectFieldDBColumnNames =
			ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
				_objectDefinition, relatedObjectDefinition,
				objectRelationship.isReverse());

		DynamicObjectRelationshipMappingTable
			dynamicObjectRelationshipMappingTable =
				new DynamicObjectRelationshipMappingTable(
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName1"),
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName2"),
					objectRelationship.getDBTableName());

		DynamicObjectDefinitionTable objectTable = _getTable(_objectDefinition);

		Column<?, ?> objectTableColumn = objectTable.getColumn(
			_objectDefinition.getPKObjectFieldDBColumnName());

		Column<DynamicObjectRelationshipMappingTable, ?> relatedObjectColumn =
			dynamicObjectRelationshipMappingTable.getColumn(
				relatedObjectDefinition.getPKObjectFieldDBColumnName());

		Column<DynamicObjectRelationshipMappingTable, ?> objectColumn =
			dynamicObjectRelationshipMappingTable.getColumn(
				_objectDefinition.getPKObjectFieldDBColumnName());

		DynamicObjectDefinitionTable relatedObjectDefinitionExtensionTable =
			_getExtensionTable(relatedObjectDefinition);

		return objectTableColumn.in(
			DSLQueryFactoryUtil.select(
				objectColumn
			).from(
				dynamicObjectRelationshipMappingTable
			).where(
				relatedObjectColumn.in(
					DSLQueryFactoryUtil.select(
						relatedObjectDefinitionTableColumn
					).from(
						relatedObjectTable
					).innerJoinON(
						relatedObjectDefinitionExtensionTable,
						relatedObjectTable.getPrimaryKeyColumn(
						).eq(
							relatedObjectDefinitionExtensionTable.
								getPrimaryKeyColumn()
						)
					).where(
						predicate
					))
			));
	}

	private DynamicObjectDefinitionTable _getExtensionTable(
		ObjectDefinition objectDefinition) {

		return new DynamicObjectDefinitionTable(
			objectDefinition,
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getExtensionDBTableName()),
			objectDefinition.getExtensionDBTableName());
	}

	private long _getRelatedObjectDefinitionId(
		long objectDefinitionId, ObjectRelationship objectRelationship) {

		if (objectRelationship.getObjectDefinitionId1() != objectDefinitionId) {
			return objectRelationship.getObjectDefinitionId1();
		}

		return objectRelationship.getObjectDefinitionId2();
	}

	private DynamicObjectDefinitionTable _getTable(
		ObjectDefinition objectDefinition) {

		return new DynamicObjectDefinitionTable(
			objectDefinition,
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getDBTableName()),
			objectDefinition.getDBTableName());
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectFieldLocalService _objectFieldLocalService;

}