/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Ryan Park
 */
public class AppTitleException extends PortalException {

	public AppTitleException() {
	}

	public AppTitleException(String msg) {
		super(msg);
	}

	public AppTitleException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AppTitleException(Throwable throwable) {
		super(throwable);
	}

}