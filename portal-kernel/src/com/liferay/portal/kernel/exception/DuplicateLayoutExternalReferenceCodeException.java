/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateLayoutExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateLayoutExternalReferenceCodeException() {
	}

	public DuplicateLayoutExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateLayoutExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateLayoutExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}