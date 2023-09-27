/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchShippingFixedOptionQualifierException
	extends NoSuchModelException {

	public NoSuchShippingFixedOptionQualifierException() {
	}

	public NoSuchShippingFixedOptionQualifierException(String msg) {
		super(msg);
	}

	public NoSuchShippingFixedOptionQualifierException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchShippingFixedOptionQualifierException(Throwable throwable) {
		super(throwable);
	}

}