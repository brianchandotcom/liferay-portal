/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auto.login;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class AutoLoginException extends PortalException {

	public AutoLoginException() {
	}

	public AutoLoginException(String msg) {
		super(msg);
	}

	public AutoLoginException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AutoLoginException(Throwable throwable) {
		super(throwable);
	}

}