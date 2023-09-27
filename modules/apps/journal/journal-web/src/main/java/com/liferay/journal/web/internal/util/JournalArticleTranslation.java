/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		_defaultLanguage = defaultLanguage;
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
		return _defaultLanguage;
	}

	private final boolean _defaultLanguage;
	private final Locale _locale;

}