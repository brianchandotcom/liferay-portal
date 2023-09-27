/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Roberto Díaz
 */
public class DLFileEntryPreviewGenerationException extends PortalException {

	public DLFileEntryPreviewGenerationException() {
	}

	public DLFileEntryPreviewGenerationException(String msg) {
		super(msg);
	}

	public DLFileEntryPreviewGenerationException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DLFileEntryPreviewGenerationException(Throwable throwable) {
		super(throwable);
	}

}