/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.internal.resource.exception;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Petteri Karttunen
 */
public class IllegalScopeParameterException extends SystemException {

	public IllegalScopeParameterException() {
	}

	public IllegalScopeParameterException(String msg) {
		super(msg);
	}

	public IllegalScopeParameterException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public IllegalScopeParameterException(Throwable throwable) {
		super(throwable);
	}

	private static final long serialVersionUID = 1L;

}