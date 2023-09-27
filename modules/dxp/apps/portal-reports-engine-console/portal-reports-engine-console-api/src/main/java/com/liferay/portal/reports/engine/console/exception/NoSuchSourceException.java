/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchSourceException extends NoSuchModelException {

	public NoSuchSourceException() {
	}

	public NoSuchSourceException(String msg) {
		super(msg);
	}

	public NoSuchSourceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchSourceException(Throwable throwable) {
		super(throwable);
	}

}