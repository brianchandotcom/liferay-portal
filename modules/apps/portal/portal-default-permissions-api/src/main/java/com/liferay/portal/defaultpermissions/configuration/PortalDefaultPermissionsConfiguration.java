/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.configuration;

import java.util.Map;

/**
 * @author Stefano Motta
 */
public interface PortalDefaultPermissionsConfiguration {

	public Map<String, Map<String, String[]>> getDefaultPermissions(
		long companyId);

	public void setDefaultPermissions(
		long companyId, Map<String, Map<String, String[]>> defaultPermissions);

}