/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWishListNameException extends PortalException {

	public CommerceWishListNameException() {
	}

	public CommerceWishListNameException(String msg) {
		super(msg);
	}

	public CommerceWishListNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CommerceWishListNameException(Throwable throwable) {
		super(throwable);
	}

}