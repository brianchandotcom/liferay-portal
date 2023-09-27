/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Lourdes Fernández Besada
 */
public class InfoFormInvalidLayoutModeException extends InfoFormException {

	public String getLocalizedMessage(Locale locale) {
		return LanguageUtil.get(
			locale, "the-form-cannot-be-submitted-from-this-view-mode");
	}

}