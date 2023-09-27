/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateMBMessageExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateMBMessageExternalReferenceCodeException() {
	}

	public DuplicateMBMessageExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateMBMessageExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateMBMessageExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}