/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateRepositoryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateRepositoryExternalReferenceCodeException() {
	}

	public DuplicateRepositoryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateRepositoryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateRepositoryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}