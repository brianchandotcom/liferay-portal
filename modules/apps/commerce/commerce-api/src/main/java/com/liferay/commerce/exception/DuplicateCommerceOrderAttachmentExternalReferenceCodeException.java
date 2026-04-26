/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Alessio Antonio Rendina
 */
public class DuplicateCommerceOrderAttachmentExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCommerceOrderAttachmentExternalReferenceCodeException() {
	}

	public DuplicateCommerceOrderAttachmentExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateCommerceOrderAttachmentExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCommerceOrderAttachmentExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}