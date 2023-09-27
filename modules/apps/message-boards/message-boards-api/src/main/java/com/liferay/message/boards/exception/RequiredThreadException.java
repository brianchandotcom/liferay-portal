/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class RequiredThreadException extends PortalException {

	public RequiredThreadException() {
	}

	public RequiredThreadException(String msg) {
		super(msg);
	}

	public RequiredThreadException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequiredThreadException(Throwable throwable) {
		super(throwable);
	}

}