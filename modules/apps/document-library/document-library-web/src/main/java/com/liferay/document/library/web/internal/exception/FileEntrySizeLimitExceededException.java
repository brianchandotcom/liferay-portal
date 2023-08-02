/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jaime León Rosado
 */
public class FileEntrySizeLimitExceededException extends PortalException {

	public FileEntrySizeLimitExceededException() {
	}

	public FileEntrySizeLimitExceededException(String msg) {
		super(msg);
	}

	public FileEntrySizeLimitExceededException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public FileEntrySizeLimitExceededException(Throwable throwable) {
		super(throwable);
	}

}