/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateCTEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateCTEntryExternalReferenceCodeException() {
	}

	public DuplicateCTEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateCTEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCTEntryExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}