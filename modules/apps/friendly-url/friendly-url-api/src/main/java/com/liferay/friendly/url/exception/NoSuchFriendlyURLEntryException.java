/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchFriendlyURLEntryException extends NoSuchModelException {

	public NoSuchFriendlyURLEntryException() {
	}

	public NoSuchFriendlyURLEntryException(String msg) {
		super(msg);
	}

	public NoSuchFriendlyURLEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchFriendlyURLEntryException(Throwable throwable) {
		super(throwable);
	}

}