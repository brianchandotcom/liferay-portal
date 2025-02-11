/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.commerce.tax.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Marco Leo
 */
public class DuplicateCommerceTaxCategoryMappingExternalReferenceCodeException extends DuplicateExternalReferenceCodeException {

	public DuplicateCommerceTaxCategoryMappingExternalReferenceCodeException() {
	}

	public DuplicateCommerceTaxCategoryMappingExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateCommerceTaxCategoryMappingExternalReferenceCodeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateCommerceTaxCategoryMappingExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}