/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchEntryUsageException extends NoSuchModelException {

	public NoSuchEntryUsageException() {
	}

	public NoSuchEntryUsageException(String msg) {
		super(msg);
	}

	public NoSuchEntryUsageException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchEntryUsageException(Throwable throwable) {
		super(throwable);
	}

}