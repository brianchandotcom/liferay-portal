/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateKBFolderExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateKBFolderExternalReferenceCodeException() {
	}

	public DuplicateKBFolderExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateKBFolderExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateKBFolderExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}