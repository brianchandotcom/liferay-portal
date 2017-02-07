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

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheMapSynchronizeUtil;
import com.liferay.portal.kernel.cache.PortalCacheMapSynchronizeUtil.Synchronizer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageWrapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.text.MessageFormat;
import java.text.NumberFormat;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides various translation related functionalities for language keys
 * specified in portlet configurations and portal resource bundles.
 *
 * <p>
 * You can disable translations by setting the
 * <code>translations.disabled</code> property to <code>true</code> in
 * <code>portal.properties</code>.
 * </p>
 *
 * <p>
 * Depending on the context passed into these methods, the lookup might be
 * limited to the portal's resource bundle (e.g. when only a locale is passed),
 * or extended to include an individual portlet's resource bundle (e.g. when a
 * request object is passed). A portlet's resource bundle overrides the portal's
 * resources when both are present.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Andrius Vitkauskas
 * @author Eduardo Lundgren
 */
@DoPrivileged
public class LanguageImpl implements Language, Serializable {

	public void afterPropertiesSet() {
		_companyLocalesPortalCache = MultiVMPoolUtil.getPortalCache(
			_COMPANY_LOCALES_PORTAL_CACHE_NAME);

		PortalCacheMapSynchronizeUtil.synchronize(
			_companyLocalesPortalCache, _companyLocalesBags,
			_removeSynchronizer);

		_groupLocalesPortalCache = MultiVMPoolUtil.getPortalCache(
			_GROUP_LOCALES_PORTAL_CACHE_NAME);

		PortalCacheMapSynchronizeUtil.synchronize(
			_groupLocalesPortalCache, _groupLanguageCodeLocalesMapMap,
			_removeSynchronizer);

		PortalCacheMapSynchronizeUtil.synchronize(
			_groupLocalesPortalCache, _groupLanguageIdLocalesMap,
			_removeSynchronizer);
	}

	/**
	 * Returns the translated pattern using the current request's locale or, if
	 * the current request locale is not available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portlet configuration first, and if it's not
	 * found, it is done on the portal's resource bundle. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  request the request used to determine the current locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the single argument to be substituted into the pattern
	 *         and translated, if possible
	 * @return the translated pattern, with the argument substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument) {

		return format(request, pattern, new LanguageWrapper[] {argument}, true);
	}

	/**
	 * Returns the translated pattern using the current request's locale or, if
	 * the current request locale is not available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portlet configuration first, and if it's not
	 * found, it is done on the portal's resource bundle. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  request the request used to determine the current locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the single argument to be substituted into the pattern
	 *         and translated, if possible
	 * @param  translateArguments whether the argument is translated
	 * @return the translated pattern, with the argument substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument,
		boolean translateArguments) {

		return format(
			request, pattern, new LanguageWrapper[] {argument},
			translateArguments);
	}

	/**
	 * Returns the translated pattern using the current request's locale or, if
	 * the current request locale is not available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portlet configuration first, and if it's not
	 * found, it is done on the portal's resource bundle. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  request the request used to determine the current locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern and
	 *         translated, if possible
	 * @return the translated pattern, with the arguments substituted in for the
	 *         pattern's placeholders
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern,
		LanguageWrapper[] arguments) {

		return format(request, pattern, arguments, true);
	}

	/**
	 * Returns the translated pattern using the current request's locale or, if
	 * the current request locale is not available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portlet configuration first, and if it's not
	 * found, it is done on the portal's resource bundle. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  request the request used to determine the current locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern
	 * @param  translateArguments whether the arguments are translated
	 * @return the translated pattern, with the arguments substituted in for the
	 *         pattern's placeholders
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper[] arguments,
		boolean translateArguments) {

		if (PropsValues.TRANSLATIONS_DISABLED) {
			return pattern;
		}

		String value = null;

		try {
			pattern = get(request, pattern);

			if (ArrayUtil.isNotEmpty(arguments)) {
				pattern = _escapePattern(pattern);

				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						formattedArguments[i] =
							arguments[i].getBefore() +
								get(request, arguments[i].getText()) +
									arguments[i].getAfter();
					}
					else {
						formattedArguments[i] =
							arguments[i].getBefore() + arguments[i].getText() +
								arguments[i].getAfter();
					}
				}

				MessageFormat messageFormat = decorateMessageFormat(
					request, pattern, formattedArguments);

				value = messageFormat.format(formattedArguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	/**
	 * Returns the translated pattern using the current request's locale or, if
	 * the current request locale is not available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portlet configuration first, and if it's not
	 * found, it is done on the portal's resource bundle. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  request the request used to determine the current locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the single argument to be substituted into the pattern
	 *         and translated, if possible
	 * @return the translated pattern, with the argument substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, Object argument) {

		return format(request, pattern, new Object[] {argument}, true);
	}

	/**
	 * Returns the translated pattern using the current request's locale or, if
	 * the current request locale is not available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portlet configuration first, and if it's not
	 * found, it is done on the portal's resource bundle. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
	 *
	 * <p>
	 * The substitute placeholder (e.g. <code>{0}</code>) is replaced with the
	 * argument, following the standard Java {@link ResourceBundle} notion of
	 * index based substitution.
	 * </p>
	 *
	 * @param  request the request used to determine the current locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  argument the single argument to be substituted into the pattern
	 *         and translated, if possible
	 * @param  translateArguments whether the argument is translated
	 * @return the translated pattern, with the argument substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, Object argument,
		boolean translateArguments) {

		return format(
			request, pattern, new Object[] {argument}, translateArguments);
	}

	/**
	 * Returns the translated pattern using the current request's locale or, if
	 * the current request locale is not available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portlet configuration first, and if it's not
	 * found, it is done on the portal's resource bundle. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  request the request used to determine the current locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern and
	 *         translated, if possible
	 * @return the translated pattern, with the arguments substituted in for the
	 *         pattern's placeholders
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, Object[] arguments) {

		return format(request, pattern, arguments, true);
	}

	/**
	 * Returns the translated pattern using the current request's locale or, if
	 * the current request locale is not available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portlet configuration first, and if it's not
	 * found, it is done on the portal's resource bundle. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
	 *
	 * <p>
	 * The substitute placeholders (e.g. <code>{0}</code>, <code>{1}</code>,
	 * <code>{2}</code>, etc.) are replaced with the arguments, following the
	 * standard Java {@link ResourceBundle} notion of index based substitution.
	 * </p>
	 *
	 * @param  request the request used to determine the current locale
	 * @param  pattern the key to look up in the current locale's resource file.
	 *         The key follows the standard Java resource specification.
	 * @param  arguments the arguments to be substituted into the pattern
	 * @param  translateArguments whether the arguments are translated
	 * @return the translated pattern, with the arguments substituted in for the
	 *         pattern's placeholders
	 */
	@Override
	public String format(
		HttpServletRequest request, String pattern, Object[] arguments,
		boolean translateArguments) {

		if (PropsValues.TRANSLATIONS_DISABLED) {
			return pattern;
		}

		String value = null;

		try {
			pattern = get(request, pattern);

			if (ArrayUtil.isNotEmpty(arguments)) {
				pattern = _escapePattern(pattern);

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						arguments[i] = get(request, arguments[i].toString());
					}
				}

				MessageFormat messageFormat = decorateMessageFormat(
					request, pattern, arguments);

				value = messageFormat.format(arguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	/**
	 * Returns the translated pattern using the locale or, if the locale is not
	 * available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portal's resource bundle. If a translation for
	 * a given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
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
	 * @return the translated pattern, with the arguments substituted in for the
	 *         pattern's placeholders
	 */
	@Override
	public String format(
		Locale locale, String pattern, List<Object> arguments) {

		return format(locale, pattern, arguments.toArray(), true);
	}

	/**
	 * Returns the translated pattern using the locale or, if the locale is not
	 * available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portal's resource bundle. If a translation for
	 * a given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
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
	 * @return the translated pattern, with the argument substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(Locale locale, String pattern, Object argument) {
		return format(locale, pattern, new Object[] {argument}, true);
	}

	/**
	 * Returns the translated pattern using the locale or, if the locale is not
	 * available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portal's resource bundle. If a translation for
	 * a given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
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
	 * @return the translated pattern, with the argument substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(
		Locale locale, String pattern, Object argument,
		boolean translateArguments) {

		return format(
			locale, pattern, new Object[] {argument}, translateArguments);
	}

	/**
	 * Returns the translated pattern using the locale or, if the locale is not
	 * available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portal's resource bundle. If a translation for
	 * a given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
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
	 * @return the translated pattern, with the arguments substituted in for the
	 *         pattern's placeholders
	 */
	@Override
	public String format(Locale locale, String pattern, Object[] arguments) {
		return format(locale, pattern, arguments, true);
	}

	/**
	 * Returns the translated pattern using the locale or, if the locale is not
	 * available, the server's default locale.
	 *
	 * <p>
	 * The lookup is done on the portal's resource bundle. If a translation for
	 * a given key does not exist, this method returns the requested key as the
	 * translation.
	 * </p>
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
	 * @return the translated pattern, with the arguments substituted in for the
	 *         pattern's placeholders
	 */
	@Override
	public String format(
		Locale locale, String pattern, Object[] arguments,
		boolean translateArguments) {

		if (PropsValues.TRANSLATIONS_DISABLED) {
			return pattern;
		}

		String value = null;

		try {
			pattern = get(locale, pattern);

			if (ArrayUtil.isNotEmpty(arguments)) {
				pattern = _escapePattern(pattern);

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						arguments[i] = get(locale, arguments[i].toString());
					}
				}

				MessageFormat messageFormat = decorateMessageFormat(
					locale, pattern, arguments);

				value = messageFormat.format(arguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	/**
	 * Returns the translated pattern in the resource bundle or, if the resource
	 * bundle is not available, the untranslated key. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
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
	 * @return the translated pattern, with the argument substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument) {

		return format(resourceBundle, pattern, new Object[] {argument}, true);
	}

	/**
	 * Returns the translated pattern in the resource bundle or, if the resource
	 * bundle is not available, the untranslated key. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
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
	 * @return the translated pattern, with the argument substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument,
		boolean translateArguments) {

		return format(
			resourceBundle, pattern, new Object[] {argument},
			translateArguments);
	}

	/**
	 * Returns the translated pattern in the resource bundle or, if the resource
	 * bundle is not available, the untranslated key. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
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
	 * @return the translated pattern, with the arguments substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments) {

		return format(resourceBundle, pattern, arguments, true);
	}

	/**
	 * Returns the translated pattern in the resource bundle or, if the resource
	 * bundle is not available, the untranslated key. If a translation for a
	 * given key does not exist, this method returns the requested key as the
	 * translation.
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
	 * @return the translated pattern, with the arguments substituted in for the
	 *         pattern's placeholder
	 */
	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments,
		boolean translateArguments) {

		if (PropsValues.TRANSLATIONS_DISABLED) {
			return pattern;
		}

		String value = null;

		try {
			pattern = get(resourceBundle, pattern);

			if (ArrayUtil.isNotEmpty(arguments)) {
				pattern = _escapePattern(pattern);

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						arguments[i] = get(
							resourceBundle, arguments[i].toString());
					}
				}

				MessageFormat messageFormat = decorateMessageFormat(
					resourceBundle.getLocale(), pattern, arguments);

				value = messageFormat.format(arguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	/**
	 * Returns the key's translation from the portlet configuration, or from the
	 * portal's resource bundle if the portlet configuration is unavailable.
	 *
	 * @param  request the request used to determine the key's context and
	 *         locale
	 * @param  resourceBundle the requested key's resource bundle
	 * @param  key the translation key
	 * @return the key's translation, or the key if the translation is
	 *         unavailable
	 */
	@Override
	public String get(
		HttpServletRequest request, ResourceBundle resourceBundle, String key) {

		return get(request, resourceBundle, key, key);
	}

	@Override
	public String get(
		HttpServletRequest request, ResourceBundle resourceBundle, String key,
		String defaultValue) {

		String value = _get(resourceBundle, key);

		if (value != null) {
			return value;
		}

		return get(request, key, defaultValue);
	}

	@Override
	public String get(HttpServletRequest request, String key) {
		return get(request, key, key);
	}

	/**
	 * Returns the key's translation from the portlet configuration, or from the
	 * portal's resource bundle if the portlet configuration is unavailable.
	 *
	 * @param  request the request used to determine the key's context and
	 *         locale
	 * @param  key the translation key
	 * @param  defaultValue the value to return if there is no matching
	 *         translation
	 * @return the key's translation, or the default value if the translation is
	 *         unavailable
	 */
	@Override
	public String get(
		HttpServletRequest request, String key, String defaultValue) {

		if ((request == null) || (key == null)) {
			return defaultValue;
		}

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		Locale locale = _getLocale(request);

		if (portletConfig == null) {
			return get(locale, key, defaultValue);
		}

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		if (resourceBundle.containsKey(key)) {
			return _get(resourceBundle, key);
		}

		return get(locale, key, defaultValue);
	}

	/**
	 * Returns the key's translation from the portal's resource bundle.
	 *
	 * @param  locale the key's locale
	 * @param  key the translation key
	 * @return the key's translation
	 */
	@Override
	public String get(Locale locale, String key) {
		return get(locale, key, key);
	}

	/**
	 * Returns the key's translation from the portal's resource bundle.
	 *
	 * @param  locale the key's locale
	 * @param  key the translation key
	 * @param  defaultValue the value to return if there is no matching
	 *         translation
	 * @return the key's translation, or the default value if the translation is
	 *         unavailable
	 */
	@Override
	public String get(Locale locale, String key, String defaultValue) {
		if (PropsValues.TRANSLATIONS_DISABLED) {
			return key;
		}

		if ((locale == null) || (key == null)) {
			return defaultValue;
		}

		String value = LanguageResources.getMessage(locale, key);

		if (value != null) {
			return LanguageResources.fixValue(value);
		}

		if (value == null) {
			if ((key.length() > 0) &&
				(key.charAt(key.length() - 1) == CharPool.CLOSE_BRACKET)) {

				int pos = key.lastIndexOf(CharPool.OPEN_BRACKET);

				if (pos != -1) {
					key = key.substring(0, pos);

					return get(locale, key, defaultValue);
				}
			}
		}

		return defaultValue;
	}

	/**
	 * Returns the key's translation from the resource bundle.
	 *
	 * @param  resourceBundle the requested key's resource bundle
	 * @param  key the translation key
	 * @return the key's translation
	 */
	@Override
	public String get(ResourceBundle resourceBundle, String key) {
		return get(resourceBundle, key, key);
	}

	/**
	 * Returns the key's translation from the resource bundle.
	 *
	 * @param  resourceBundle the requested key's resource bundle
	 * @param  key the translation key
	 * @param  defaultValue the value to return if there is no matching
	 *         translation
	 * @return the key's translation, or the default value if the translation is
	 *         unavailable
	 */
	@Override
	public String get(
		ResourceBundle resourceBundle, String key, String defaultValue) {

		String value = _get(resourceBundle, key);

		if (value != null) {
			return value;
		}

		return defaultValue;
	}

	/**
	 * Returns the locales configured for the portal. Locales can be configured
	 * in <code>portal.properties</code> using the <code>locales</code> and
	 * <code>locales.enabled</code> keys.
	 *
	 * @return the locales configured for the portal
	 */
	@Override
	public Set<Locale> getAvailableLocales() {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag.getAvailableLocales();
	}

	@Override
	public Set<Locale> getAvailableLocales(long groupId) {
		if (groupId <= 0) {
			return getAvailableLocales();
		}

		try {
			if (isInheritLocales(groupId)) {
				return getAvailableLocales();
			}
		}
		catch (Exception e) {
		}

		Map<String, Locale> groupLanguageIdLocalesMap =
			_getGroupLanguageIdLocalesMap(groupId);

		return new HashSet<>(groupLanguageIdLocalesMap.values());
	}

	@Override
	public String getBCP47LanguageId(HttpServletRequest request) {
		Locale locale = PortalUtil.getLocale(request);

		return getBCP47LanguageId(locale);
	}

	@Override
	public String getBCP47LanguageId(Locale locale) {
		return LocaleUtil.toBCP47LanguageId(locale);
	}

	@Override
	public String getBCP47LanguageId(PortletRequest portletRequest) {
		Locale locale = PortalUtil.getLocale(portletRequest);

		return getBCP47LanguageId(locale);
	}

	/**
	 * Returns the language ID that the request is served with. The language ID
	 * is returned as a language code (e.g. <code>en</code>) or a specific
	 * variant (e.g. <code>en_GB</code>).
	 *
	 * @param  request the request used to determine the language ID
	 * @return the language ID that the request is served with
	 */
	@Override
	public String getLanguageId(HttpServletRequest request) {
		String languageId = ParamUtil.getString(request, "languageId");

		if (Validator.isNotNull(languageId)) {
			CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

			if (companyLocalesBag.containsLanguageCode(languageId) ||
				companyLocalesBag.containsLanguageId(languageId)) {

				return languageId;
			}
		}

		Locale locale = PortalUtil.getLocale(request);

		return getLanguageId(locale);
	}

	/**
	 * Returns the language ID from the locale. The language ID is returned as a
	 * language code (e.g. <code>en</code>) or a specific variant (e.g.
	 * <code>en_GB</code>).
	 *
	 * @param  locale the locale used to determine the language ID
	 * @return the language ID from the locale
	 */
	@Override
	public String getLanguageId(Locale locale) {
		return LocaleUtil.toLanguageId(locale);
	}

	/**
	 * Returns the language ID that the {@link PortletRequest} is served with.
	 * The language ID is returned as a language code (e.g. <code>en</code>) or
	 * a specific variant (e.g. <code>en_GB</code>).
	 *
	 * @param  portletRequest the portlet request used to determine the language
	 *         ID
	 * @return the language ID that the portlet request is served with
	 */
	@Override
	public String getLanguageId(PortletRequest portletRequest) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getLanguageId(request);
	}

	@Override
	public Locale getLocale(long groupId, String languageCode) {
		Map<String, Locale> groupLanguageCodeLocalesMap =
			_getGroupLanguageCodeLocalesMap(groupId);

		return groupLanguageCodeLocalesMap.get(languageCode);
	}

	/**
	 * Returns the locale associated with the language code.
	 *
	 * @param  languageCode the code representation of a language (e.g.
	 *         <code>en</code> and <code>en_GB</code>)
	 * @return the locale associated with the language code
	 */
	@Override
	public Locale getLocale(String languageCode) {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag.getByLanguageCode(languageCode);
	}

	@Override
	public ResourceBundleLoader getPortalResourceBundleLoader() {
		return LanguageResources.RESOURCE_BUNDLE_LOADER;
	}

	@Override
	public Set<Locale> getSupportedLocales() {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag._supportedLocalesSet;
	}

	/**
	 * Returns an exact localized description of the time interval (in
	 * milliseconds) in the largest unit possible.
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
	 * @param  request the request used to determine the current locale
	 * @param  milliseconds the time interval in milliseconds to describe
	 * @return an exact localized description of the time interval in the
	 *         largest unit possible
	 */
	@Override
	public String getTimeDescription(
		HttpServletRequest request, long milliseconds) {

		return getTimeDescription(request, milliseconds, false);
	}

	/**
	 * Returns an approximate or exact localized description of the time
	 * interval (in milliseconds) in the largest unit possible.
	 *
	 * <p>
	 * Approximate descriptions round the time to the largest possible unit and
	 * ignores the rest. For example, using the English locale:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * Any time interval 1000-1999 = 1 Second
	 * </li>
	 * <li>
	 * Any time interval 86400000-172799999 = 1 Day
	 * </li>
	 * </ul>
	 *
	 * <p>
	 * Otherwise, exact descriptions would follow a similar conversion pattern
	 * as below:
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
	 * @param  request the request used to determine the current locale
	 * @param  milliseconds the time interval in milliseconds to describe
	 * @param  approximate whether the time description is approximate
	 * @return a localized description of the time interval in the largest unit
	 *         possible
	 */
	@Override
	public String getTimeDescription(
		HttpServletRequest request, long milliseconds, boolean approximate) {

		String description = Time.getDescription(milliseconds, approximate);

		String value = null;

		try {
			int pos = description.indexOf(CharPool.SPACE);

			String x = description.substring(0, pos);

			value = x.concat(StringPool.SPACE).concat(
				get(
					request,
					StringUtil.toLowerCase(
						description.substring(pos + 1, description.length()))));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	/**
	 * Returns an exact localized description of the time interval (in
	 * milliseconds) in the largest unit possible.
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
	 * @param  request the request used to determine the current locale
	 * @param  milliseconds the time interval in milliseconds to describe
	 * @return an exact localized description of the time interval in the
	 *         largest unit possible
	 */
	@Override
	public String getTimeDescription(
		HttpServletRequest request, Long milliseconds) {

		return getTimeDescription(request, milliseconds.longValue());
	}

	/**
	 * Returns an exact localized description of the time interval (in
	 * milliseconds) in the largest unit possible.
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
	 * @param  locale the locale used to determine the language
	 * @param  milliseconds the time interval in milliseconds to describe
	 * @return an exact localized description of the time interval in the
	 *         largest unit possible
	 */
	@Override
	public String getTimeDescription(Locale locale, long milliseconds) {
		return getTimeDescription(locale, milliseconds, false);
	}

	/**
	 * Returns an approximate or exact localized description of the time
	 * interval (in milliseconds) in the largest unit possible.
	 *
	 * <p>
	 * Approximate descriptions round the time to the largest possible unit and
	 * ignores the rest. For example, using the English locale:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * Any time interval 1000-1999 = 1 Second
	 * </li>
	 * <li>
	 * Any time interval 86400000-172799999 = 1 Day
	 * </li>
	 * </ul>
	 *
	 * <p>
	 * Otherwise, exact descriptions would follow a similar conversion pattern
	 * as below:
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
	 * @param  locale the locale used to determine the language
	 * @param  milliseconds the time interval in milliseconds to describe
	 * @param  approximate whether the time description is approximate
	 * @return a localized description of the time interval in the largest unit
	 *         possible
	 */
	@Override
	public String getTimeDescription(
		Locale locale, long milliseconds, boolean approximate) {

		String description = Time.getDescription(milliseconds, approximate);

		String value = null;

		try {
			int pos = description.indexOf(CharPool.SPACE);

			String x = description.substring(0, pos);

			value = x.concat(StringPool.SPACE).concat(
				get(
					locale,
					StringUtil.toLowerCase(
						description.substring(pos + 1, description.length()))));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return value;
	}

	/**
	 * Returns an exact localized description of the time interval (in
	 * milliseconds) in the largest unit possible.
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
	 * @param  locale the locale used to determine the language
	 * @param  milliseconds the time interval in milliseconds to describe
	 * @return an exact localized description of the time interval in the
	 *         largest unit possible
	 */
	@Override
	public String getTimeDescription(Locale locale, Long milliseconds) {
		return getTimeDescription(locale, milliseconds.longValue());
	}

	@Override
	public void init() {
		_companyLocalesBags.clear();
	}

	/**
	 * Returns <code>true</code> if the language code is configured to be
	 * available. Locales can be configured in <code>portal.properties</code>
	 * using the <code>locales</code> and <code>locales.enabled</code> keys.
	 *
	 * @param  languageCode the code representation of a language (e.g.
	 *         <code>en</code> and <code>en_GB</code>) to search for
	 * @return <code>true</code> if the language code is configured to be
	 *         available; <code>false</code> otherwise
	 */
	@Override
	public boolean isAvailableLanguageCode(String languageCode) {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag.containsLanguageCode(languageCode);
	}

	/**
	 * Returns <code>true</code> if the locale is configured to be available.
	 * Locales can be configured in <code>portal.properties</code> using the
	 * <code>locales</code> and <code>locales.enabled</code> keys.
	 *
	 * @param  locale the locale to search for
	 * @return <code>true</code> if the locale is configured to be available;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean isAvailableLocale(Locale locale) {
		if (locale == null) {
			return false;
		}

		return isAvailableLocale(LocaleUtil.toLanguageId(locale));
	}

	/**
	 * Returns <code>true</code> if the locale is configured to be available in
	 * the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  locale the locale to search for
	 * @return <code>true</code> if the locale is configured to be available in
	 *         the group; <code>false</code> otherwise
	 */
	@Override
	public boolean isAvailableLocale(long groupId, Locale locale) {
		if (locale == null) {
			return false;
		}

		return isAvailableLocale(groupId, LocaleUtil.toLanguageId(locale));
	}

	/**
	 * Returns <code>true</code> if the language ID is configured to be
	 * available in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  languageId the language ID to search for
	 * @return <code>true</code> if the language ID is configured to be
	 *         available in the group; <code>false</code> otherwise
	 */
	@Override
	public boolean isAvailableLocale(long groupId, String languageId) {
		if (groupId <= 0) {
			return isAvailableLocale(languageId);
		}

		try {
			if (isInheritLocales(groupId)) {
				return isAvailableLocale(languageId);
			}
		}
		catch (Exception e) {
		}

		Map<String, Locale> groupLanguageIdLocalesMap =
			_getGroupLanguageIdLocalesMap(groupId);

		return groupLanguageIdLocalesMap.containsKey(languageId);
	}

	/**
	 * Returns <code>true</code> if the language ID is configured to be
	 * available.
	 *
	 * @param  languageId the language ID to search for
	 * @return <code>true</code> if the language ID is configured to be
	 *         available; <code>false</code> otherwise
	 */
	@Override
	public boolean isAvailableLocale(String languageId) {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag.containsLanguageId(languageId);
	}

	/**
	 * Returns <code>true</code> if the locale is configured to be a beta
	 * language.
	 *
	 * @param  locale the locale to search for
	 * @return <code>true</code> if the locale is configured to be a beta
	 *         language; <code>false</code> otherwise
	 */
	@Override
	public boolean isBetaLocale(Locale locale) {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag.isBetaLocale(locale);
	}

	@Override
	public boolean isDuplicateLanguageCode(String languageCode) {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag.isDuplicateLanguageCode(languageCode);
	}

	@Override
	public boolean isInheritLocales(long groupId) throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (!group.isSite() || group.isCompany()) {
			return true;
		}

		return GetterUtil.getBoolean(
			group.getTypeSettingsProperty(
				GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES),
			true);
	}

	@Override
	public String process(
		ResourceBundle resourceBundle, Locale locale, String content) {

		StringBundler sb = new StringBundler();

		Matcher matcher = _pattern.matcher(content);

		int x = 0;

		while (matcher.find()) {
			int y = matcher.start(0);

			String key = matcher.group(1);

			sb.append(content.substring(x, y));
			sb.append(StringPool.APOSTROPHE);

			String value = get(resourceBundle, key);

			sb.append(HtmlUtil.escapeJS(value));

			sb.append(StringPool.APOSTROPHE);

			x = matcher.end(0);
		}

		sb.append(content.substring(x));

		return sb.toString();
	}

	@Override
	public void resetAvailableGroupLocales(long groupId) {
		_resetAvailableGroupLocales(groupId);
	}

	@Override
	public void resetAvailableLocales(long companyId) {
		_resetAvailableLocales(companyId);
	}

	@Override
	public void updateCookie(
		HttpServletRequest request, HttpServletResponse response,
		Locale locale) {

		String languageId = LocaleUtil.toLanguageId(locale);

		Cookie languageIdCookie = new Cookie(
			CookieKeys.GUEST_LANGUAGE_ID, languageId);

		String domain = CookieKeys.getDomain(request);

		if (Validator.isNotNull(domain)) {
			languageIdCookie.setDomain(domain);
		}

		languageIdCookie.setMaxAge(CookieKeys.MAX_AGE);
		languageIdCookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(request, response, languageIdCookie);
	}

	protected MessageFormat decorateMessageFormat(
		HttpServletRequest request, String pattern,
		Object[] formattedArguments) {

		Locale locale = _getLocale(request);

		return decorateMessageFormat(locale, pattern, formattedArguments);
	}

	protected MessageFormat decorateMessageFormat(
		Locale locale, String pattern, Object[] formattedArguments) {

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		MessageFormat messageFormat = new MessageFormat(pattern, locale);

		for (int i = 0; i < formattedArguments.length; i++) {
			Object formattedArgument = formattedArguments[i];

			if (formattedArgument instanceof Number) {
				messageFormat.setFormat(i, NumberFormat.getInstance(locale));
			}
		}

		return messageFormat;
	}

	private static CompanyLocalesBag _getCompanyLocalesBag() {
		Long companyId = CompanyThreadLocal.getCompanyId();

		CompanyLocalesBag companyLocalesBag = _companyLocalesBags.get(
			companyId);

		if (companyLocalesBag == null) {
			companyLocalesBag = new CompanyLocalesBag(companyId);

			_companyLocalesBags.put(companyId, companyLocalesBag);
		}

		return companyLocalesBag;
	}

	private ObjectValuePair<HashMap<String, Locale>, HashMap<String, Locale>>
		_createGroupLocales(long groupId) {

		String[] languageIds = PropsValues.LOCALES_ENABLED;

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			UnicodeProperties typeSettingsProperties =
				group.getTypeSettingsProperties();

			languageIds = StringUtil.split(
				typeSettingsProperties.getProperty(PropsKeys.LOCALES));
		}
		catch (Exception e) {
		}

		HashMap<String, Locale> groupLanguageCodeLocalesMap = new HashMap<>();
		HashMap<String, Locale> groupLanguageIdLocalesMap = new HashMap<>();

		for (String languageId : languageIds) {
			Locale locale = LocaleUtil.fromLanguageId(languageId, false);

			String languageCode = languageId;

			int pos = languageId.indexOf(CharPool.UNDERLINE);

			if (pos > 0) {
				languageCode = languageId.substring(0, pos);
			}

			if (!groupLanguageCodeLocalesMap.containsKey(languageCode)) {
				groupLanguageCodeLocalesMap.put(languageCode, locale);
			}

			groupLanguageIdLocalesMap.put(languageId, locale);
		}

		_groupLanguageCodeLocalesMapMap.put(
			groupId, groupLanguageCodeLocalesMap);
		_groupLanguageIdLocalesMap.put(groupId, groupLanguageIdLocalesMap);

		return new ObjectValuePair<>(
			groupLanguageCodeLocalesMap, groupLanguageIdLocalesMap);
	}

	private String _escapePattern(String pattern) {
		return StringUtil.replace(
			pattern, CharPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);
	}

	private String _get(ResourceBundle resourceBundle, String key) {
		if (PropsValues.TRANSLATIONS_DISABLED) {
			return key;
		}

		if ((resourceBundle == null) || (key == null)) {
			return null;
		}

		String value = ResourceBundleUtil.getString(resourceBundle, key);

		if (value != null) {
			return LanguageResources.fixValue(value);
		}

		if ((key.length() > 0) &&
			(key.charAt(key.length() - 1) == CharPool.CLOSE_BRACKET)) {

			int pos = key.lastIndexOf(CharPool.OPEN_BRACKET);

			if (pos != -1) {
				key = key.substring(0, pos);

				return _get(resourceBundle, key);
			}
		}

		return null;
	}

	private Map<String, Locale> _getGroupLanguageCodeLocalesMap(long groupId) {
		Map<String, Locale> groupLanguageCodeLocalesMap =
			_groupLanguageCodeLocalesMapMap.get(groupId);

		if (groupLanguageCodeLocalesMap == null) {
			ObjectValuePair<HashMap<String, Locale>, HashMap<String, Locale>>
				objectValuePair = _createGroupLocales(groupId);

			groupLanguageCodeLocalesMap = objectValuePair.getKey();
		}

		return groupLanguageCodeLocalesMap;
	}

	private Map<String, Locale> _getGroupLanguageIdLocalesMap(long groupId) {
		Map<String, Locale> groupLanguageIdLocalesMap =
			_groupLanguageIdLocalesMap.get(groupId);

		if (groupLanguageIdLocalesMap == null) {
			ObjectValuePair<HashMap<String, Locale>, HashMap<String, Locale>>
				objectValuePair = _createGroupLocales(groupId);

			groupLanguageIdLocalesMap = objectValuePair.getValue();
		}

		return groupLanguageIdLocalesMap;
	}

	private Locale _getLocale(HttpServletRequest request) {
		Locale locale = null;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			locale = themeDisplay.getLocale();
		}
		else {
			locale = request.getLocale();

			if (!isAvailableLocale(locale)) {
				locale = LocaleUtil.getDefault();
			}
		}

		return locale;
	}

	private void _resetAvailableGroupLocales(long groupId) {
		_groupLocalesPortalCache.remove(groupId);
	}

	private void _resetAvailableLocales(long companyId) {
		_companyLocalesPortalCache.remove(companyId);
	}

	private static final String _COMPANY_LOCALES_PORTAL_CACHE_NAME =
		LanguageImpl.class + "._companyLocalesPortalCache";

	private static final String _GROUP_LOCALES_PORTAL_CACHE_NAME =
		LanguageImpl.class + "._groupLocalesPortalCache";

	private static final Log _log = LogFactoryUtil.getLog(LanguageImpl.class);

	private static final Map<Long, CompanyLocalesBag> _companyLocalesBags =
		new ConcurrentHashMap<>();
	private static PortalCache<Long, Serializable> _companyLocalesPortalCache;
	private static PortalCache<Long, Serializable> _groupLocalesPortalCache;
	private static final Pattern _pattern = Pattern.compile(
		"Liferay\\.Language\\.get\\([\"']([^)]+)[\"']\\)");

	private static final Synchronizer<Long, Serializable> _removeSynchronizer =
		new Synchronizer<Long, Serializable>() {

			@Override
			public void onSynchronize(
				Map<? extends Long, ? extends Serializable> map, Long key,
				Serializable value, int timeToLive) {

				map.remove(key);
			}

		};

	private final Map<Long, HashMap<String, Locale>>
		_groupLanguageCodeLocalesMapMap = new ConcurrentHashMap<>();
	private final Map<Long, HashMap<String, Locale>>
		_groupLanguageIdLocalesMap = new ConcurrentHashMap<>();

	private static class CompanyLocalesBag implements Serializable {

		public boolean containsLanguageCode(String languageCode) {
			return _languageCodeLocalesMap.containsKey(languageCode);
		}

		public boolean containsLanguageId(String languageId) {
			return _languageIdLocalesMap.containsKey(languageId);
		}

		public Set<Locale> getAvailableLocales() {
			return _availableLocales;
		}

		public Locale getByLanguageCode(String languageCode) {
			return _languageCodeLocalesMap.get(languageCode);
		}

		public boolean isBetaLocale(Locale locale) {
			return _localesBetaSet.contains(locale);
		}

		public boolean isDuplicateLanguageCode(String languageCode) {
			return _duplicateLanguageCodes.contains(languageCode);
		}

		private CompanyLocalesBag(long companyId) {
			String[] languageIds = PropsValues.LOCALES;

			if (companyId != CompanyConstants.SYSTEM) {
				try {
					languageIds = PrefsPropsUtil.getStringArray(
						companyId, PropsKeys.LOCALES, StringPool.COMMA,
						PropsValues.LOCALES_ENABLED);
				}
				catch (SystemException se) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(se, se);
					}

					languageIds = PropsValues.LOCALES_ENABLED;
				}
			}

			Set<String> duplicateLanguageCodes = new HashSet<>();

			for (String languageId : languageIds) {
				Locale locale = LocaleUtil.fromLanguageId(languageId, false);

				String languageCode = languageId;

				int pos = languageId.indexOf(CharPool.UNDERLINE);

				if (pos > 0) {
					languageCode = languageId.substring(0, pos);
				}

				if (_languageCodeLocalesMap.containsKey(languageCode)) {
					duplicateLanguageCodes.add(languageCode);
				}
				else {
					_languageCodeLocalesMap.put(languageCode, locale);
				}

				_languageIdLocalesMap.put(languageId, locale);
			}

			if (duplicateLanguageCodes.isEmpty()) {
				_duplicateLanguageCodes = Collections.emptySet();
			}
			else {
				_duplicateLanguageCodes = duplicateLanguageCodes;
			}

			for (String languageId : PropsValues.LOCALES_BETA) {
				_localesBetaSet.add(
					LocaleUtil.fromLanguageId(languageId, false));
			}

			_availableLocales = Collections.unmodifiableSet(
				new HashSet<>(_languageIdLocalesMap.values()));

			Set<Locale> supportedLocalesSet = new HashSet<>(
				_languageIdLocalesMap.values());

			supportedLocalesSet.removeAll(_localesBetaSet);

			_supportedLocalesSet = Collections.unmodifiableSet(
				supportedLocalesSet);
		}

		private final Set<Locale> _availableLocales;
		private final Set<String> _duplicateLanguageCodes;
		private final Map<String, Locale> _languageCodeLocalesMap =
			new HashMap<>();
		private final Map<String, Locale> _languageIdLocalesMap =
			new HashMap<>();
		private final Set<Locale> _localesBetaSet = new HashSet<>();
		private final Set<Locale> _supportedLocalesSet;

	}

}