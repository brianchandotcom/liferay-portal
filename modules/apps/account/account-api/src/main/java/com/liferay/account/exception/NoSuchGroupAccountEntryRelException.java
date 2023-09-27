/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchGroupAccountEntryRelException extends NoSuchModelException {

	public NoSuchGroupAccountEntryRelException() {
	}

	public NoSuchGroupAccountEntryRelException(String msg) {
		super(msg);
	}

	public NoSuchGroupAccountEntryRelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchGroupAccountEntryRelException(Throwable throwable) {
		super(throwable);
	}

}