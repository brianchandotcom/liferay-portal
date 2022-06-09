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

package com.liferay.object.web.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

/**
 * @author Guilherme Camacho
 */
public class ObjectDefinitionPermissionUtil {

	public static boolean hasModelResourcePermission(
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			ObjectEntryService objectEntryService, String actionId)
		throws PortalException {

		if (!objectDefinition.isDefaultStorageType()) {
			return true;
		}

		return objectEntryService.hasModelResourcePermission(
			objectDefinition.getObjectDefinitionId(), objectEntry.getId(),
			ActionKeys.UPDATE);
	}

}