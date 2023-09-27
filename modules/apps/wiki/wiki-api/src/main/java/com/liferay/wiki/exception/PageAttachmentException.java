/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class PageAttachmentException extends NoSuchModelException {

	public PageAttachmentException() {
	}

	public PageAttachmentException(String msg) {
		super(msg);
	}

	public PageAttachmentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PageAttachmentException(Throwable throwable) {
		super(throwable);
	}

}