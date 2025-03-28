/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class AddressSubtypeException extends PortalException {

	public AddressSubtypeException() {
	}

	public AddressSubtypeException(String msg) {
		super(msg);
	}

	public AddressSubtypeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AddressSubtypeException(Throwable throwable) {
		super(throwable);
	}

}