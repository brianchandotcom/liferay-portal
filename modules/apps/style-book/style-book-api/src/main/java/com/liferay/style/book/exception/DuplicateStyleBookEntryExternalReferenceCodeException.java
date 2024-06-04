/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateStyleBookEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateStyleBookEntryExternalReferenceCodeException() {
	}

	public DuplicateStyleBookEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateStyleBookEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateStyleBookEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}