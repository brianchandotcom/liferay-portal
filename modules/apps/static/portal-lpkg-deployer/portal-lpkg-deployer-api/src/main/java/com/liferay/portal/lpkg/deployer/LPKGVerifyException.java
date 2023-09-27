/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer;

/**
 * @author Shuyang Zhou
 */
public class LPKGVerifyException extends RuntimeException {

	public LPKGVerifyException(String message) {
		super(message);
	}

	public LPKGVerifyException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public LPKGVerifyException(Throwable throwable) {
		super(throwable);
	}

}