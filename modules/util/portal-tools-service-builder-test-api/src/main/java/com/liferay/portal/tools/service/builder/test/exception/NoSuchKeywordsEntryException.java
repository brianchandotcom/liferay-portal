/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchKeywordsEntryException extends NoSuchModelException {

	public NoSuchKeywordsEntryException() {
	}

	public NoSuchKeywordsEntryException(String msg) {
		super(msg);
	}

	public NoSuchKeywordsEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchKeywordsEntryException(Throwable throwable) {
		super(throwable);
	}

}