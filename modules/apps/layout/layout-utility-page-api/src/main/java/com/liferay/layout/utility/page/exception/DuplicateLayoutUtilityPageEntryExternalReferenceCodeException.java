/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateLayoutUtilityPageEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateLayoutUtilityPageEntryExternalReferenceCodeException() {
	}

	public DuplicateLayoutUtilityPageEntryExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateLayoutUtilityPageEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateLayoutUtilityPageEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}