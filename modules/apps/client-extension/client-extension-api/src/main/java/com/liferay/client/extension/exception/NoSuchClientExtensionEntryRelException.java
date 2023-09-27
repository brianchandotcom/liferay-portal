/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchClientExtensionEntryRelException
	extends NoSuchModelException {

	public NoSuchClientExtensionEntryRelException() {
	}

	public NoSuchClientExtensionEntryRelException(String msg) {
		super(msg);
	}

	public NoSuchClientExtensionEntryRelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchClientExtensionEntryRelException(Throwable throwable) {
		super(throwable);
	}

}