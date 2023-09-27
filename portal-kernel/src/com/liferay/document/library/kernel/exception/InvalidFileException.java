/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class InvalidFileException extends PortalException {

	public InvalidFileException() {
	}

	public InvalidFileException(String msg) {
		super(msg);
	}

	public InvalidFileException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public InvalidFileException(Throwable throwable) {
		super(throwable);
	}

}