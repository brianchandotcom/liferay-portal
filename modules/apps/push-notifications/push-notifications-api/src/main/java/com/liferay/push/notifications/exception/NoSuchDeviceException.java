/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Bruno Farache
 */
public class NoSuchDeviceException extends NoSuchModelException {

	public NoSuchDeviceException() {
	}

	public NoSuchDeviceException(String msg) {
		super(msg);
	}

	public NoSuchDeviceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchDeviceException(Throwable throwable) {
		super(throwable);
	}

}