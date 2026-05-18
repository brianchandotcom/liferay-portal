/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Stefano Motta
 */
public class RoleSubtypeException extends PortalException {

	public RoleSubtypeException() {
	}

	public RoleSubtypeException(String msg) {
		super(msg);
	}

	public RoleSubtypeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RoleSubtypeException(Throwable throwable) {
		super(throwable);
	}

}