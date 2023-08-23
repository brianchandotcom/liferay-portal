/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.configuration.util;

import com.liferay.object.configuration.ObjectScriptConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Pedro Leite
 */
public class ObjectScriptConfigurationUtil {

	public static boolean hasPermissionExecuteCode(
			PermissionChecker permissionChecker)
		throws PortalException {

		ObjectScriptConfiguration objectScriptConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				ObjectScriptConfiguration.class);

		if (permissionChecker.isOmniadmin() ||
			(objectScriptConfiguration.allowInstanceAdminExecuteCode() &&
			 permissionChecker.isCompanyAdmin())) {

			return true;
		}

		return false;
	}

}