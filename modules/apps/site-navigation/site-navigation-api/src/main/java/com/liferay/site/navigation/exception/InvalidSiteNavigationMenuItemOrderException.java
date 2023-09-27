/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class InvalidSiteNavigationMenuItemOrderException
	extends PortalException {

	public InvalidSiteNavigationMenuItemOrderException() {
	}

	public InvalidSiteNavigationMenuItemOrderException(String msg) {
		super(msg);
	}

	public InvalidSiteNavigationMenuItemOrderException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public InvalidSiteNavigationMenuItemOrderException(Throwable throwable) {
		super(throwable);
	}

}