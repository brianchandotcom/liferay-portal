/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Luca Pellizzon
 */
public class NoSuchInventoryBookedQuantityException
	extends NoSuchModelException {

	public NoSuchInventoryBookedQuantityException() {
	}

	public NoSuchInventoryBookedQuantityException(String msg) {
		super(msg);
	}

	public NoSuchInventoryBookedQuantityException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchInventoryBookedQuantityException(Throwable throwable) {
		super(throwable);
	}

}