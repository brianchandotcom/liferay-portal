/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchOAuth2ApplicationException extends NoSuchModelException {

	public NoSuchOAuth2ApplicationException() {
	}

	public NoSuchOAuth2ApplicationException(String msg) {
		super(msg);
	}

	public NoSuchOAuth2ApplicationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchOAuth2ApplicationException(Throwable throwable) {
		super(throwable);
	}

}