/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.spring.sample.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchSpringEntryException extends NoSuchModelException {

	public NoSuchSpringEntryException() {
	}

	public NoSuchSpringEntryException(String msg) {
		super(msg);
	}

	public NoSuchSpringEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchSpringEntryException(Throwable throwable) {
		super(throwable);
	}

}