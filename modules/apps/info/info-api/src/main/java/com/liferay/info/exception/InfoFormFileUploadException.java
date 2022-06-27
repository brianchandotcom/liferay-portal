/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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