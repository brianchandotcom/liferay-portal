/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Rubén Pulido
 */
public class InfoItemActionExecutionException extends PortalException {

	public InfoItemActionExecutionException() {
	}

	public InfoItemActionExecutionException(String message) {
		_message = message;
	}

	public String getLocalizedMessage(Locale locale) {
		if (_message != null) {
			return _message;
		}

		return LanguageUtil.get(locale, "your-request-failed-to-complete");
	}

	private String _message;

}