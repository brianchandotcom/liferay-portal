/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.test.util;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

/**
 * @author Feliphe Marinho
 */
public class ObjectRelationshipTestUtil {

	public static ObjectRelationship addObjectRelationship(
			ObjectRelationshipLocalService objectRelationshipLocalService,
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2)
		throws PortalException {

		return addObjectRelationship(
			objectRelationshipLocalService, objectDefinition1,
			objectDefinition2, TestPropsValues.getUserId());
	}

	public static ObjectRelationship addObjectRelationship(
			ObjectRelationshipLocalService objectRelationshipLocalService,
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, long userId)
		throws PortalException {

		return objectRelationshipLocalService.addObjectRelationship(
			null, userId, objectDefinition1.getObjectDefinitionId(),
			objectDefinition2.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	public static ObjectRelationship addObjectRelationship(
			ObjectRelationshipLocalService objectRelationshipLocalService,
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, String deletionType)
		throws PortalException {

		return objectRelationshipLocalService.addObjectRelationship(
			null, TestPropsValues.getUserId(),
			objectDefinition1.getObjectDefinitionId(),
			objectDefinition2.getObjectDefinitionId(), 0, deletionType, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

}