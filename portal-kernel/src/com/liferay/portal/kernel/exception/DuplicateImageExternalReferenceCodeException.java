/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.portal.kernel.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateImageExternalReferenceCodeException extends DuplicateExternalReferenceCodeException {

	public DuplicateImageExternalReferenceCodeException() {
	}

	public DuplicateImageExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateImageExternalReferenceCodeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateImageExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}