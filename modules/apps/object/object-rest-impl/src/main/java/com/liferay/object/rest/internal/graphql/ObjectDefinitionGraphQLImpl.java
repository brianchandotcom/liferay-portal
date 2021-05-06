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

package com.liferay.object.rest.internal.graphql;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.graphql.ObjectDefinitionGraphQL;
import com.liferay.object.rest.internal.odata.entity.ObjectEntryEntityModel;
import com.liferay.portal.odata.entity.EntityModel;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class ObjectDefinitionGraphQLImpl implements ObjectDefinitionGraphQL {

	public ObjectDefinitionGraphQLImpl(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		_objectDefinition = objectDefinition;
		_objectFields = objectFields;

		_entityModel = new ObjectEntryEntityModel(objectFields);
	}

	@Override
	public EntityModel getEntityModel() {
		return _entityModel;
	}

	@Override
	public ObjectDefinition getObjectDefinition() {
		return _objectDefinition;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return _objectFields;
	}

	private final EntityModel _entityModel;
	private final ObjectDefinition _objectDefinition;
	private final List<ObjectField> _objectFields;

}