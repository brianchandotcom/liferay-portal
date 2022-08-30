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

package com.liferay.object.util;

import com.liferay.object.exception.ObjectRelationshipReverseException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Marcela Cunha
 */
public class ObjectRelationshipUtil {

	public static ObjectRelationship getObjectRelationship(
			List<ObjectRelationship> objectRelationships)
		throws PortalException {

		if (objectRelationships.isEmpty()) {
			return null;
		}

		if (objectRelationships.size() == 1) {
			return objectRelationships.get(0);
		}

		Stream<ObjectRelationship> objectRelationshipsStream =
			objectRelationships.stream();

		return objectRelationshipsStream.filter(
			objectRelationship -> !objectRelationship.isReverse()
		).findFirst(
		).orElseThrow(
			ObjectRelationshipReverseException::new
		);
	}

	public static Map<String, String> getPKObjectFieldDBColumnNames(
		ObjectDefinition objectDefinition1, ObjectDefinition objectDefinition2,
		boolean reverse) {

		String pkObjectFieldDBColumnName1 =
			objectDefinition1.getPKObjectFieldDBColumnName();

		String pkObjectFieldDBColumnName2 =
			objectDefinition2.getPKObjectFieldDBColumnName();

		if (objectDefinition1.getObjectDefinitionId() !=
				objectDefinition2.getObjectDefinitionId()) {

			return HashMapBuilder.put(
				"pkObjectFieldDBColumnName1", pkObjectFieldDBColumnName1
			).put(
				"pkObjectFieldDBColumnName2", pkObjectFieldDBColumnName2
			).build();
		}

		return HashMapBuilder.put(
			"pkObjectFieldDBColumnName1",
			pkObjectFieldDBColumnName1.concat(reverse ? "2" : "1")
		).put(
			"pkObjectFieldDBColumnName2",
			pkObjectFieldDBColumnName2.concat(reverse ? "1" : "2")
		).build();
	}

}