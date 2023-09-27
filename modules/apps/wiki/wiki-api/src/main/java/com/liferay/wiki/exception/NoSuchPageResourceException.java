/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchPageResourceException extends NoSuchModelException {

	public NoSuchPageResourceException() {
	}

	public NoSuchPageResourceException(String msg) {
		super(msg);
	}

	public NoSuchPageResourceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchPageResourceException(Throwable throwable) {
		super(throwable);
	}

}