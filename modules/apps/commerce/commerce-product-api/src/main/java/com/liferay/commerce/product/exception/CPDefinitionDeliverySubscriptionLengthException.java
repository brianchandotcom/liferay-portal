/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class CPDefinitionDeliverySubscriptionLengthException
	extends PortalException {

	public CPDefinitionDeliverySubscriptionLengthException() {
	}

	public CPDefinitionDeliverySubscriptionLengthException(String msg) {
		super(msg);
	}

	public CPDefinitionDeliverySubscriptionLengthException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CPDefinitionDeliverySubscriptionLengthException(
		Throwable throwable) {

		super(throwable);
	}

}