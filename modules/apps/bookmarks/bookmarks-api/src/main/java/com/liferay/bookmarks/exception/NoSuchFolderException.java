/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchFolderException extends NoSuchModelException {

	public NoSuchFolderException() {
	}

	public NoSuchFolderException(String msg) {
		super(msg);
	}

	public NoSuchFolderException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchFolderException(Throwable throwable) {
		super(throwable);
	}

}