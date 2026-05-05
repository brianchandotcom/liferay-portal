/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchCacheReplicatorEntryException extends NoSuchModelException {

	public NoSuchCacheReplicatorEntryException() {
	}

	public NoSuchCacheReplicatorEntryException(String msg) {
		super(msg);
	}

	public NoSuchCacheReplicatorEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchCacheReplicatorEntryException(Throwable throwable) {
		super(throwable);
	}

}