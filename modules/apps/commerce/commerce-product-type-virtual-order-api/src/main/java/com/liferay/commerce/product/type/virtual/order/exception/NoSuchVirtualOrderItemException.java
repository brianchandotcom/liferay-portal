/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchVirtualOrderItemException extends NoSuchModelException {

	public NoSuchVirtualOrderItemException() {
	}

	public NoSuchVirtualOrderItemException(String msg) {
		super(msg);
	}

	public NoSuchVirtualOrderItemException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchVirtualOrderItemException(Throwable throwable) {
		super(throwable);
	}

}