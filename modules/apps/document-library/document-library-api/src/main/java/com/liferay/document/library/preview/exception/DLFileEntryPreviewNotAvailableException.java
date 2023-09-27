/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alejandro Tardín
 */
public class DLFileEntryPreviewNotAvailableException extends PortalException {

	public DLFileEntryPreviewNotAvailableException() {
	}

	public DLFileEntryPreviewNotAvailableException(String msg) {
		super(msg);
	}

	public DLFileEntryPreviewNotAvailableException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DLFileEntryPreviewNotAvailableException(Throwable throwable) {
		super(throwable);
	}

}