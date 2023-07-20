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
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio Jimenez del Coso
 */
public class SystemObjectEntry1toMObjectRelationshipElementsParserImpl
	extends BaseObjectRelationshipElementsParserImpl<Map<String, Object>> {

	public SystemObjectEntry1toMObjectRelationshipElementsParserImpl(
		ObjectDefinition objectDefinition) {

		super(objectDefinition);
	}

	@Override
	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_ONE_TO_MANY;
	}

	@Override
	public List<Map<String, Object>> parse(
			ObjectRelationship objectRelationship, Object value)
		throws Exception {

		if (objectRelationship.getObjectDefinitionId1() ==
				objectDefinition.getObjectDefinitionId()) {

			Map<String, Object> systemObjectEntryMap = parseOne(value);

			if (systemObjectEntryMap == null) {
				return Collections.emptyList();
			}

			return Collections.singletonList(systemObjectEntryMap);
		}

		return parseMany(value);
	}

	@Override
	protected Map<String, Object> parseOne(Object object) {
		validateOne(object);

		Map<String, Object> nestedSystemObjectEntryProperties =
			(Map<String, Object>)object;

		if (MapUtil.isEmpty(nestedSystemObjectEntryProperties)) {
			return null;
		}

		return nestedSystemObjectEntryProperties;
	}

}