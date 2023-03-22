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

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;

import java.util.List;
import java.util.Map;

/**
 * @author Sergio Jimenez del Coso
 */
public class SystemObjectEntryMtoMObjectRelationshipElementsParserImpl
	extends BaseObjectRelationshipElementsParserImpl<Map<String, Object>> {

	public SystemObjectEntryMtoMObjectRelationshipElementsParserImpl(
		ObjectDefinition objectDefinition) {

		super(objectDefinition);
	}

	@Override
	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_MANY_TO_MANY;
	}

	@Override
	public List<Map<String, Object>> parse(
			ObjectRelationship objectRelationship, Object value)
		throws Exception {

		return parseMany(value);
	}

	@Override
	protected Map<String, Object> parseOne(Object object) {
		validateOne(object);

		return (Map<String, Object>)object;
	}

}