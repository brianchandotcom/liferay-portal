/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchClientExtensionEntryException extends NoSuchModelException {

	public NoSuchClientExtensionEntryException() {
	}

	public NoSuchClientExtensionEntryException(String msg) {
		super(msg);
	}

	public NoSuchClientExtensionEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchClientExtensionEntryException(Throwable throwable) {
		super(throwable);
	}

}