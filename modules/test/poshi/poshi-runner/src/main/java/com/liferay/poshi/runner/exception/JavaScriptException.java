/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.exception;

/**
 * @author Kenji Heigel
 */
public class JavaScriptException extends PoshiRunnerWarningException {

	public JavaScriptException(String msg) {
		super(msg);
	}

	public JavaScriptException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}