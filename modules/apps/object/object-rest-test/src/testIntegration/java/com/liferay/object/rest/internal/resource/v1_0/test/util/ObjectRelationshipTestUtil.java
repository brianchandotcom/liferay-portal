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

package com.liferay.object.rest.internal.resource.v1_0.test.util;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectRelationshipTestUtil {

	public static ObjectRelationship addObjectRelationship(
			ObjectDefinition objectDefinition,
			ObjectDefinition relatedObjectDefinition, long userId, String type)
		throws Exception {

		return ObjectRelationshipLocalServiceUtil.addObjectRelationship(
			userId, objectDefinition.getObjectDefinitionId(),
			relatedObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), type);
	}

	public static void relateObjectEntries(
			long objectEntryId1, long objectEntryId2,
			ObjectRelationship objectRelationship, long userId)
		throws Exception {

		ObjectRelationshipLocalServiceUtil.
			addObjectRelationshipMappingTableValues(
				userId, objectRelationship.getObjectRelationshipId(),
				objectEntryId1, objectEntryId2,
				ServiceContextTestUtil.getServiceContext());
	}

}