/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateWebsiteExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateWebsiteExternalReferenceCodeException() {
	}

	public DuplicateWebsiteExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateWebsiteExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateWebsiteExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}