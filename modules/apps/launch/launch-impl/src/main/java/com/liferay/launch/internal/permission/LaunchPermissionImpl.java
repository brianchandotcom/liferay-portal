/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.permission;

import com.liferay.launch.internal.util.LaunchUtil;
import com.liferay.launch.permission.LaunchPermission;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = LaunchPermission.class)
public class LaunchPermissionImpl implements LaunchPermission {

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long launchId,
		String actionId) {

		ObjectEntry objectEntry = LaunchUtil.fetchLaunchObjectEntry(
			launchId);

		if (objectEntry == null) {
			return false;
		}

		try {
			ModelResourcePermission<ObjectEntry> modelResourcePermission =
				_objectEntryService.getModelResourcePermission(
					objectEntry.getObjectDefinitionId());

			return modelResourcePermission.contains(
				permissionChecker, launchId, actionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LaunchPermissionImpl.class);

	@Reference
	private ObjectEntryService _objectEntryService;

}