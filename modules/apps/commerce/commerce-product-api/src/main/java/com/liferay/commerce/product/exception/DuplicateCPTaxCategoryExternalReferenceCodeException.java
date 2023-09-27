/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Marco Leo
 */
public class DuplicateCPTaxCategoryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCPTaxCategoryExternalReferenceCodeException() {
	}

	public DuplicateCPTaxCategoryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateCPTaxCategoryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCPTaxCategoryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}