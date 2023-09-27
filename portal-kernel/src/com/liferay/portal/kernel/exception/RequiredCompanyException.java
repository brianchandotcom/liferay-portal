/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class RequiredCompanyException extends PortalException {

	public RequiredCompanyException() {
	}

	public RequiredCompanyException(String msg) {
		super(msg);
	}

	public RequiredCompanyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequiredCompanyException(Throwable throwable) {
		super(throwable);
	}

}