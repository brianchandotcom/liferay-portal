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

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.related.models.ObjectRelatedModelsPredicateProvider;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.odata.filter.InvalidFilterException;

/**
 * @author Luis Miguel Barcos
 */
public abstract class BaseObjectEntryObjectRelatedModelsPredicateProviderImpl
	implements ObjectRelatedModelsPredicateProvider {

	public BaseObjectEntryObjectRelatedModelsPredicateProviderImpl(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		this.objectDefinition = objectDefinition;
		this.objectFieldLocalService = objectFieldLocalService;
	}

	@Override
	public String getClassName() {
		return objectDefinition.getClassName();
	}

	@Override
	public Predicate getPredicate(
			ObjectRelationship objectRelationship, Predicate predicate)
		throws PortalException {

		ObjectDefinition relatedObjectDefinition = _getRelatedObjectDefinition(
			objectRelationship);

		if (relatedObjectDefinition.isUnmodifiableSystemObject()) {
			throw new InvalidFilterException(
				"Filtering over system objects is not supported");
		}

		return getPredicate(
			objectRelationship, predicate, relatedObjectDefinition);
	}

	public abstract Predicate getPredicate(
			ObjectRelationship objectRelationship, Predicate predicate,
			ObjectDefinition relatedObjectDefinition)
		throws PortalException;

	protected DynamicObjectDefinitionTable getDynamicObjectDefinitionTable(
		ObjectDefinition objectDefinition) {

		return new DynamicObjectDefinitionTable(
			objectDefinition,
			objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getDBTableName()),
			objectDefinition.getDBTableName());
	}

	protected DynamicObjectDefinitionTable
		getExtensionDynamicObjectDefinitionTable(
			ObjectDefinition objectDefinition) {

		return new DynamicObjectDefinitionTable(
			objectDefinition,
			objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getExtensionDBTableName()),
			objectDefinition.getExtensionDBTableName());
	}

	protected <T extends BaseTable<T>> Column<?, ?> getPKObjectFieldColumn(
		BaseTable<T> baseTable, String pkObjectFieldDBColumnName) {

		return baseTable.getColumn(pkObjectFieldDBColumnName);
	}

	protected final ObjectDefinition objectDefinition;
	protected final ObjectFieldLocalService objectFieldLocalService;

	private ObjectDefinition _getRelatedObjectDefinition(
			ObjectRelationship objectRelationship)
		throws PortalException {

		long relatedObjectDefinitionId;

		if (objectDefinition.getObjectDefinitionId() !=
				objectRelationship.getObjectDefinitionId1()) {

			relatedObjectDefinitionId =
				objectRelationship.getObjectDefinitionId1();
		}
		else {
			relatedObjectDefinitionId =
				objectRelationship.getObjectDefinitionId2();
		}

		return ObjectDefinitionLocalServiceUtil.getObjectDefinition(
			relatedObjectDefinitionId);
	}

}