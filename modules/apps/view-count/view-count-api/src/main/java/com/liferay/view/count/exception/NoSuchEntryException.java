/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.view.count.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Preston Crary
 */
public class NoSuchEntryException extends NoSuchModelException {

	public NoSuchEntryException() {
	}

	public NoSuchEntryException(String msg) {
		super(msg);
	}

	public NoSuchEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchEntryException(Throwable throwable) {
		super(throwable);
	}

}