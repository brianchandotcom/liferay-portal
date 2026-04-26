/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchOrderAttachmentException extends NoSuchModelException {

	public NoSuchOrderAttachmentException() {
	}

	public NoSuchOrderAttachmentException(String msg) {
		super(msg);
	}

	public NoSuchOrderAttachmentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchOrderAttachmentException(Throwable throwable) {
		super(throwable);
	}

}