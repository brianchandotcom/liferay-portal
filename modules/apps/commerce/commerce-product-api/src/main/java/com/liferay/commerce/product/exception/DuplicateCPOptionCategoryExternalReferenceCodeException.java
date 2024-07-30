/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Marco Leo
 */
public class DuplicateCPOptionCategoryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCPOptionCategoryExternalReferenceCodeException() {
	}

	public DuplicateCPOptionCategoryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateCPOptionCategoryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCPOptionCategoryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}