/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class AntException extends Exception {

	public AntException() {
	}

	public AntException(String message) {
		super(message);
	}

	public AntException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public AntException(
		String message, Throwable throwable, boolean enableSuppression,
		boolean writableStackTrace) {

		super(message, throwable, enableSuppression, writableStackTrace);
	}

	public AntException(Throwable throwable) {
		super(throwable);
	}

}