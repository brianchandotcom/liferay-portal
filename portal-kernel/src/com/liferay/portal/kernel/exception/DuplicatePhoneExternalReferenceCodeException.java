/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicatePhoneExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicatePhoneExternalReferenceCodeException() {
	}

	public DuplicatePhoneExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicatePhoneExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicatePhoneExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}