/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Set;

/**
 * @author Julio Camarero
 */
public class LanguageEntry {

	public LanguageEntry(
		Set<String> duplicateLanguages, Locale currentLocale, Locale locale,
		String url, boolean disabled) {

		_duplicateLanguages = duplicateLanguages;
		_locale = locale;
		_url = url;
		_disabled = disabled;

		_languageId = LocaleUtil.toLanguageId(locale);

		if (LocaleUtil.equals(locale, currentLocale)) {
			_selected = true;
		}
		else {
			_selected = false;
		}
	}

	public String getLanguageId() {
		return _languageId;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getLongDisplayName() {
		return StringUtil.toLowerCase(
			LocaleUtil.getLongDisplayName(_locale, _duplicateLanguages));
	}

	public String getShortDisplayName() {
		return StringUtil.toLowerCase(
			LocaleUtil.getShortDisplayName(_locale, _duplicateLanguages));
	}

	public String getURL() {
		return _url;
	}

	public String getW3cLanguageId() {
		return LocaleUtil.toW3cLanguageId(_languageId);
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public boolean isSelected() {
		return _selected;
	}

	private final boolean _disabled;
	private final Set<String> _duplicateLanguages;
	private final String _languageId;
	private final Locale _locale;
	private final boolean _selected;
	private final String _url;

}