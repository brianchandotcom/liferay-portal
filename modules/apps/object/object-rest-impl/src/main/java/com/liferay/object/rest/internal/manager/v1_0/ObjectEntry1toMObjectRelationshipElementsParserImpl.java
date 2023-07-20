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
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Carlos Correa
 * @author Sergio Jimenez del Coso
 */
public class ObjectEntry1toMObjectRelationshipElementsParserImpl
	extends BaseObjectRelationshipElementsParserImpl<ObjectEntry> {

	public ObjectEntry1toMObjectRelationshipElementsParserImpl(
		ObjectDefinition objectDefinition) {

		super(objectDefinition);
	}

	@Override
	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_ONE_TO_MANY;
	}

	@Override
	public List<ObjectEntry> parse(
			ObjectRelationship objectRelationship, Object value)
		throws Exception {

		if (objectRelationship.getObjectDefinitionId1() ==
				objectDefinition.getObjectDefinitionId()) {

			ObjectEntry objectEntry = parseOne(value);

			if (objectEntry == null) {
				return Collections.emptyList();
			}

			return Collections.singletonList(objectEntry);
		}

		return parseMany(value);
	}

	@Override
	protected ObjectEntry parseOne(Object object) {
		validateOne(object);

		Map<String, Object> nestedObjectEntryProperties =
			(Map<String, Object>)object;

		if (MapUtil.isEmpty(nestedObjectEntryProperties)) {
			return null;
		}

		return toObjectEntry(nestedObjectEntryProperties);
	}

}