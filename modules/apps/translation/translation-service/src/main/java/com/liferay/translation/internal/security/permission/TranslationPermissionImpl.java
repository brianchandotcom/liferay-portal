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

package com.liferay.translation.internal.security.permission;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.security.permission.TranslationPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = TranslationPermission.class)
public class TranslationPermissionImpl implements TranslationPermission {

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String languageId,
		String actionId) {

		String resourceName =
			TranslationConstants.RESOURCE_NAME + "." + languageId;

		Boolean hasPermission = _stagingPermission.hasPermission(
			permissionChecker, groupId, resourceName, 0,
			TranslationPortletKeys.TRANSLATION, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return permissionChecker.hasPermission(
			groupId, resourceName, resourceName, actionId);
	}

	@Reference
	private StagingPermission _stagingPermission;

}