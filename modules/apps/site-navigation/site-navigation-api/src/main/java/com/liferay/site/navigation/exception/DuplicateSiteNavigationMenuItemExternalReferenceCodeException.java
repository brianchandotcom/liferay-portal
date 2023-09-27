/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateSiteNavigationMenuItemExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateSiteNavigationMenuItemExternalReferenceCodeException() {
	}

	public DuplicateSiteNavigationMenuItemExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateSiteNavigationMenuItemExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateSiteNavigationMenuItemExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}