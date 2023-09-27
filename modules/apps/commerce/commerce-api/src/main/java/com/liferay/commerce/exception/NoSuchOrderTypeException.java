/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchOrderTypeException extends NoSuchModelException {

	public NoSuchOrderTypeException() {
	}

	public NoSuchOrderTypeException(String msg) {
		super(msg);
	}

	public NoSuchOrderTypeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchOrderTypeException(Throwable throwable) {
		super(throwable);
	}

}