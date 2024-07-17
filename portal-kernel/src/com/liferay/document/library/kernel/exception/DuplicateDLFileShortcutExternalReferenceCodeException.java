/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateDLFileShortcutExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateDLFileShortcutExternalReferenceCodeException() {
	}

	public DuplicateDLFileShortcutExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateDLFileShortcutExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateDLFileShortcutExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}