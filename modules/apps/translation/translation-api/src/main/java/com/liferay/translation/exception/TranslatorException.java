/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public class TranslatorException extends PortalException {

	public TranslatorException() {
	}

	public TranslatorException(String msg) {
		super(msg);
	}

	public TranslatorException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public TranslatorException(Throwable throwable) {
		super(throwable);
	}

}