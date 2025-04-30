/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchEntryPinException extends NoSuchModelException {

	public NoSuchEntryPinException() {
	}

	public NoSuchEntryPinException(String msg) {
		super(msg);
	}

	public NoSuchEntryPinException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchEntryPinException(Throwable throwable) {
		super(throwable);
	}

}