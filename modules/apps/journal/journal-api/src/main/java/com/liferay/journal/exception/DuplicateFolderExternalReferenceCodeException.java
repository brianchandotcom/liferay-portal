/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateFolderExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateFolderExternalReferenceCodeException() {
	}

	public DuplicateFolderExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateFolderExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateFolderExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}