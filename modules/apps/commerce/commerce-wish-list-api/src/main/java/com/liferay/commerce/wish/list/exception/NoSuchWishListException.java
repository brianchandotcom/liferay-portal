/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Andrea Di Giorgi
 */
public class NoSuchWishListException extends NoSuchModelException {

	public NoSuchWishListException() {
	}

	public NoSuchWishListException(String msg) {
		super(msg);
	}

	public NoSuchWishListException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchWishListException(Throwable throwable) {
		super(throwable);
	}

}