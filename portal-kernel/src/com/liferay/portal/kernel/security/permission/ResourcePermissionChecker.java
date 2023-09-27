/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission;

/**
 * @author     Miguel Pastor
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission}
 */
@Deprecated
public interface ResourcePermissionChecker {

	public Boolean checkResource(
		PermissionChecker permissionChecker, long classPK, String actionId);

}