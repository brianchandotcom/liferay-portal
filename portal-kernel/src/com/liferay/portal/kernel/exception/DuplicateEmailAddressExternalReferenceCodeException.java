/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateEmailAddressExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateEmailAddressExternalReferenceCodeException() {
	}

	public DuplicateEmailAddressExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateEmailAddressExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateEmailAddressExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}