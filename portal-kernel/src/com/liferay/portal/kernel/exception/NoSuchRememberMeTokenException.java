/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchRememberMeTokenException extends NoSuchModelException {

	public NoSuchRememberMeTokenException() {
	}

	public NoSuchRememberMeTokenException(String msg) {
		super(msg);
	}

	public NoSuchRememberMeTokenException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchRememberMeTokenException(Throwable throwable) {
		super(throwable);
	}

}