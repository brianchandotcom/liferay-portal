/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class RoleNameException extends PortalException {

	public RoleNameException() {
	}

	public RoleNameException(String msg) {
		super(msg);
	}

	public RoleNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RoleNameException(Throwable throwable) {
		super(throwable);
	}

}