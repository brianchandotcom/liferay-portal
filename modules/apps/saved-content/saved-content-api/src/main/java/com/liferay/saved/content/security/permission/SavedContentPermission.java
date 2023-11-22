/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.security.permission;

import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Alicia García
 */
public interface SavedContentPermission {

	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId);

}