/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchVirtualOrderItemFileEntryException
	extends NoSuchModelException {

	public NoSuchVirtualOrderItemFileEntryException() {
	}

	public NoSuchVirtualOrderItemFileEntryException(String msg) {
		super(msg);
	}

	public NoSuchVirtualOrderItemFileEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchVirtualOrderItemFileEntryException(Throwable throwable) {
		super(throwable);
	}

}