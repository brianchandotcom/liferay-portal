/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.portal.language.override.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Drew Brokke
 */
public class PLOEntryLanguageIdException extends PortalException {

	public PLOEntryLanguageIdException() {
	}

	public PLOEntryLanguageIdException(String msg) {
		super(msg);
	}

	public PLOEntryLanguageIdException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PLOEntryLanguageIdException(Throwable throwable) {
		super(throwable);
	}

}