/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.security.permission;

import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Alejandro Tardín
 */
public interface TranslationPermission {

	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String languageId,
		String actionId);

}