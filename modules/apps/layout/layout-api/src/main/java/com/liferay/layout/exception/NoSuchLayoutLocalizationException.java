/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchLayoutLocalizationException extends NoSuchModelException {

	public NoSuchLayoutLocalizationException() {
	}

	public NoSuchLayoutLocalizationException(String msg) {
		super(msg);
	}

	public NoSuchLayoutLocalizationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchLayoutLocalizationException(Throwable throwable) {
		super(throwable);
	}

}