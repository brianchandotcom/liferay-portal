/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sass.compiler;

/**
 * @author David Truong
 */
public class SassCompilerException extends Exception {

	public SassCompilerException() {
	}

	public SassCompilerException(String message) {
		super(message);
	}

	public SassCompilerException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public SassCompilerException(Throwable throwable) {
		super(throwable);
	}

}