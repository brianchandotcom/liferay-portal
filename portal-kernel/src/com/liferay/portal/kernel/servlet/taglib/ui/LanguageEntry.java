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

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.kernel.util.LocaleUtil;

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
		return LocaleUtil.getLongDisplayName(_locale, _duplicateLanguages);
	}

	public String getShortDisplayName() {
		return LocaleUtil.getShortDisplayName(_locale, _duplicateLanguages);
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