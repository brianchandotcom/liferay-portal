/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Luca Pellizzon
 */
public class NoSuchCTermEntryLocalizationException
	extends NoSuchModelException {

	public NoSuchCTermEntryLocalizationException() {
	}

	public NoSuchCTermEntryLocalizationException(String msg) {
		super(msg);
	}

	public NoSuchCTermEntryLocalizationException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchCTermEntryLocalizationException(Throwable throwable) {
		super(throwable);
	}

}