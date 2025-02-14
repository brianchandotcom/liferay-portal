/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchTaxCategoryMappingException extends NoSuchModelException {

	public NoSuchTaxCategoryMappingException() {
	}

	public NoSuchTaxCategoryMappingException(String msg) {
		super(msg);
	}

	public NoSuchTaxCategoryMappingException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchTaxCategoryMappingException(Throwable throwable) {
		super(throwable);
	}

}