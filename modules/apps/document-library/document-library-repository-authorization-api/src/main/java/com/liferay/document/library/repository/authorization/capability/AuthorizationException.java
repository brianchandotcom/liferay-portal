/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.authorization.capability;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public class AuthorizationException extends PortalException {

	protected AuthorizationException() {
	}

	protected AuthorizationException(String msg) {
		super(msg);
	}

	protected AuthorizationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	protected AuthorizationException(Throwable throwable) {
		super(throwable);
	}

}