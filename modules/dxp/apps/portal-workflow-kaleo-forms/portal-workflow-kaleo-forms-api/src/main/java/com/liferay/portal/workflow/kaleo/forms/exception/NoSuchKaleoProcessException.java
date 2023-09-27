/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * Thrown when the system is unable to find a required Kaleo process.
 *
 * @author Marcellus Tavares
 */
public class NoSuchKaleoProcessException extends NoSuchModelException {

	public NoSuchKaleoProcessException() {
	}

	public NoSuchKaleoProcessException(String msg) {
		super(msg);
	}

	public NoSuchKaleoProcessException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchKaleoProcessException(Throwable throwable) {
		super(throwable);
	}

}