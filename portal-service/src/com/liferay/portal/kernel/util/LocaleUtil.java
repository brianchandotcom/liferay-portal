/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class LocaleUtil {

	public static boolean equals(Locale locale1, Locale locale2) {
		return getInstance()._equals(locale1, locale2);
	}

	public static Locale fromLanguageId(String languageId) {
		return getInstance()._fromLanguageId(languageId, true);
	}

	public static Locale fromLanguageId(String languageId, boolean validate) {
		return getInstance()._fromLanguageId(languageId, validate);
	}

	public static Locale[] fromLanguageIds(List<String> languageIds) {
		return getInstance()._fromLanguageIds(languageIds);
	}

	public static Locale[] fromLanguageIds(String[] languageIds) {
		return getInstance()._fromLanguageIds(languageIds);
	}

	public static Locale getDefault() {
		return getInstance()._getDefault();
	}

	public static LocaleUtil getInstance() {
		PortalRuntimePermission.checkGetBeanProperty(LocaleUtil.class);

		return _instance;
	}

	public static Map<String, String> getISOLanguages(Locale locale) {
		return getInstance()._getISOLanguages(locale);
	}

	public static String getLongDisplayName(
		Locale locale, Set<String> duplicateLanguages) {

		return getInstance()._getLongDisplayName(locale, duplicateLanguages);
	}

	public static Locale getMostRelevantLocale() {
		return getInstance()._getMostRelevantLocale();
	}

	public static String getShortDisplayName(
		Locale locale, Set<String> duplicateLanguages) {

		return getInstance()._getShortDisplayName(locale, duplicateLanguages);
	}

	public static void setDefault(
		String userLanguage, String userCountry, String userVariant) {

		getInstance()._setDefault(userLanguage, userCountry, userVariant);
	}

	public static String toLanguageId(Locale locale) {
		return getInstance()._toLanguageId(locale);
	}

	public static String[] toLanguageIds(Locale[] locales) {
		return getInstance()._toLanguageIds(locales);
	}

	public static String toW3cLanguageId(Locale locale) {
		return getInstance()._toW3cLanguageId(locale);
	}

	public static String toW3cLanguageId(String languageId) {
		return getInstance()._toW3cLanguageId(languageId);
	}

	public static String[] toW3cLanguageIds(Locale[] locales) {
		return getInstance()._toW3cLanguageIds(locales);
	}

	public static String[] toW3cLanguageIds(String[] languageIds) {
		return getInstance()._toW3cLanguageIds(languageIds);
	}

	private LocaleUtil() {
		_locale = new Locale("en", "US");
	}

	private boolean _equals(Locale locale1, Locale locale2) {
		String languageId1 = _toLanguageId(locale1);
		String languageId2 = _toLanguageId(locale2);

		return languageId1.equalsIgnoreCase(languageId2);
	}

	private Locale _fromLanguageId(String languageId, boolean validate) {
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

			if (validate && !LanguageUtil.isAvailableLanguageCode(languageId)) {
				throw new IllegalArgumentException("Invalid locale " + locale);
			}

			_locales.put(languageId, locale);
		}
		catch (Exception e) {
			locale = null;

			if (_log.isWarnEnabled()) {
				_log.warn(languageId + " is not a valid language id");
			}
		}

		if (locale == null) {
			locale = _locale;
		}

		return locale;
	}

	private Locale[] _fromLanguageIds(List<String> languageIds) {
		Locale[] locales = new Locale[languageIds.size()];

		for (int i = 0; i < languageIds.size(); i++) {
			locales[i] = _fromLanguageId(languageIds.get(i), true);
		}

		return locales;
	}

	private Locale[] _fromLanguageIds(String[] languageIds) {
		Locale[] locales = new Locale[languageIds.length];

		for (int i = 0; i < languageIds.length; i++) {
			locales[i] = _fromLanguageId(languageIds[i], true);
		}

		return locales;
	}

	private Locale _getDefault() {
		Locale locale = LocaleThreadLocal.getDefaultLocale();

		if (locale != null) {
			return locale;
		}

		return _locale;
	}

	private String _getDisplayName(
		String language, String country, Locale locale,
		Set<String> duplicateLanguages) {

		StringBundler sb = new StringBundler(6);

		sb.append(language);

		if (duplicateLanguages.contains(locale.getLanguage())) {
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(country);
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		if (LanguageUtil.isBetaLocale(locale)) {
			sb.append(_BETA_SUFFIX);
		}

		return sb.toString();
	}

	private Map<String, String> _getISOLanguages(Locale locale) {
		Map<String, String> isoLanguages = new TreeMap<String, String>(
			String.CASE_INSENSITIVE_ORDER);

		for (String isoLanguageId : Locale.getISOLanguages()) {
			Locale isoLocale = _fromLanguageId(isoLanguageId, true);

			isoLanguages.put(
				isoLocale.getDisplayLanguage(locale), isoLanguageId);
		}

		return isoLanguages;
	}

	private String _getLongDisplayName(
		Locale locale, Set<String> duplicateLanguages) {

		return _getDisplayName(
			locale.getDisplayLanguage(locale), locale.getDisplayCountry(locale),
			locale, duplicateLanguages);
	}

	private Locale _getMostRelevantLocale() {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		if (locale == null) {
			locale = _getDefault();
		}

		return locale;
	}

	private String _getShortDisplayName(
		Locale locale, Set<String> duplicateLanguages) {

		String language = locale.getDisplayLanguage(locale);

		if (language.length() > 3) {
			language = locale.getLanguage();
			language = language.toUpperCase();
		}

		String country = locale.getCountry();

		return _getDisplayName(
			language, country.toUpperCase(), locale, duplicateLanguages);
	}

	private void _setDefault(
		String userLanguage, String userCountry, String userVariant) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

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

	private static final String _BETA_SUFFIX = " [Beta]";

	private static Log _log = LogFactoryUtil.getLog(LocaleUtil.class);

	private static LocaleUtil _instance = new LocaleUtil();

	private Locale _locale;
	private Map<String, Locale> _locales = new HashMap<String, Locale>();

}