/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVirtualOrderItemUrlException extends PortalException {

	public CommerceVirtualOrderItemUrlException() {
	}

	public CommerceVirtualOrderItemUrlException(String msg) {
		super(msg);
	}

	public CommerceVirtualOrderItemUrlException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommerceVirtualOrderItemUrlException(Throwable throwable) {
		super(throwable);
	}

}