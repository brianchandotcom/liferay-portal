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

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
		return _languageIds;
	}

	public static void setLanguageIds(Element root) {
		_languageIds = new HashSet<>();

		List<Element> rootElements = root.elements("servlet-mapping");

		for (Element element : rootElements) {
			String servletName = element.elementText("servlet-name");

			if (servletName.equals("I18n Servlet")) {
				String urlPattern = element.elementText("url-pattern");

				String languageId = urlPattern.substring(
					0, urlPattern.lastIndexOf(CharPool.SLASH));

				_languageIds.add(languageId);
			}
		}

		_languageIds = Collections.unmodifiableSet(_languageIds);
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			String[] i18nData = getI18nData(request);

			if ((i18nData == null) ||
				!PortalUtil.isValidResourceId(i18nData[2])) {

				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), request, response);
			}
			else {
				String i18nLanguageCode = i18nData[0];
				String i18nLanguageId = i18nData[1];
				String i18nPath = i18nData[2];
				String redirect = i18nData[3];

				request.setAttribute(
					WebKeys.I18N_LANGUAGE_CODE, i18nLanguageCode);
				request.setAttribute(WebKeys.I18N_LANGUAGE_ID, i18nLanguageId);
				request.setAttribute(WebKeys.I18N_PATH, i18nPath);

				Locale locale = LocaleUtil.fromLanguageId(i18nLanguageId);

				HttpSession session = request.getSession();

				session.setAttribute(Globals.LOCALE_KEY, locale);

				LanguageUtil.updateCookie(request, response, locale);

				ServletContext servletContext = getServletContext();

				RequestDispatcher requestDispatcher =
					servletContext.getRequestDispatcher(redirect);

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

	protected String[] getI18nData(HttpServletRequest request) {
		String path = GetterUtil.getString(request.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
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

		Locale locale = LocaleUtil.fromLanguageId(i18nLanguageId);

		String i18nLanguageCode = locale.getLanguage();

		if (Validator.isNull(locale.getCountry())) {

			// Locales must contain the country code

			locale = LanguageUtil.getLocale(i18nLanguageCode);

			i18nLanguageId = LocaleUtil.toLanguageId(locale);
		}

		String redirect = path;

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		String i18nPath = StringPool.SLASH + i18nLanguageId;

		return new String[] {
			i18nLanguageCode, i18nLanguageId, i18nPath, redirect
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(I18nServlet.class);

	private static Set<String> _languageIds;

}