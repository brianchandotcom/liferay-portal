/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class CategoryExternalReferenceCodeException extends PortalException {

	public CategoryExternalReferenceCodeException() {
	}

	public CategoryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public CategoryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CategoryExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}