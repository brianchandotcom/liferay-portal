/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchERCGroupEntryException extends NoSuchModelException {

	public NoSuchERCGroupEntryException() {
	}

	public NoSuchERCGroupEntryException(String msg) {
		super(msg);
	}

	public NoSuchERCGroupEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchERCGroupEntryException(Throwable throwable) {
		super(throwable);
	}

}