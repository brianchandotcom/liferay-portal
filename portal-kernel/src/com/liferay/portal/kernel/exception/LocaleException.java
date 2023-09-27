/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class LocaleException extends PortalException {

	public static final int TYPE_CONTENT = 3;

	public static final int TYPE_DEFAULT = 4;

	public static final int TYPE_DISPLAY_SETTINGS = 1;

	public static final int TYPE_EXPORT_IMPORT = 2;

	public LocaleException() {
		_type = 0;
	}

	public LocaleException(int type) {
		_type = type;
	}

	public LocaleException(int type, String msg) {
		super(msg);

		_type = type;
	}

	public LocaleException(int type, String msg, Throwable throwable) {
		super(msg, throwable);

		_type = type;
	}

	public LocaleException(int type, Throwable throwable) {
		super(throwable);

		_type = type;
	}

	public Collection<String> getSourceAvailableLanguageIds() {
		if (_sourceAvailableLanguageIds != null) {
			return _sourceAvailableLanguageIds;
		}

		_sourceAvailableLanguageIds = _toLanguageIds(_sourceAvailableLocales);

		return _sourceAvailableLanguageIds;
	}

	public Collection<Locale> getSourceAvailableLocales() {
		if (_sourceAvailableLocales != null) {
			return _sourceAvailableLocales;
		}

		_sourceAvailableLocales = _toLocales(_sourceAvailableLanguageIds);

		return _sourceAvailableLocales;
	}

	public Collection<String> getTargetAvailableLanguageIds() {
		if (_targetAvailableLanguageIds != null) {
			return _targetAvailableLanguageIds;
		}

		_targetAvailableLanguageIds = _toLanguageIds(_targetAvailableLocales);

		return _targetAvailableLanguageIds;
	}

	public Collection<Locale> getTargetAvailableLocales() {
		if (_targetAvailableLocales != null) {
			return _targetAvailableLocales;
		}

		_targetAvailableLocales = _toLocales(_targetAvailableLanguageIds);

		return _targetAvailableLocales;
	}

	public int getType() {
		return _type;
	}

	public void setSourceAvailableLanguageIds(
		Collection<String> sourceAvailableLanguageIds) {

		_sourceAvailableLanguageIds = sourceAvailableLanguageIds;
		_sourceAvailableLocales = null;
	}

	public void setSourceAvailableLocales(
		Collection<Locale> sourceAvailableLocales) {

		_sourceAvailableLanguageIds = null;
		_sourceAvailableLocales = sourceAvailableLocales;
	}

	public void setTargetAvailableLanguageIds(
		Collection<String> targetAvailableLanguageIds) {

		_targetAvailableLanguageIds = targetAvailableLanguageIds;
		_targetAvailableLocales = null;
	}

	public void setTargetAvailableLocales(
		Collection<Locale> targetAvailableLocales) {

		_targetAvailableLanguageIds = null;
		_targetAvailableLocales = targetAvailableLocales;
	}

	private Collection<String> _toLanguageIds(Collection<Locale> locales) {
		if (locales == null) {
			return null;
		}

		Collection<String> languageIds = new ArrayList<>(locales.size());

		for (Locale locale : locales) {
			languageIds.add(String.valueOf(locale));
		}

		return languageIds;
	}

	private Collection<Locale> _toLocales(Collection<String> languageIds) {
		if (languageIds == null) {
			return null;
		}

		Collection<Locale> locales = new ArrayList<>(languageIds.size());

		for (String languageId : languageIds) {
			locales.add(LocaleUtil.fromLanguageId(languageId, false));
		}

		return locales;
	}

	private Collection<String> _sourceAvailableLanguageIds;
	private Collection<Locale> _sourceAvailableLocales;
	private Collection<String> _targetAvailableLanguageIds;
	private Collection<Locale> _targetAvailableLocales;
	private final int _type;

}