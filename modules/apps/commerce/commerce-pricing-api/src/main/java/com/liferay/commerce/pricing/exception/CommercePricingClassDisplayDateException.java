/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassDisplayDateException extends PortalException {

	public CommercePricingClassDisplayDateException() {
	}

	public CommercePricingClassDisplayDateException(String msg) {
		super(msg);
	}

	public CommercePricingClassDisplayDateException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommercePricingClassDisplayDateException(Throwable throwable) {
		super(throwable);
	}

}