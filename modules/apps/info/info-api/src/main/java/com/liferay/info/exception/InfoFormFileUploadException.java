/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Lourdes Fernández Besada
 */
public class InfoFormFileUploadException extends InfoFormException {

	public InfoFormFileUploadException() {
		_infoFieldUniqueId = StringPool.BLANK;
	}

	public InfoFormFileUploadException(String infoFieldUniqueId) {
		_infoFieldUniqueId = infoFieldUniqueId;
	}

	public String getInfoFieldUniqueId() {
		return _infoFieldUniqueId;
	}

	@Override
	public String getLocalizedMessage(Locale locale) {
		return LanguageUtil.get(
			locale, "an-unexpected-error-occurred-while-uploading-your-file");
	}

	public String getLocalizedMessage(String fieldLabel, Locale locale) {
		return LanguageUtil.format(
			locale, "x-an-unexpected-error-occurred-while-uploading-your-file",
			new String[] {fieldLabel}, false);
	}

	private final String _infoFieldUniqueId;

}