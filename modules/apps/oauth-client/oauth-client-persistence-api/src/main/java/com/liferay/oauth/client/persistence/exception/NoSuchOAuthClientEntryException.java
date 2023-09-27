/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchOAuthClientEntryException extends NoSuchModelException {

	public NoSuchOAuthClientEntryException() {
	}

	public NoSuchOAuthClientEntryException(String msg) {
		super(msg);
	}

	public NoSuchOAuthClientEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchOAuthClientEntryException(Throwable throwable) {
		super(throwable);
	}

}