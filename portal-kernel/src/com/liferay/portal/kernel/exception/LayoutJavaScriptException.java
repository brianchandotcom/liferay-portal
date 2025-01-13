/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutJavaScriptException extends PortalException {

	public LayoutJavaScriptException() {
	}

	public LayoutJavaScriptException(String msg) {
		super(msg);
	}

	public LayoutJavaScriptException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public LayoutJavaScriptException(Throwable throwable) {
		super(throwable);
	}

}