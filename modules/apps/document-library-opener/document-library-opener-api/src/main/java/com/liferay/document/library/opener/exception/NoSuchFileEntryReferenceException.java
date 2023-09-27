/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchFileEntryReferenceException extends NoSuchModelException {

	public NoSuchFileEntryReferenceException() {
	}

	public NoSuchFileEntryReferenceException(String msg) {
		super(msg);
	}

	public NoSuchFileEntryReferenceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchFileEntryReferenceException(Throwable throwable) {
		super(throwable);
	}

}