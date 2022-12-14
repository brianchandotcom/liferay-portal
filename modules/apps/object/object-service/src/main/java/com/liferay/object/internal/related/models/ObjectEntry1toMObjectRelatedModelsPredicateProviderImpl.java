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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProvider;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectEntry1toMObjectRelatedModelsPredicateProviderImpl
	implements ObjectRelatedModelsPredicateProvider {

	public ObjectEntry1toMObjectRelatedModelsPredicateProviderImpl(
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
		return ObjectRelationshipConstants.TYPE_ONE_TO_MANY;
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

		DynamicObjectDefinitionTable relatedObjectTable =
			new DynamicObjectDefinitionTable(
				relatedObjectDefinition,
				_objectFieldLocalService.getObjectFields(
					relatedObjectDefinition.getObjectDefinitionId(),
					relatedObjectDefinition.getDBTableName()),
				relatedObjectDefinition.getDBTableName());

		Column<?, ?> relatedObjectDefinitionTableColumn =
			relatedObjectTable.getColumn(
				StringBundler.concat(
					"r_", objectRelationship.getName(), "_",
					_objectDefinition.getPKObjectFieldName()));

		DynamicObjectDefinitionTable objectTable =
			new DynamicObjectDefinitionTable(
				_objectDefinition,
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId(),
					_objectDefinition.getDBTableName()),
				_objectDefinition.getDBTableName());

		Column<?, ?> objectTableColumn = objectTable.getColumn(
			_objectDefinition.getPKObjectFieldName() + "_");

		return objectTableColumn.in(
			DSLQueryFactoryUtil.select(
				relatedObjectDefinitionTableColumn
			).from(
				relatedObjectTable
			).where(
				predicate
			));
	}

	private long _getRelatedObjectDefinitionId(
		long objectDefinitionId, ObjectRelationship objectRelationship) {

		if (objectRelationship.getObjectDefinitionId1() != objectDefinitionId) {
			return objectRelationship.getObjectDefinitionId1();
		}

		return objectRelationship.getObjectDefinitionId2();
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectFieldLocalService _objectFieldLocalService;

}