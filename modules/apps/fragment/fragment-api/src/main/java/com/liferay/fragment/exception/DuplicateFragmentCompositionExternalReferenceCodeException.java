/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateFragmentCompositionExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateFragmentCompositionExternalReferenceCodeException() {
	}

	public DuplicateFragmentCompositionExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateFragmentCompositionExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateFragmentCompositionExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}