/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ac.AccessControlThreadLocal;
import com.liferay.portal.servlet.JSONServlet;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import org.apache.struts.Globals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceServlet extends JSONServlet {

	@Override
	public void destroy() {
		_jsonWebServiceServiceAction.destroy();

		super.destroy();
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (PortalUtil.isMultipartRequest(request)) {
			UploadServletRequest uploadServletRequest =
				new UploadServletRequestImpl(request);

			request = uploadServletRequest;
		}

		String path = GetterUtil.getString(request.getPathInfo());

		if (!path.equals(StringPool.SLASH) && !path.equals(StringPool.BLANK)) {
			try {
				_servicePreAction(request, response);
			}
			catch (Exception e) {
				throw new ServletException(e);
			}

			super.service(request, response);

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Servlet context " + request.getContextPath());
		}

		String apiPath = PortalUtil.getPathMain() + "/portal/api/jsonws";

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		try {
			AccessControlThreadLocal.setRemoteAccess(true);

			String contextPath = PropsValues.PORTAL_CTX;

			if (servletContext.getContext(contextPath) != null) {
				if (!contextPath.equals(StringPool.SLASH) &&
					apiPath.startsWith(contextPath)) {

					apiPath = apiPath.substring(contextPath.length());
				}

				RequestDispatcher requestDispatcher =
					request.getRequestDispatcher(apiPath);

				requestDispatcher.forward(request, response);
			}
			else {
				String requestURI = request.getRequestURI();
				String requestURL = String.valueOf(request.getRequestURL());

				String serverURL = requestURL.substring(
					0, requestURL.length() - requestURI.length());

				String queryString = request.getQueryString();

				if (Validator.isNull(queryString)) {
					queryString = StringPool.BLANK;
				}
				else {
					queryString += StringPool.AMPERSAND;
				}

				String servletContextPath = ContextPathUtil.getContextPath(
					servletContext);

				queryString +=
					"contextPath=" + HttpUtil.encodeURL(servletContextPath);

				apiPath =
					serverURL + apiPath + StringPool.QUESTION + queryString;

				URL url = new URL(apiPath);

				InputStream inputStream = null;

				try {
					inputStream = url.openStream();

					OutputStream outputStream = response.getOutputStream();

					StreamUtil.transfer(inputStream, outputStream);
				}
				finally {
					StreamUtil.cleanUp(inputStream);

					AccessControlThreadLocal.setRemoteAccess(remoteAccess);
				}
			}
		}
		finally {
			AccessControlThreadLocal.setRemoteAccess(remoteAccess);
		}
	}

	@Override
	protected JSONAction getJSONAction(ServletContext servletContext) {
		ClassLoader classLoader = (ClassLoader)servletContext.getAttribute(
			PluginContextListener.PLUGIN_CLASS_LOADER);

		_jsonWebServiceServiceAction = new JSONWebServiceServiceAction(
			servletContext, classLoader);

		_jsonWebServiceServiceAction.setServletContext(servletContext);

		return _jsonWebServiceServiceAction;
	}

	private void _servicePreAction(
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		// Company

		Company company = PortalUtil.getCompany(request);

		// User

		User user = null;

		try {
			user = PortalUtil.getUser(request);
		}
		catch (NoSuchUserException nsue) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsue.getMessage());
			}

			long userId = PortalUtil.getUserId(request);

			if (userId > 0) {
				session.invalidate();
			}

			return;
		}

		boolean signedIn = false;

		if (user == null) {
			user = company.getDefaultUser();
		}
		else if (!user.isDefaultUser()) {
			signedIn = true;
		}

		String doAsUserLanguageId = ParamUtil.getString(
			request, "doAsUserLanguageId");

		// Locale

		Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);

		if (Validator.isNotNull(doAsUserLanguageId)) {
			locale = LocaleUtil.fromLanguageId(doAsUserLanguageId);
		}

		String i18nLanguageId = (String)request.getAttribute(
			WebKeys.I18N_LANGUAGE_ID);

		if (Validator.isNotNull(i18nLanguageId)) {
			locale = LocaleUtil.fromLanguageId(i18nLanguageId);
		}
		else if (locale == null) {
			if (signedIn) {
				locale = user.getLocale();
			}
			else {

				// User previously set their preferred language

				String languageId = CookieKeys.getCookie(
					request, CookieKeys.GUEST_LANGUAGE_ID, false);

				if (Validator.isNotNull(languageId)) {
					locale = LocaleUtil.fromLanguageId(languageId);
				}

				// Get locale from the request

				if ((locale == null) && PropsValues.LOCALE_DEFAULT_REQUEST) {
					Enumeration<Locale> locales = request.getLocales();

					while (locales.hasMoreElements()) {
						Locale requestLocale = locales.nextElement();

						if (Validator.isNull(requestLocale.getCountry())) {

							// Locales must contain a country code

							requestLocale = LanguageUtil.getLocale(
								requestLocale.getLanguage());
						}

						if (LanguageUtil.isAvailableLocale(requestLocale)) {
							locale = requestLocale;

							break;
						}
					}
				}

				// Get locale from the default user

				if (locale == null) {
					locale = user.getLocale();
				}

				if (Validator.isNull(locale.getCountry())) {

					// Locales must contain a country code

					locale = LanguageUtil.getLocale(locale.getLanguage());
				}

				if (!LanguageUtil.isAvailableLocale(locale)) {
					locale = user.getLocale();
				}
			}

			session.setAttribute(Globals.LOCALE_KEY, locale);

			LanguageUtil.updateCookie(request, response, locale);
		}

		LocaleThreadLocal.setThemeDisplayLocale(locale);
	}


	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceServlet.class);

	private JSONWebServiceServiceAction _jsonWebServiceServiceAction;

}