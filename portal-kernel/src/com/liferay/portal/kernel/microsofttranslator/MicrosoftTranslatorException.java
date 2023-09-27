/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.microsofttranslator;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Hugo Huijser
 */
public class MicrosoftTranslatorException extends PortalException {

	public MicrosoftTranslatorException() {
	}

	public MicrosoftTranslatorException(String msg) {
		super(msg);
	}

	public MicrosoftTranslatorException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public MicrosoftTranslatorException(Throwable throwable) {
		super(throwable);
	}

}