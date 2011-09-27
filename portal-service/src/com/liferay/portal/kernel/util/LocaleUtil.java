/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Brian Wing Shun Chan
 */
public class LocaleUtil {

	public static Locale fromLanguageId(String languageId) {
		return _instance._fromLanguageId(languageId);
	}

	public static Locale[] fromLanguageIds(String[] languageIds) {
		return _instance._fromLanguageIds(languageIds);
	}

	public static Locale getDefault() {
		return _instance._getDefault();
	}

	public static LocaleUtil getInstance() {
		return _instance;
	}

	public static Map<String, String> getISOLanguages(Locale locale) {
		return _instance._getISOLanguages(locale);
	}

	public static void setDefault(
		String userLanguage, String userCountry, String userVariant) {

		_instance._setDefault(userLanguage, userCountry, userVariant);
	}

	public static String toLanguageId(Locale locale) {
		return _instance._toLanguageId(locale);
	}

	public static String[] toLanguageIds(Locale[] locales) {
		return _instance._toLanguageIds(locales);
	}

	public static String toW3cLanguageId(Locale locale) {
		return _instance._toW3cLanguageId(locale);
	}

	public static String toW3cLanguageId(String languageId) {
		return _instance._toW3cLanguageId(languageId);
	}

	public static String[] toW3cLanguageIds(Locale[] locales) {
		return _instance._toW3cLanguageIds(locales);
	}

	public static String[] toW3cLanguageIds(String[] languageIds) {
		return _instance._toW3cLanguageIds(languageIds);
	}

	private LocaleUtil() {
		_locale = new Locale("en", "US");
	}

	private Locale _fromLanguageId(String languageId) {
		if (languageId == null) {
			return _locale;
		}

		Locale locale = _locales.get(languageId);

		if (locale != null) {
			return locale;
		}

		try {
			int pos = languageId.indexOf(CharPool.UNDERLINE);

			if (pos == -1) {
				locale = new Locale(languageId);
			}
			else {
				String[] languageIdParts = StringUtil.split(
					languageId, CharPool.UNDERLINE);

				String languageCode = languageIdParts[0];
				String countryCode = languageIdParts[1];

				String variant = null;

				if (languageIdParts.length > 2) {
					variant = languageIdParts[2];
				}

				if (Validator.isNotNull(variant)) {
					locale = new Locale(languageCode, countryCode, variant);
				}
				else {
					locale = new Locale(languageCode, countryCode);
				}
			}

			if (_locales.size() < _MAX_LOCALES) {
				_locales.put(languageId, locale);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("There are too many entries in the locales map");
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(languageId + " is not a valid language id");
			}
		}

		if (locale == null) {
			locale = _locale;
		}

		return locale;
	}

	private Locale[] _fromLanguageIds(String[] languageIds) {
		Locale[] locales = new Locale[languageIds.length];

		for (int i = 0; i < languageIds.length; i++) {
			locales[i] = _fromLanguageId(languageIds[i]);
		}

		return locales;
	}

	private Locale _getDefault() {
		Locale locale = LocaleThreadLocal.getLocale();

		if (locale != null) {
			return locale;
		}

		return _locale;
	}

	private Map<String, String> _getISOLanguages(Locale locale) {
		Map<String, String> isoLanguages = new TreeMap<String, String>(
			String.CASE_INSENSITIVE_ORDER);

		for (String isoLanguageId : Locale.getISOLanguages()) {
			Locale isoLocale = _fromLanguageId(isoLanguageId);

			isoLanguages.put(
				isoLocale.getDisplayLanguage(locale), isoLanguageId);
		}

		return isoLanguages;
	}

	private void _setDefault(
		String userLanguage, String userCountry, String userVariant) {

		if (Validator.isNotNull(userLanguage) &&
			Validator.isNull(userCountry) && Validator.isNull(userVariant)) {

			_locale = new Locale(userLanguage);
		}
		else if (Validator.isNotNull(userLanguage) &&
				 Validator.isNotNull(userCountry) &&
				 Validator.isNull(userVariant)) {

			_locale = new Locale(userLanguage, userCountry);
		}
		else if (Validator.isNotNull(userLanguage) &&
				 Validator.isNotNull(userCountry) &&
				 Validator.isNotNull(userVariant)) {

			_locale = new Locale(userLanguage, userCountry, userVariant);
		}
	}

	private String _toLanguageId(Locale locale) {
		if (locale == null) {
			locale = _locale;
		}

		String country = locale.getCountry();

		boolean hasCountry = false;

		if (country.length() != 0) {
			hasCountry = true;
		}

		String variant = locale.getVariant();

		boolean hasVariant = false;

		if (variant.length() != 0) {
			hasVariant = true;
		}

		if (!hasCountry && !hasVariant) {
			return locale.getLanguage();
		}

		int length = 3;

		if (hasCountry && hasVariant) {
			length = 5;
		}

		StringBundler sb = new StringBundler(length);

		sb.append(locale.getLanguage());

		if (hasCountry) {
			sb.append(StringPool.UNDERLINE);
			sb.append(country);
		}

		if (hasVariant) {
			sb.append(StringPool.UNDERLINE);
			sb.append(variant);
		}

		return sb.toString();
	}

	private String[] _toLanguageIds(Locale[] locales) {
		String[] languageIds = new String[locales.length];

		for (int i = 0; i < locales.length; i++) {
			languageIds[i] = _toLanguageId(locales[i]);
		}

		return languageIds;
	}

	private String _toW3cLanguageId(Locale locale) {
		return _toW3cLanguageId(_toLanguageId(locale));
	}

	private String _toW3cLanguageId(String languageId) {
		return StringUtil.replace(
			languageId, CharPool.UNDERLINE, CharPool.MINUS);
	}

	private String[] _toW3cLanguageIds(Locale[] locales) {
		return _toW3cLanguageIds(_toLanguageIds(locales));
	}

	private String[] _toW3cLanguageIds(String[] languageIds) {
		String[] w3cLanguageIds = new String[languageIds.length];

		for (int i = 0; i < languageIds.length; i++) {
			w3cLanguageIds[i] = _toW3cLanguageId(languageIds[i]);
		}

		return w3cLanguageIds;
	}

	private static final int _MAX_LOCALES = 1000;

	private static LocaleUtil _instance = new LocaleUtil();

	private static Log _log = LogFactoryUtil.getLog(LocaleUtil.class);

	private Locale _locale;
	private Map<String, Locale> _locales = new HashMap<String, Locale>();

}