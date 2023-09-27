/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Sbarra
 */
public class DuplicateCommerceInventoryWarehouseItemException
	extends PortalException {

	public DuplicateCommerceInventoryWarehouseItemException() {
	}

	public DuplicateCommerceInventoryWarehouseItemException(String msg) {
		super(msg);
	}

	public DuplicateCommerceInventoryWarehouseItemException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCommerceInventoryWarehouseItemException(
		Throwable throwable) {

		super(throwable);
	}

}