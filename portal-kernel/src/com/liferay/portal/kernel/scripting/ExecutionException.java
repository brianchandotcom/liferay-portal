/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scripting;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
public class ExecutionException extends ScriptingException {

	public ExecutionException() {
	}

	public ExecutionException(String msg) {
		super(msg);
	}

	public ExecutionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ExecutionException(Throwable throwable) {
		super(throwable);
	}

}