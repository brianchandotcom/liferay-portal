/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alexander Chow
 */
public class InvalidRequestException extends PortalException {

	public InvalidRequestException() {
	}

	public InvalidRequestException(String msg) {
		super(msg);
	}

	public InvalidRequestException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public InvalidRequestException(Throwable throwable) {
		super(throwable);
	}

}