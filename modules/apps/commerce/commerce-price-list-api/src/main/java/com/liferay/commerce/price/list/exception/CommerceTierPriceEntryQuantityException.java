/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTierPriceEntryQuantityException extends PortalException {

	public CommerceTierPriceEntryQuantityException() {
	}

	public CommerceTierPriceEntryQuantityException(String msg) {
		super(msg);
	}

	public CommerceTierPriceEntryQuantityException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommerceTierPriceEntryQuantityException(Throwable throwable) {
		super(throwable);
	}

}