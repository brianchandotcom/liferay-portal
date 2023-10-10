/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.configuration.util;

import com.liferay.object.configuration.ObjectConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Pedro Leite
 */
public class ObjectConfigurationUtil {

	public static boolean hasPermissionExecuteScript(
			PermissionChecker permissionChecker)
		throws PortalException {

		ObjectConfiguration objectConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				ObjectConfiguration.class);

		if (permissionChecker.isOmniadmin() ||
			(objectConfiguration.allowAdministratorsExecuteScript() &&
			 permissionChecker.isCompanyAdmin())) {

			return true;
		}

		return false;
	}

	public static int maximumFileSizeForGuestUsers() throws PortalException {
		ObjectConfiguration objectConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				ObjectConfiguration.class);

		return objectConfiguration.maximumFileSizeForGuestUsers();
	}

}