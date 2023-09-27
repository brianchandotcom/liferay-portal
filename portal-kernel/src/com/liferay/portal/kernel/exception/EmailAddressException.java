/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class EmailAddressException extends PortalException {

	public EmailAddressException() {
	}

	public EmailAddressException(String msg) {
		super(msg);
	}

	public EmailAddressException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EmailAddressException(Throwable throwable) {
		super(throwable);
	}

}