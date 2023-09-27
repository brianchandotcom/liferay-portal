/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = TranslationPermission.class)
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