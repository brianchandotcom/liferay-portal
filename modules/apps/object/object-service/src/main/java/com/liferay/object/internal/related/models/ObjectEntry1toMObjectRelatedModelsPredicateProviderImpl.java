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
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProvider;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
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

		ObjectField relatedObjectDefinitionObjectField =
			_objectFieldLocalService.getObjectField(
				relatedObjectDefinition.getTitleObjectFieldId());

		Table<?> relatedObjectTable = _objectFieldLocalService.getTable(
			relatedObjectDefinition.getObjectDefinitionId(),
			relatedObjectDefinitionObjectField.getName());

		Column<?, ?> relatedObjectDefinitionTableColumn =
			relatedObjectTable.getColumn(
				StringBundler.concat(
					"r_", objectRelationship.getName(), "_",
					_objectDefinition.getPKObjectFieldName()));

		ObjectField objectDefinitionField =
			_objectFieldLocalService.getObjectField(
				_objectDefinition.getTitleObjectFieldId());

		Table<?> objectTable = _objectFieldLocalService.getTable(
			_objectDefinition.getObjectDefinitionId(),
			objectDefinitionField.getName());

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