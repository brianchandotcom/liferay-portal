/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateSharingEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateSharingEntryExternalReferenceCodeException() {
	}

	public DuplicateSharingEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateSharingEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateSharingEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}