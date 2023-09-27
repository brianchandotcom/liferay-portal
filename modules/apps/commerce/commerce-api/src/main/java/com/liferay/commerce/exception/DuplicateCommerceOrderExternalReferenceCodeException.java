/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Alessio Antonio Rendina
 */
public class DuplicateCommerceOrderExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCommerceOrderExternalReferenceCodeException() {
	}

	public DuplicateCommerceOrderExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateCommerceOrderExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCommerceOrderExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}