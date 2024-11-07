/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.defaultpermissions.configuration.manager;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.Map;

/**
 * @author Stefano Motta
 */
public class PortalDefaultPermissionsConfigurationManagerUtil {

	public static Map<String, String[]> getDefaultPermissions(
		long companyId, long groupId, String className) {

		PortalDefaultPermissionsConfigurationManager
			portalDefaultPermissionsConfigurationManager =
				_portalDefaultPermissionsConfigurationManagerSnapshot.get();

		if (portalDefaultPermissionsConfigurationManager == null) {
			return null;
		}

		return portalDefaultPermissionsConfigurationManager.
			getDefaultPermissions(companyId, groupId, className);
	}

	private static final Snapshot<PortalDefaultPermissionsConfigurationManager>
		_portalDefaultPermissionsConfigurationManagerSnapshot = new Snapshot<>(
			PortalDefaultPermissionsConfigurationManagerUtil.class,
			PortalDefaultPermissionsConfigurationManager.class,
			"(portal.default.permissions.scope=group)");

}