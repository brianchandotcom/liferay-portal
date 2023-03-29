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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
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
	extends BaseObjectEntryObjectRelatedModelsPredicateProviderImpl {

	public ObjectEntryMtoMObjectRelatedModelsPredicateProviderImpl(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		super(objectDefinition, objectFieldLocalService);
	}

	@Override
	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_MANY_TO_MANY;
	}

	@Override
	public Predicate getPredicate(
			ObjectRelationship objectRelationship, Predicate predicate)
		throws PortalException {

		Column<?, ?> dynamicObjectDefinitionTableColumn =
			getPKObjectFieldColumn(
				getDynamicObjectDefinitionTable(objectDefinition),
				objectDefinition);

		ObjectDefinition relatedObjectDefinition =
			ObjectDefinitionLocalServiceUtil.getObjectDefinition(
				_getRelatedObjectDefinitionId(
					objectDefinition.getObjectDefinitionId(),
					objectRelationship));

		Map<String, String> pkObjectFieldDBColumnNames =
			ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
				objectDefinition, relatedObjectDefinition,
				objectRelationship.isReverse());

		DynamicObjectRelationshipMappingTable
			dynamicObjectRelationshipMappingTable =
				new DynamicObjectRelationshipMappingTable(
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName1"),
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName2"),
					objectRelationship.getDBTableName());

		Column<DynamicObjectRelationshipMappingTable, ?>
			dynamicObjectRelationshipMappingTableColumn =
				(Column<DynamicObjectRelationshipMappingTable, ?>)
					getPKObjectFieldColumn(
						dynamicObjectRelationshipMappingTable,
						relatedObjectDefinition);

		DynamicObjectDefinitionTable relatedDynamicObjectDefinitionTable =
			getDynamicObjectDefinitionTable(relatedObjectDefinition);
		DynamicObjectDefinitionTable relatedObjectDefinitionExtensionTable =
			getExtensionDynamicObjectDefinitionTable(relatedObjectDefinition);

		return dynamicObjectDefinitionTableColumn.in(
			DSLQueryFactoryUtil.select(
				getPKObjectFieldColumn(
					dynamicObjectRelationshipMappingTable, objectDefinition)
			).from(
				dynamicObjectRelationshipMappingTable
			).where(
				dynamicObjectRelationshipMappingTableColumn.in(
					DSLQueryFactoryUtil.select(
						getPKObjectFieldColumn(
							relatedDynamicObjectDefinitionTable,
							relatedObjectDefinition)
					).from(
						relatedDynamicObjectDefinitionTable
					).innerJoinON(
						ObjectEntryTable.INSTANCE,
						ObjectEntryTable.INSTANCE.objectEntryId.eq(
							relatedDynamicObjectDefinitionTable.
								getPrimaryKeyColumn())
					).innerJoinON(
						relatedObjectDefinitionExtensionTable,
						relatedDynamicObjectDefinitionTable.getPrimaryKeyColumn(
						).eq(
							relatedObjectDefinitionExtensionTable.
								getPrimaryKeyColumn()
						)
					).where(
						predicate
					))
			));
	}

	private long _getRelatedObjectDefinitionId(
		long objectDefinitionId, ObjectRelationship objectRelationship) {

		if (objectRelationship.getObjectDefinitionId1() != objectDefinitionId) {
			return objectRelationship.getObjectDefinitionId1();
		}

		return objectRelationship.getObjectDefinitionId2();
	}

}