/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateMBCategoryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateMBCategoryExternalReferenceCodeException() {
	}

	public DuplicateMBCategoryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateMBCategoryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateMBCategoryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}