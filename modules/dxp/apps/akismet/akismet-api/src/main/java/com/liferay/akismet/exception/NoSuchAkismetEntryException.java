/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Jamie Sammons
 */
public class NoSuchAkismetEntryException extends NoSuchModelException {

	public NoSuchAkismetEntryException() {
	}

	public NoSuchAkismetEntryException(String msg) {
		super(msg);
	}

	public NoSuchAkismetEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchAkismetEntryException(Throwable throwable) {
		super(throwable);
	}

}