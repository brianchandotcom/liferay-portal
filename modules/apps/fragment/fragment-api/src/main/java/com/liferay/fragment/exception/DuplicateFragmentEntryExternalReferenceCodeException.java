/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateFragmentEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateFragmentEntryExternalReferenceCodeException() {
	}

	public DuplicateFragmentEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateFragmentEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateFragmentEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}