/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alicia García
 */
public class FileNameExtensionException extends PortalException {

	public FileNameExtensionException() {
	}

	public FileNameExtensionException(String msg) {
		super(msg);
	}

	public FileNameExtensionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public FileNameExtensionException(Throwable throwable) {
		super(throwable);
	}

}