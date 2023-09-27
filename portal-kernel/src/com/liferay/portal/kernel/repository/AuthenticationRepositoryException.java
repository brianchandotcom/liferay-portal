/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository;

/**
 * @author Adolfo Pérez
 */
public class AuthenticationRepositoryException extends RepositoryException {

	public AuthenticationRepositoryException() {
	}

	public AuthenticationRepositoryException(String msg) {
		super(msg);
	}

	public AuthenticationRepositoryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AuthenticationRepositoryException(Throwable throwable) {
		super(throwable);
	}

}