/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.portal.tools.service.builder.test.compat720.exception;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateERCGroupEntryExternalReferenceCodeException extends SystemException {

	public DuplicateERCGroupEntryExternalReferenceCodeException() {
	}

	public DuplicateERCGroupEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateERCGroupEntryExternalReferenceCodeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateERCGroupEntryExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}