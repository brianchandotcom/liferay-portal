/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class AddressZipException extends PortalException {

	public AddressZipException() {
	}

	public AddressZipException(String msg) {
		super(msg);
	}

	public AddressZipException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AddressZipException(Throwable throwable) {
		super(throwable);
	}

}