/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateFileEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateFileEntryExternalReferenceCodeException() {
	}

	public DuplicateFileEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateFileEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateFileEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}