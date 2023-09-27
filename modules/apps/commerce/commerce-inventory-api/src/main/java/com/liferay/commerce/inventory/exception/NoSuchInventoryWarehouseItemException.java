/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Luca Pellizzon
 */
public class NoSuchInventoryWarehouseItemException
	extends NoSuchModelException {

	public NoSuchInventoryWarehouseItemException() {
	}

	public NoSuchInventoryWarehouseItemException(String msg) {
		super(msg);
	}

	public NoSuchInventoryWarehouseItemException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchInventoryWarehouseItemException(Throwable throwable) {
		super(throwable);
	}

}