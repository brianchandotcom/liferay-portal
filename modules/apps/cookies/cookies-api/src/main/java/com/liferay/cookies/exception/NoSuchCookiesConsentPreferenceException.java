/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Christopher Kian
 */
public class NoSuchCookiesConsentPreferenceException
	extends NoSuchModelException {

	public NoSuchCookiesConsentPreferenceException() {
	}

	public NoSuchCookiesConsentPreferenceException(String msg) {
		super(msg);
	}

	public NoSuchCookiesConsentPreferenceException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchCookiesConsentPreferenceException(Throwable throwable) {
		super(throwable);
	}

}