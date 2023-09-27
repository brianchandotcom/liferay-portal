/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class CompanyWebIdException extends PortalException {

	public CompanyWebIdException() {
	}

	public CompanyWebIdException(String msg) {
		super(msg);
	}

	public CompanyWebIdException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CompanyWebIdException(Throwable throwable) {
		super(throwable);
	}

}