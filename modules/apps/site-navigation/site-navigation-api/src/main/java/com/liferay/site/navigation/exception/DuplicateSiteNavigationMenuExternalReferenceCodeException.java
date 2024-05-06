/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateSiteNavigationMenuExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateSiteNavigationMenuExternalReferenceCodeException() {
	}

	public DuplicateSiteNavigationMenuExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateSiteNavigationMenuExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateSiteNavigationMenuExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}