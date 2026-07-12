/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Javier Moral
 */
public class DuplicatePageElementException extends PortalException {

	public DuplicatePageElementException(String externalReferenceCode) {
		super(
			"A page element with the external reference code \"" +
				externalReferenceCode + "\" already exists");
	}

}