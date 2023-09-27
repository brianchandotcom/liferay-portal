/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateOrganizationException extends PortalException {

	public DuplicateOrganizationException() {
	}

	public DuplicateOrganizationException(String msg) {
		super(msg);
	}

	public DuplicateOrganizationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateOrganizationException(Throwable throwable) {
		super(throwable);
	}

}