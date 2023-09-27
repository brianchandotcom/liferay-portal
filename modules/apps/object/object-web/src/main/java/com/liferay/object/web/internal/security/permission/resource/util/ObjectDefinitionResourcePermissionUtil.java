/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.security.permission.resource.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Guilherme Camacho
 */
public class ObjectDefinitionResourcePermissionUtil {

	public static boolean hasModelResourcePermission(
			ObjectDefinition objectDefinition, Long objectEntryId,
			ObjectEntryService objectEntryService, String actionId)
		throws PortalException {

		if (!objectDefinition.isDefaultStorageType()) {
			return true;
		}

		return objectEntryService.hasModelResourcePermission(
			objectDefinition.getObjectDefinitionId(), objectEntryId, actionId);
	}

	public static boolean hasModelResourcePermission(
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			ObjectEntryService objectEntryService, String actionId)
		throws PortalException {

		return hasModelResourcePermission(
			objectDefinition, objectEntry.getId(), objectEntryService,
			actionId);
	}

}