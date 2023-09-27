/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchOAuth2AuthorizationException extends NoSuchModelException {

	public NoSuchOAuth2AuthorizationException() {
	}

	public NoSuchOAuth2AuthorizationException(String msg) {
		super(msg);
	}

	public NoSuchOAuth2AuthorizationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchOAuth2AuthorizationException(Throwable throwable) {
		super(throwable);
	}

}