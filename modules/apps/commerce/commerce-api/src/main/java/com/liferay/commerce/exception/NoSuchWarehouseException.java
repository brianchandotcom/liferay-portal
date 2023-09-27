/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchWarehouseException extends NoSuchModelException {

	public NoSuchWarehouseException() {
	}

	public NoSuchWarehouseException(String msg) {
		super(msg);
	}

	public NoSuchWarehouseException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchWarehouseException(Throwable throwable) {
		super(throwable);
	}

}