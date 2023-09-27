/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Riccardo Alberti
 */
public class DuplicateCommercePriceModifierExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCommercePriceModifierExternalReferenceCodeException() {
	}

	public DuplicateCommercePriceModifierExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateCommercePriceModifierExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCommercePriceModifierExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}