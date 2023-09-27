/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sass.compiler.dart.internal;

import com.liferay.sass.compiler.SassCompilerException;

/**
 * @author David Truong
 */
public class DartSassCompilerException extends SassCompilerException {

	public DartSassCompilerException() {
	}

	public DartSassCompilerException(String message) {
		super(message);
	}

	public DartSassCompilerException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public DartSassCompilerException(Throwable throwable) {
		super(throwable);
	}

}