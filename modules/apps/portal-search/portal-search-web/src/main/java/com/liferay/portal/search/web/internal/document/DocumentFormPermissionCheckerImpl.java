/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.document;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author André de Oliveira
 */
public class DocumentFormPermissionCheckerImpl
	implements DocumentFormPermissionChecker {

	public DocumentFormPermissionCheckerImpl(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	@Override
	public boolean hasPermission() {
		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return true;
		}

		return false;
	}

	private final ThemeDisplay _themeDisplay;

}