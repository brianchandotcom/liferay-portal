/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jonathan McCann
 */
public class InvalidSiteNavigationMenuItemModelException
	extends PortalException {

	public InvalidSiteNavigationMenuItemModelException() {
	}

	public InvalidSiteNavigationMenuItemModelException(String msg) {
		super(msg);
	}

	public InvalidSiteNavigationMenuItemModelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public InvalidSiteNavigationMenuItemModelException(Throwable throwable) {
		super(throwable);
	}

}