/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.defaultpermissions.configuration.manager;

import java.util.Map;

/**
 * @author Stefano Motta
 */
public interface PortalDefaultPermissionsConfigurationManager {

	public Map<String, Map<String, String[]>> getDefaultPermissions(
		long companyId, long groupId);

	public Map<String, String[]> getDefaultPermissions(
		long companyId, long groupId, String className);

	public String getScope();

	public void saveDefaultPermissions(
		long primaryKey, Map<String, Map<String, String[]>> defaultPermissions);

}