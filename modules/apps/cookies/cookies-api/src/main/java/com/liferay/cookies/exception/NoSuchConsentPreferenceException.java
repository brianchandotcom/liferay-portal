/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.cookies.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchConsentPreferenceException extends NoSuchModelException {

	public NoSuchConsentPreferenceException() {
	}

	public NoSuchConsentPreferenceException(String msg) {
		super(msg);
	}

	public NoSuchConsentPreferenceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchConsentPreferenceException(Throwable throwable) {
		super(throwable);
	}

}