/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Arthur Chan
 */
public class DuplicateOpenIdConnectSessionException extends PortalException {

	public DuplicateOpenIdConnectSessionException() {
	}

	public DuplicateOpenIdConnectSessionException(String msg) {
		super(msg);
	}

	public DuplicateOpenIdConnectSessionException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateOpenIdConnectSessionException(Throwable throwable) {
		super(throwable);
	}

}