/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Marco Leo
 */
public class DuplicateCPAttachmentFileEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCPAttachmentFileEntryExternalReferenceCodeException() {
	}

	public DuplicateCPAttachmentFileEntryExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateCPAttachmentFileEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCPAttachmentFileEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}