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

package com.liferay.portal.servlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

/**
 * @author Brian Wing Shun Chan
 */
public class I18nServlet extends HttpServlet {

	public static Set<String> getLanguageIds() {
		return new HashSet<>(_languageIds.values());
	}

	public static Map<String, String> getLanguageIdsMap() {
		return _languageIds;
	}

	public static void setLanguageIds(Element root) {
		Map<String, String> languageIds = new HashMap<>();

		List<Element> rootElements = root.elements("servlet-mapping");

		for (Element element : rootElements) {
			String servletName = element.elementText("servlet-name");

			if (servletName.equals("I18n Servlet")) {
				String urlPattern = element.elementText("url-pattern");

				String languageId = urlPattern.substring(
					0, urlPattern.lastIndexOf(CharPool.SLASH));

				languageIds.put(StringUtil.toLowerCase(languageId), languageId);
			}
		}

		_languageIds = Collections.unmodifiableMap(languageIds);
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			I18nData i18nData = getI18nData(request);

			if ((i18nData == null) ||
				!PortalUtil.isValidResourceId(i18nData.getPath())) {

				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), request, response);
			}
			else {
				request.setAttribute(
					WebKeys.I18N_LANGUAGE_CODE, i18nData.getLanguageCode());
				request.setAttribute(
					WebKeys.I18N_LANGUAGE_ID, i18nData.getLanguageId());
				request.setAttribute(WebKeys.I18N_PATH, i18nData.getI18nPath());

				Locale locale = LocaleUtil.fromLanguageId(
					i18nData.getLanguageId(), false, false);

				HttpSession session = request.getSession();

				session.setAttribute(Globals.LOCALE_KEY, locale);

				LanguageUtil.updateCookie(request, response, locale);

				ServletContext servletContext = getServletContext();

				RequestDispatcher requestDispatcher =
					servletContext.getRequestDispatcher(i18nData.getPath());

				requestDispatcher.forward(request, response);
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}

	protected I18nData getI18nData(HttpServletRequest request) {
		String path = GetterUtil.getString(request.getPathInfo());

		if (Validator.isNull(path)) {
			path = "/";
		}

		String i18nLanguageId = request.getServletPath();

		int pos = i18nLanguageId.lastIndexOf(CharPool.SLASH);

		i18nLanguageId = i18nLanguageId.substring(pos + 1);

		if (_log.isDebugEnabled()) {
			_log.debug("Language ID " + i18nLanguageId);
		}

		if (Validator.isNull(i18nLanguageId)) {
			return null;
		}

		String i18nPath = StringPool.SLASH + i18nLanguageId;

		Locale locale = LocaleUtil.fromLanguageId(i18nLanguageId, true, false);

		String i18nLanguageCode = i18nLanguageId;

		Locale siteDefaultLocale = null;

		try {
			int[] friendlyURLIndices = PortalUtil.getGroupFriendlyURLIndex(
				path);

			String friendlyURL = path.substring(
				friendlyURLIndices[0], friendlyURLIndices[1]);

			Group siteGroup = GroupLocalServiceUtil.getFriendlyURLGroup(
				(Long)request.getAttribute(WebKeys.COMPANY_ID), friendlyURL);

			siteDefaultLocale = PortalUtil.getSiteDefaultLocale(siteGroup);

			if (!LanguageUtil.isSameLanguage(locale, siteDefaultLocale)) {
				siteDefaultLocale = LanguageUtil.getLocale(
					siteGroup.getGroupId(), locale.getLanguage());
			}
		}
		catch (Exception e) {
			siteDefaultLocale = LocaleUtil.getDefault();

			if (!LanguageUtil.isSameLanguage(locale, siteDefaultLocale)) {
				siteDefaultLocale = LanguageUtil.getLocale(
					locale.getLanguage());
			}
		}

		String siteDefaultLanguageId = LanguageUtil.getLanguageId(
			siteDefaultLocale);

		if (siteDefaultLanguageId.startsWith(i18nLanguageId)) {
			locale = siteDefaultLocale;

			i18nPath = StringPool.SLASH + locale.getLanguage();
		}

		if (locale != null) {
			i18nLanguageId = LocaleUtil.toLanguageId(locale);

			i18nLanguageCode = locale.getLanguage();
		}

		if (!PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE &&
			!LanguageUtil.isAvailableLocale(i18nLanguageId)) {

			return null;
		}

		String redirect = path;

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		return new I18nData(
			i18nPath, i18nLanguageCode, i18nLanguageId, redirect);
	}

	protected I18nData getI18nData(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		String i18nPath = StringPool.SLASH + languageId;

		Locale defaultLocale = LocaleUtil.getDefault();

		if (LocaleUtil.equals(defaultLocale, locale)) {
			i18nPath = StringPool.SLASH + defaultLocale.getLanguage();
		}
		else if (!LanguageUtil.isSameLanguage(defaultLocale, locale)) {
			defaultLocale = LanguageUtil.getLocale(locale.getLanguage());

			if (LocaleUtil.equals(defaultLocale, locale)) {
				i18nPath = StringPool.SLASH + defaultLocale.getLanguage();
			}
		}

		return new I18nData(
			i18nPath, locale.getLanguage(), languageId, StringPool.SLASH);
	}

	protected class I18nData {

		public I18nData(
			String i18nPath, String languageCode, String languageId,
			String path) {

			_i18nPath = i18nPath;
			_languageCode = languageCode;
			_languageId = languageId;
			_path = path;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof I18nData)) {
				return false;
			}

			I18nData i18nData = (I18nData)obj;

			if (Objects.equals(getI18nPath(), i18nData.getI18nPath()) &&
				Objects.equals(getLanguageCode(), i18nData.getLanguageCode()) &&
				Objects.equals(getLanguageId(), i18nData.getLanguageId()) &&
				Objects.equals(getPath(), i18nData.getPath())) {

				return true;
			}

			return false;
		}

		public String getI18nPath() {
			return _i18nPath;
		}

		public String getLanguageCode() {
			return _languageCode;
		}

		public String getLanguageId() {
			return _languageId;
		}

		public String getPath() {
			return _path;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, getI18nPath());

			hash = HashUtil.hash(hash, getLanguageCode());
			hash = HashUtil.hash(hash, getLanguageId());

			return HashUtil.hash(hash, getPath());
		}

		private final String _i18nPath;
		private final String _languageCode;
		private final String _languageId;
		private final String _path;

	}

	private static final Log _log = LogFactoryUtil.getLog(I18nServlet.class);

	private static Map<String, String> _languageIds;

}