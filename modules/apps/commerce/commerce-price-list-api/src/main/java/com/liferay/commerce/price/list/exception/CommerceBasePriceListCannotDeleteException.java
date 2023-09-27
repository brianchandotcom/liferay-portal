/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceBasePriceListCannotDeleteException
	extends PortalException {

	public CommerceBasePriceListCannotDeleteException() {
	}

	public CommerceBasePriceListCannotDeleteException(String msg) {
		super(msg);
	}

	public CommerceBasePriceListCannotDeleteException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommerceBasePriceListCannotDeleteException(Throwable throwable) {
		super(throwable);
	}

}