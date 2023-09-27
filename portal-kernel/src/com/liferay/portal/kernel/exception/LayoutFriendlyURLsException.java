/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Sergio González
 */
public class LayoutFriendlyURLsException extends LocalizedException {

	public LayoutFriendlyURLsException() {
	}

	public LayoutFriendlyURLsException(String msg) {
		super(msg);
	}

	public LayoutFriendlyURLsException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public LayoutFriendlyURLsException(Throwable throwable) {
		super(throwable);
	}

}