/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Luca Pellizzon
 */
public class DuplicateCommerceInventoryWarehouseExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCommerceInventoryWarehouseExternalReferenceCodeException() {
	}

	public DuplicateCommerceInventoryWarehouseExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateCommerceInventoryWarehouseExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCommerceInventoryWarehouseExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}