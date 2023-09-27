/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchObjectLayoutTabException extends NoSuchModelException {

	public NoSuchObjectLayoutTabException() {
	}

	public NoSuchObjectLayoutTabException(String msg) {
		super(msg);
	}

	public NoSuchObjectLayoutTabException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchObjectLayoutTabException(Throwable throwable) {
		super(throwable);
	}

}