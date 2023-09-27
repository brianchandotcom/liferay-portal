/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xml;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentException extends Exception {

	public DocumentException() {
	}

	public DocumentException(String msg) {
		super(msg);
	}

	public DocumentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DocumentException(Throwable throwable) {
		super(throwable);
	}

}