/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchInstanceTokenException extends NoSuchModelException {

	public NoSuchInstanceTokenException() {
	}

	public NoSuchInstanceTokenException(String msg) {
		super(msg);
	}

	public NoSuchInstanceTokenException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchInstanceTokenException(Throwable throwable) {
		super(throwable);
	}

}