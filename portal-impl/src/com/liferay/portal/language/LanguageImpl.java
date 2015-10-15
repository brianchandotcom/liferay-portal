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

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheMapSynchronizeUtil;
import com.liferay.portal.kernel.cache.PortalCacheMapSynchronizeUtil.Synchronizer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageWrapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.dependency.ServiceDependencyListener;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.io.Serializable;

import java.text.MessageFormat;

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
 * @author Brian Wing Shun Chan
 * @author Andrius Vitkauskas
 * @author Eduardo Lundgren
 */
@DoPrivileged
public class LanguageImpl implements Language, Serializable {

	public void afterPropertiesSet() {
		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					Registry registry = RegistryUtil.getRegistry();

					MultiVMPool multiVMPool = registry.getService(
						MultiVMPool.class);

					_portalCache =
						(PortalCache<Long, Serializable>)
							multiVMPool.getPortalCache(
								LanguageImpl.class.getName());

					PortalCacheMapSynchronizeUtil.synchronize(
						_portalCache, _companyLocalesBags,
						new Synchronizer<Long, Serializable>() {

							@Override
							public void onSynchronize(
								Map<? extends Long, ? extends Serializable> map,
								Long key, Serializable value, int timeToLive) {

								_companyLocalesBags.remove(key);
							}

						});
				}

				@Override
				public void destroy() {
				}

			}
		);

		serviceDependencyManager.registerDependencies(MultiVMPool.class);
	}

	@Override
	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument) {

		return format(request, pattern, new LanguageWrapper[] {argument}, true);
	}

	@Override
	public String format(
		HttpServletRequest request, String pattern, LanguageWrapper argument,
		boolean translateArguments) {

		return format(
			request, pattern, new LanguageWrapper[] {argument},
			translateArguments);
	}

	@Override
	public String format(
		HttpServletRequest request, String pattern,
		LanguageWrapper[] arguments) {

		return format(request, pattern, arguments, true);
	}

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
							arguments[i].getBefore() +
							arguments[i].getText() +
							arguments[i].getAfter();
					}
				}

				value = MessageFormat.format(pattern, formattedArguments);
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

	@Override
	public String format(
		HttpServletRequest request, String pattern, Object argument) {

		return format(request, pattern, new Object[] {argument}, true);
	}

	@Override
	public String format(
		HttpServletRequest request, String pattern, Object argument,
		boolean translateArguments) {

		return format(
			request, pattern, new Object[] {argument}, translateArguments);
	}

	@Override
	public String format(
		HttpServletRequest request, String pattern, Object[] arguments) {

		return format(request, pattern, arguments, true);
	}

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

				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						formattedArguments[i] = get(
							request, arguments[i].toString());
					}
					else {
						formattedArguments[i] = arguments[i];
					}
				}

				value = MessageFormat.format(pattern, formattedArguments);
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

	@Override
	public String format(
		Locale locale, String pattern, List<Object> arguments) {

		return format(locale, pattern, arguments.toArray(), true);
	}

	@Override
	public String format(Locale locale, String pattern, Object argument) {
		return format(locale, pattern, new Object[] {argument}, true);
	}

	@Override
	public String format(
		Locale locale, String pattern, Object argument,
		boolean translateArguments) {

		return format(
			locale, pattern, new Object[] {argument}, translateArguments);
	}

	@Override
	public String format(Locale locale, String pattern, Object[] arguments) {
		return format(locale, pattern, arguments, true);
	}

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

				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						formattedArguments[i] = get(
							locale, arguments[i].toString());
					}
					else {
						formattedArguments[i] = arguments[i];
					}
				}

				value = MessageFormat.format(pattern, formattedArguments);
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

	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument) {

		return format(resourceBundle, pattern, new Object[] {argument}, true);
	}

	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object argument,
		boolean translateArguments) {

		return format(
			resourceBundle, pattern, new Object[] {argument},
			translateArguments);
	}

	@Override
	public String format(
		ResourceBundle resourceBundle, String pattern, Object[] arguments) {

		return format(resourceBundle, pattern, arguments, true);
	}

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

				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						formattedArguments[i] = get(
							resourceBundle, arguments[i].toString());
					}
					else {
						formattedArguments[i] = arguments[i];
					}
				}

				value = MessageFormat.format(pattern, formattedArguments);
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

	@Override
	public String get(Locale locale, String key) {
		return get(locale, key, key);
	}

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

	@Override
	public String get(ResourceBundle resourceBundle, String key) {
		return get(resourceBundle, key, key);
	}

	@Override
	public String get(
		ResourceBundle resourceBundle, String key, String defaultValue) {

		String value = _get(resourceBundle, key);

		if (value != null) {
			return value;
		}

		return defaultValue;
	}

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

	@Override
	public String getLanguageId(Locale locale) {
		return LocaleUtil.toLanguageId(locale);
	}

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

	@Override
	public Locale getLocale(String languageCode) {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag.getByLanguageCode(languageCode);
	}

	@Override
	public Set<Locale> getSupportedLocales() {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag._supportedLocalesSet;
	}

	@Override
	public String getTimeDescription(
		HttpServletRequest request, long milliseconds) {

		return getTimeDescription(request, milliseconds, false);
	}

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

	@Override
	public String getTimeDescription(
		HttpServletRequest request, Long milliseconds) {

		return getTimeDescription(request, milliseconds.longValue());
	}

	@Override
	public String getTimeDescription(Locale locale, long milliseconds) {
		return getTimeDescription(locale, milliseconds, false);
	}

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

	@Override
	public String getTimeDescription(Locale locale, Long milliseconds) {
		return getTimeDescription(locale, milliseconds.longValue());
	}

	@Override
	public void init() {
		_companyLocalesBags.clear();
	}

	@Override
	public boolean isAvailableLanguageCode(String languageCode) {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag.containsLanguageCode(languageCode);
	}

	@Override
	public boolean isAvailableLocale(Locale locale) {
		if (locale == null) {
			return false;
		}

		return isAvailableLocale(LocaleUtil.toLanguageId(locale));
	}

	@Override
	public boolean isAvailableLocale(long groupId, Locale locale) {
		if (locale == null) {
			return false;
		}

		return isAvailableLocale(groupId, LocaleUtil.toLanguageId(locale));
	}

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

	@Override
	public boolean isAvailableLocale(String languageId) {
		CompanyLocalesBag companyLocalesBag = _getCompanyLocalesBag();

		return companyLocalesBag.containsLanguageId(languageId);
	}

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

		languageIdCookie.setPath(StringPool.SLASH);
		languageIdCookie.setMaxAge(CookieKeys.MAX_AGE);

		CookieKeys.addCookie(request, response, languageIdCookie);
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

	private ObjectValuePair<Map<String, Locale>, Map<String, Locale>>
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

		Map<String, Locale> groupLanguageCodeLocalesMap = new HashMap<>();
		Map<String, Locale> groupLanguageIdLocalesMap = new HashMap<>();

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
			pattern, StringPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);
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
			ObjectValuePair<Map<String, Locale>, Map<String, Locale>>
				objectValuePair = _createGroupLocales(groupId);

			groupLanguageCodeLocalesMap = objectValuePair.getKey();
		}

		return groupLanguageCodeLocalesMap;
	}

	private Map<String, Locale> _getGroupLanguageIdLocalesMap(long groupId) {
		Map<String, Locale> groupLanguageIdLocalesMap =
			_groupLanguageIdLocalesMap.get(groupId);

		if (groupLanguageIdLocalesMap == null) {
			ObjectValuePair<Map<String, Locale>, Map<String, Locale>>
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
		_groupLanguageCodeLocalesMapMap.remove(groupId);
		_groupLanguageIdLocalesMap.remove(groupId);
	}

	private void _resetAvailableLocales(long companyId) {
		_portalCache.remove(companyId);
	}

	private static final Log _log = LogFactoryUtil.getLog(LanguageImpl.class);

	private static final Map<Long, CompanyLocalesBag> _companyLocalesBags =
		new ConcurrentHashMap<>();
	private static final Pattern _pattern = Pattern.compile(
		"Liferay\\.Language\\.get\\([\"']([^)]+)[\"']\\)");
	private static PortalCache<Long, Serializable> _portalCache;

	private final Map<Long, Map<String, Locale>>
		_groupLanguageCodeLocalesMapMap = new ConcurrentHashMap<>();
	private final Map<Long, Map<String, Locale>> _groupLanguageIdLocalesMap =
		new ConcurrentHashMap<>();

	private static class CompanyLocalesBag implements Serializable {

		public boolean containsLanguageCode(String languageCode) {
			return _languageCodeLocalesMap.containsKey(languageCode);
		}

		public boolean containsLanguageId(String languageId) {
			return _languageIdLocalesMap.containsKey(languageId);
		}

		public Set<Locale> getAvailableLocales() {
			return new HashSet<>(_languageIdLocalesMap.values());
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
					languageIds = PropsValues.LOCALES_ENABLED;
				}
			}

			for (String languageId : languageIds) {
				Locale locale = LocaleUtil.fromLanguageId(languageId, false);

				String languageCode = languageId;

				int pos = languageId.indexOf(CharPool.UNDERLINE);

				if (pos > 0) {
					languageCode = languageId.substring(0, pos);
				}

				if (_languageCodeLocalesMap.containsKey(languageCode)) {
					_duplicateLanguageCodes.add(languageCode);
				}
				else {
					_languageCodeLocalesMap.put(languageCode, locale);
				}

				_languageIdLocalesMap.put(languageId, locale);
			}

			for (String languageId : PropsValues.LOCALES_BETA) {
				_localesBetaSet.add(
					LocaleUtil.fromLanguageId(languageId, false));
			}

			_supportedLocalesSet = new HashSet<>(
				_languageIdLocalesMap.values());

			_supportedLocalesSet.removeAll(_localesBetaSet);
		}

		private final Set<String> _duplicateLanguageCodes = new HashSet<>();
		private final Map<String, Locale> _languageCodeLocalesMap =
			new HashMap<>();
		private final Map<String, Locale> _languageIdLocalesMap =
			new HashMap<>();
		private final Set<Locale> _localesBetaSet = new HashSet<>();
		private final Set<Locale> _supportedLocalesSet;

	}

}