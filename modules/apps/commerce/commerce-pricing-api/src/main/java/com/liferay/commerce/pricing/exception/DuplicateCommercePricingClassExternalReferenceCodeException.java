/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Riccardo Alberti
 */
public class DuplicateCommercePricingClassExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCommercePricingClassExternalReferenceCodeException() {
	}

	public DuplicateCommercePricingClassExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateCommercePricingClassExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCommercePricingClassExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}