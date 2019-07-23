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

package com.liferay.journal.web.internal.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author Pavel Savinov
 */
public class JournalArticleTranslation {

	public JournalArticleTranslation(boolean defaultLanguage, Locale locale) {
		_default = defaultLanguage;
		_locale = locale;
	}

	public String getLanguageId() {
		return LocaleUtil.toLanguageId(_locale);
	}

	public String getLanguageTag() {
		return StringUtil.toLowerCase(_locale.toLanguageTag());
	}

	public Locale getLocale() {
		return _locale;
	}

	public boolean isDefault() {
		return _default;
	}

	private final boolean _default;
	private final Locale _locale;

}