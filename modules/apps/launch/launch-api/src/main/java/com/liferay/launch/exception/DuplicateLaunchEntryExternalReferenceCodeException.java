/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateLaunchEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateLaunchEntryExternalReferenceCodeException() {
	}

	public DuplicateLaunchEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateLaunchEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateLaunchEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}