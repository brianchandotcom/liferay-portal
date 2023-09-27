/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Alessio Antonio Rendina
 */
public class DuplicateCommerceTierPriceEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCommerceTierPriceEntryExternalReferenceCodeException() {
	}

	public DuplicateCommerceTierPriceEntryExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateCommerceTierPriceEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCommerceTierPriceEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}