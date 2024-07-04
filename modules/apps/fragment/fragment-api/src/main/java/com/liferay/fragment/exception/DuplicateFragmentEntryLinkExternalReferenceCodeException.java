/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateFragmentEntryLinkExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateFragmentEntryLinkExternalReferenceCodeException() {
	}

	public DuplicateFragmentEntryLinkExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateFragmentEntryLinkExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateFragmentEntryLinkExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}