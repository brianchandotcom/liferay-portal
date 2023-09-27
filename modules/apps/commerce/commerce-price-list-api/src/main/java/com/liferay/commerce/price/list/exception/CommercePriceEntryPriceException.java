/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceEntryPriceException extends PortalException {

	public CommercePriceEntryPriceException() {
	}

	public CommercePriceEntryPriceException(String msg) {
		super(msg);
	}

	public CommercePriceEntryPriceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CommercePriceEntryPriceException(Throwable throwable) {
		super(throwable);
	}

}