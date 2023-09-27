/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteNavigationMenuItemNameException extends PortalException {

	public SiteNavigationMenuItemNameException() {
	}

	public SiteNavigationMenuItemNameException(String msg) {
		super(msg);
	}

	public SiteNavigationMenuItemNameException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public SiteNavigationMenuItemNameException(Throwable throwable) {
		super(throwable);
	}

}