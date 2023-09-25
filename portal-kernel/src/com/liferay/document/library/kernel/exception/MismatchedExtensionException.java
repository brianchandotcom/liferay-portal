/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class MismatchedExtensionException extends PortalException {

	public MismatchedExtensionException() {
	}

	public MismatchedExtensionException(String msg) {
		super(msg);
	}

	public MismatchedExtensionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public MismatchedExtensionException(Throwable throwable) {
		super(throwable);
	}

}