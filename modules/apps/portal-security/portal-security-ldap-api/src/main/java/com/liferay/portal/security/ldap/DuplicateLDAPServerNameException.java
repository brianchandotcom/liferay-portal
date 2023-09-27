/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jonathan McCann
 */
public class DuplicateLDAPServerNameException extends PortalException {

	public DuplicateLDAPServerNameException() {
	}

	public DuplicateLDAPServerNameException(String msg) {
		super(msg);
	}

	public DuplicateLDAPServerNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateLDAPServerNameException(Throwable throwable) {
		super(throwable);
	}

}