/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchObjectEntryVersionException extends NoSuchModelException {

	public NoSuchObjectEntryVersionException() {
	}

	public NoSuchObjectEntryVersionException(String msg) {
		super(msg);
	}

	public NoSuchObjectEntryVersionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchObjectEntryVersionException(Throwable throwable) {
		super(throwable);
	}

}