/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.webdav;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class WebDAVException extends PortalException {

	public WebDAVException() {
	}

	public WebDAVException(String msg) {
		super(msg);
	}

	public WebDAVException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public WebDAVException(Throwable throwable) {
		super(throwable);
	}

}