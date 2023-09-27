/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alessio Antonio Rendina
 */
public class NoSuchAddressRestrictionException extends NoSuchModelException {

	public NoSuchAddressRestrictionException() {
	}

	public NoSuchAddressRestrictionException(String msg) {
		super(msg);
	}

	public NoSuchAddressRestrictionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchAddressRestrictionException(Throwable throwable) {
		super(throwable);
	}

}