/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchSavedContentEntryException extends NoSuchModelException {

	public NoSuchSavedContentEntryException() {
	}

	public NoSuchSavedContentEntryException(String msg) {
		super(msg);
	}

	public NoSuchSavedContentEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchSavedContentEntryException(Throwable throwable) {
		super(throwable);
	}

}