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

package com.liferay.portal.language;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.LanguageWrapper;
import com.liferay.portal.kernel.language.UnicodeLanguage;
import com.liferay.portal.kernel.util.UnicodeFormatter;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides various translation related functionalities in unicode for language
 * keys specified in portlet configurations and portal resource bundles.
 *
 * @author Brian Wing Shun Chan
 * @see    LanguageImpl
 */
public class UnicodeLanguageImpl implements UnicodeLanguage {

	/**
	 * Returns the translated pattern in unicode using the current request's
	 * locale or, if the current request locale is not available, the server's
	 * default locale. If a translation for a given key does not exist, this
	 * method returns the requested key as the translation.
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the single argument to be substituted into the pattern
	 *         and translated, if possible
	 * @return the translated pattern in unicode, with the argument substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper argument) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(httpServletRequest, pattern, argument));
	}

	/**
	 * Returns the translated pattern in unicode using the current request's
	 * locale or, if the current request locale is not available, the server's
	 * default locale. If a translation for a given key does not exist, this
	 * method returns the requested key as the translation.
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the single argument to be substituted into the pattern
	 *         and translated, if possible
	 * @param  translateArguments whether the argument is translated
	 * @return the translated pattern in unicode, with the argument substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper argument, boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				httpServletRequest, pattern, argument, translateArguments));
	}

	/**
	 * Returns the translated pattern in unicode using the current request's
	 * locale or, if the current request locale is not available, the server's
	 * default locale. If a translation for a given key does not exist, this
	 * method returns the requested key as the translation.
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern and
	 *         translated, if possible
	 * @return the translated pattern in unicode, with the arguments substituted
	 *         in for the pattern's placeholders
	 */
	@Override
	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper[] arguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(httpServletRequest, pattern, arguments));
	}

	/**
	 * Returns the translated pattern in unicode using the current request's
	 * locale or, if the current request locale is not available, the server's
	 * default locale. If a translation for a given key does not exist, this
	 * method returns the requested key as the translation.
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern
	 * @param  translateArguments whether the arguments are translated
	 * @return the translated pattern in unicode, with the arguments substituted
	 *         in for the pattern's placeholders
	 */
	@Override
	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		LanguageWrapper[] arguments, boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				httpServletRequest, pattern, arguments, translateArguments));
	}

	/**
	 * Returns the translated pattern in unicode using the current request's
	 * locale or, if the current request locale is not available, the server's
	 * default locale. If a translation for a given key does not exist, this
	 * method returns the requested key as the translation.
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the single argument to be substituted into the pattern
	 *         and translated, if possible
	 * @return the translated pattern in unicode, with the argument substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object argument) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(httpServletRequest, pattern, argument));
	}

	/**
	 * Returns the translated pattern in unicode using the current request's
	 * locale or, if the current request locale is not available, the server's
	 * default locale. If a translation for a given key does not exist, this
	 * method returns the requested key as the translation.
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the single argument to be substituted into the pattern
	 *         and translated, if possible
	 * @param  translateArguments whether the argument is translated
	 * @return the translated pattern in unicode, with the argument substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(
		HttpServletRequest httpServletRequest, String pattern, Object argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				httpServletRequest, pattern, argument, translateArguments));
	}

	/**
	 * Returns the translated pattern in unicode using the current request's
	 * locale or, if the current request locale is not available, the server's
	 * default locale. If a translation for a given key does not exist, this
	 * method returns the requested key as the translation.
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern and
	 *         translated, if possible
	 * @return the translated pattern in unicode, with the arguments substituted
	 *         in for the pattern's placeholders
	 */
	@Override
	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object[] arguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(httpServletRequest, pattern, arguments));
	}

	/**
	 * Returns the translated pattern in unicode using the current request's
	 * locale or, if the current request locale is not available, the server's
	 * default locale. If a translation for a given key does not exist, this
	 * method returns the requested key as the translation.
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern
	 * @param  translateArguments whether the arguments are translated
	 * @return the translated pattern in unicode, with the arguments substituted
	 *         in for the pattern's placeholders
	 */
	@Override
	public String format(
		HttpServletRequest httpServletRequest, String pattern,
		Object[] arguments, boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				httpServletRequest, pattern, arguments, translateArguments));
	}

	/**
	 * Returns the translated pattern in unicode using the locale or, if the
	 * locale is not available, the server's default locale. If a translation
	 * for a given key does not exist, this method returns the requested key as
	 * the translation.
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  locale the locale to translate to
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the argument to be substituted into the pattern
	 * @return the translated pattern in unicode, with the argument substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(Locale locale, String pattern, Object argument) {
		return UnicodeFormatter.toString(
			LanguageUtil.format(locale, pattern, argument));
	}

	/**
	 * Returns the translated pattern in unicode using the locale or, if the
	 * locale is not available, the server's default locale. If a translation
	 * for a given key does not exist, this method returns the requested key as
	 * the translation.
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  locale the locale to translate to
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the argument to be substituted into the pattern
	 * @param  translateArguments whether the argument is translated
	 * @return the translated pattern in unicode, with the argument substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(
		Locale locale, String pattern, Object argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(locale, pattern, argument, translateArguments));
	}

	/**
	 * Returns the translated pattern in unicode using the locale or, if the
	 * locale is not available, the server's default locale. If a translation
	 * for a given key does not exist, this method returns the requested key as
	 * the translation.
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  locale the locale to translate to
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern
	 * @return the translated pattern in unicode, with the arguments substituted
	 *         in for the pattern's placeholders
	 */
	@Override
	public String format(Locale locale, String pattern, Object[] arguments) {
		return UnicodeFormatter.toString(
			LanguageUtil.format(locale, pattern, arguments));
	}

	/**
	 * Returns the translated pattern in unicode using the locale or, if the
	 * locale is not available, the server's default locale. If a translation
	 * for a given key does not exist, this method returns the requested key as
	 * the translation.
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  locale the locale to translate to
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern
	 * @param  translateArguments whether the arguments are translated
	 * @return the translated pattern in unicode, with the arguments substituted
	 *         in for the pattern's placeholders
	 */
	@Override
	public String format(
		Locale locale, String pattern, Object[] arguments,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				locale, pattern, arguments, translateArguments));
	}

	/**
	 * Returns the translated pattern in the resource bundle in unicode or, if
	 * the resource bundle is not available, the untranslated key in unicode. If
	 * a translation for a given key does not exist, this method returns the
	 * requested key in unicode as the translation.
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  resourceBundle the requested key's resource bundle
	 * @param  pattern the key to look up in the resource bundle. The key
	 *         follows the standard Java resource specification.
	 * @param  argument the argument to be substituted into the pattern
	 * @return the translated pattern in unicode, with the argument substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(resourceBundle, pattern, argument));
	}

	/**
	 * Returns the translated pattern in the resource bundle in unicode or, if
	 * the resource bundle is not available, the untranslated key in unicode. If
	 * a translation for a given key does not exist, this method returns the
	 * requested key in unicode as the translation.
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  resourceBundle the requested key's resource bundle
	 * @param  pattern the key to look up in the resource bundle. The key
	 *         follows the standard Java resource specification.
	 * @param  argument the argument to be substituted into the pattern
	 * @param  translateArguments whether the argument is translated
	 * @return the translated pattern in unicode, with the argument substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				resourceBundle, pattern, argument, translateArguments));
	}

	/**
	 * Returns the translated pattern in the resource bundle in unicode or, if
	 * the resource bundle is not available, the untranslated key in unicode. If
	 * a translation for a given key does not exist, this method returns the
	 * requested key in unicode as the translation.
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  resourceBundle the requested key's resource bundle
	 * @param  pattern the key to look up in the resource bundle. The key
	 *         follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern
	 * @return the translated pattern in unicode, with the arguments substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(resourceBundle, pattern, arguments));
	}

	/**
	 * Returns the translated pattern in the resource bundle in unicode or, if
	 * the resource bundle is not available, the untranslated key in unicode. If
	 * a translation for a given key does not exist, this method returns the
	 * requested key in unicode as the translation.
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  resourceBundle the requested key's resource bundle
	 * @param  pattern the key to look up in the resource bundle. The key
	 *         follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern
	 * @param  translateArguments whether the arguments are translated
	 * @return the translated pattern in unicode, with the arguments substituted
	 *         in for the pattern's placeholder
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments,
		boolean translateArguments) {

		return UnicodeFormatter.toString(
			LanguageUtil.format(
				resourceBundle, pattern, arguments, translateArguments));
	}

	/**
	 * Returns the key's translation from the portlet configuration in unicode,
	 * or from the portal's resource bundle if the portlet configuration is
	 * unavailable.
	 *
	 * @param  httpServletRequest the request used to determine the key's
	 *         context and locale
	 * @param  key the translation key
	 * @return the key's translation in unicode, or the unicode key if the
	 *         translation is unavailable
	 */
	@Override
	public String get(HttpServletRequest httpServletRequest, String key) {
		return UnicodeFormatter.toString(
			LanguageUtil.get(httpServletRequest, key));
	}

	/**
	 * Returns the key's translation from the portlet configuration in unicode,
	 * or from the portal's resource bundle if the portlet configuration is
	 * unavailable.
	 *
	 * @param  httpServletRequest the request used to determine the key's
	 *         context and locale
	 * @param  key the translation key
	 * @param  defaultValue the value to return if there is no matching
	 *         translation
	 * @return the key's translation in unicode, or the default value in unicode
	 *         if the translation is unavailable
	 */
	@Override
	public String get(
		HttpServletRequest httpServletRequest, String key,
		String defaultValue) {

		return UnicodeFormatter.toString(
			LanguageUtil.get(httpServletRequest, key, defaultValue));
	}

	/**
	 * Returns the key's translation from the portal's resource bundle in
	 * unicode.
	 *
	 * @param  locale the key's locale
	 * @param  key the translation key
	 * @return the key's translation in unicode
	 */
	@Override
	public String get(Locale locale, String key) {
		return UnicodeFormatter.toString(LanguageUtil.get(locale, key));
	}

	/**
	 * Returns the key's translation from the portal's resource bundle in
	 * unicode.
	 *
	 * @param  locale the key's locale
	 * @param  key the translation key
	 * @param  defaultValue the value to return if there is no matching
	 *         translation
	 * @return the key's translation in unicode, or the default value in unicode
	 *         if the translation is unavailable
	 */
	@Override
	public String get(Locale locale, String key, String defaultValue) {
		return UnicodeFormatter.toString(
			LanguageUtil.get(locale, key, defaultValue));
	}

	/**
	 * Returns the key's translation from the resource bundle in unicode.
	 *
	 * @param  resourceBundle the requested key's resource bundle
	 * @param  key the translation key
	 * @return the key's translation in unicode
	 */
	@Override
	public String get(ResourceBundle resourceBundle, String key) {
		return UnicodeFormatter.toString(LanguageUtil.get(resourceBundle, key));
	}

	/**
	 * Returns the key's translation from the resource bundle in unicode.
	 *
	 * @param  resourceBundle the requested key's resource bundle
	 * @param  key the translation key
	 * @param  defaultValue the value to return if there is no matching
	 *         translation
	 * @return the key's translation in unicode, or the default value in unicode
	 *         if the translation is unavailable
	 */
	@Override
	public String get(
		ResourceBundle resourceBundle, String key, String defaultValue) {

		return UnicodeFormatter.toString(
			LanguageUtil.get(resourceBundle, key, defaultValue));
	}

	/**
	 * Returns an exact localized description in unicode of the time interval
	 * (in milliseconds) in the largest unit possible.
	 *
	 * <p>
	 * For example, the following time intervals would be converted to the
	 * following time descriptions, using the English locale:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * 1000 = 1 Second
	 * </li>
	 * <li>
	 * 1001 = 1001 Milliseconds
	 * </li>
	 * <li>
	 * 86400000 = 1 Day
	 * </li>
	 * <li>
	 * 86401000 = 86401 Seconds
	 * </li>
	 * </ul>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  milliseconds the time interval in milliseconds to describe
	 * @return an exact localized description in unicode of the time interval in
	 *         the largest unit possible
	 */
	@Override
	public String getTimeDescription(
		HttpServletRequest httpServletRequest, long milliseconds) {

		return UnicodeFormatter.toString(
			LanguageUtil.getTimeDescription(httpServletRequest, milliseconds));
	}

	/**
	 * Returns an exact localized description in unicode of the time interval
	 * (in milliseconds) in the largest unit possible.
	 *
	 * <p>
	 * For example, the following time intervals would be converted to the
	 * following time descriptions, using the English locale:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * 1000 = 1 Second
	 * </li>
	 * <li>
	 * 1001 = 1001 Milliseconds
	 * </li>
	 * <li>
	 * 86400000 = 1 Day
	 * </li>
	 * <li>
	 * 86401000 = 86401 Seconds
	 * </li>
	 * </ul>
	 *
	 * @param  httpServletRequest the request used to determine the current
	 *         locale
	 * @param  milliseconds the time interval in milliseconds to describe
	 * @return an exact localized description in unicode of the time interval in
	 *         the largest unit possible
	 */
	@Override
	public String getTimeDescription(
		HttpServletRequest httpServletRequest, Long milliseconds) {

		return UnicodeFormatter.toString(
			LanguageUtil.getTimeDescription(httpServletRequest, milliseconds));
	}

}