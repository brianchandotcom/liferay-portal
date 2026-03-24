/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateOAuthClientEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateOAuthClientEntryExternalReferenceCodeException() {
	}

	public DuplicateOAuthClientEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateOAuthClientEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateOAuthClientEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}