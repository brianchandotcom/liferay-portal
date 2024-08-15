/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Joao Victor Alves
 */
public class InvalidFriendlyURLException extends PortalException {

	public InvalidFriendlyURLException() {
	}

	public InvalidFriendlyURLException(String msg) {
		super(msg);
	}

	public InvalidFriendlyURLException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public InvalidFriendlyURLException(Throwable throwable) {
		super(throwable);
	}

}