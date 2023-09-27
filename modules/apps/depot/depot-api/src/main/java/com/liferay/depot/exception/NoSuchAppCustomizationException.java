/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchAppCustomizationException extends NoSuchModelException {

	public NoSuchAppCustomizationException() {
	}

	public NoSuchAppCustomizationException(String msg) {
		super(msg);
	}

	public NoSuchAppCustomizationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchAppCustomizationException(Throwable throwable) {
		super(throwable);
	}

}