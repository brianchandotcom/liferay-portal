/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class InvalidRepositoryException extends PortalException {

	public InvalidRepositoryException() {
	}

	public InvalidRepositoryException(String msg) {
		super(msg);
	}

	public InvalidRepositoryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public InvalidRepositoryException(Throwable throwable) {
		super(throwable);
	}

}