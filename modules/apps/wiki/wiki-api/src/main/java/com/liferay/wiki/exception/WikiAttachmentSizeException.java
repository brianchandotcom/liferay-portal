/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Roberto Díaz
 */
public class WikiAttachmentSizeException extends PortalException {

	public WikiAttachmentSizeException() {
	}

	public WikiAttachmentSizeException(String msg) {
		super(msg);
	}

	public WikiAttachmentSizeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public WikiAttachmentSizeException(Throwable throwable) {
		super(throwable);
	}

}