/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jaime León Rosado
 */
public class EntrySizeLimitExceededException extends PortalException {

	public EntrySizeLimitExceededException() {
	}

	public EntrySizeLimitExceededException(String msg) {
		super(msg);
	}

	public EntrySizeLimitExceededException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EntrySizeLimitExceededException(Throwable throwable) {
		super(throwable);
	}

}