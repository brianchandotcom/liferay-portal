/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.validator;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author James Lefeu
 */
public class LDAPFilterException extends PortalException {

	public LDAPFilterException() {
	}

	public LDAPFilterException(String msg) {
		super(msg);
	}

	public LDAPFilterException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public LDAPFilterException(Throwable throwable) {
		super(throwable);
	}

}