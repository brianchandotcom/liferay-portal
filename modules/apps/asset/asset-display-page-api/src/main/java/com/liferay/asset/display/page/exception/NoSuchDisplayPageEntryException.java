/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchDisplayPageEntryException extends NoSuchModelException {

	public NoSuchDisplayPageEntryException() {
	}

	public NoSuchDisplayPageEntryException(String msg) {
		super(msg);
	}

	public NoSuchDisplayPageEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchDisplayPageEntryException(Throwable throwable) {
		super(throwable);
	}

}