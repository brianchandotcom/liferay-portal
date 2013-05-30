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

package com.liferay.portal.servlet.filters.i18n;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.User;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class I18nFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		I18nFilter.class.getName() + "SKIP_FILTER";

	public static Set<String> getLanguageIds() {
		return _languageIds;
	}

	public static void setLanguageIds(Set<String> languageIds) {
		_languageIds = new HashSet<String>();

		for (String languageId : languageIds) {
			languageId = languageId.substring(1);

			_languageIds.add(languageId);
		}

		_languageIds = Collections.unmodifiableSet(_languageIds);
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (!isAlreadyFiltered(request) && !isForwardedByI18nServlet(request)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected String getRedirect(HttpServletRequest request) throws Exception {
		if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) {
			return null;
		}

		String method = request.getMethod();

		if (method.equals(HttpMethods.POST)) {
			return null;
		}

		String contextPath = PortalUtil.getPathContext();

		String requestURI = request.getRequestURI();

		if (Validator.isNotNull(contextPath) &&
			requestURI.contains(contextPath)) {

			requestURI = requestURI.substring(contextPath.length());
		}

		requestURI = StringUtil.replace(
			requestURI, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		String i18nLanguageId = requestURI.substring(1);

		int pos = requestURI.indexOf(CharPool.SLASH, 1);

		if (pos != -1) {
			i18nLanguageId = i18nLanguageId.substring(0, pos - 1);
		}

		if (_languageIds.contains(i18nLanguageId)) {
			return null;
		}

		String guestLanguageId = null;

		User user = (User)request.getAttribute(WebKeys.USER);

		if (user != null) {
			guestLanguageId = user.getLanguageId();
		}

		if (Validator.isNull(guestLanguageId)) {
			guestLanguageId = CookieKeys.getCookie(
				request, CookieKeys.GUEST_LANGUAGE_ID, false);
		}

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if (Validator.isNull(guestLanguageId)) {
			guestLanguageId = defaultLanguageId;
		}

		if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 1) {
			if (!defaultLanguageId.equals(guestLanguageId)) {
				i18nLanguageId = guestLanguageId;
			}
			else {
				return null;
			}
		}
		else if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2) {
			i18nLanguageId = LocaleUtil.toLanguageId(
				PortalUtil.getLocale(request));
		}

		if (i18nLanguageId == null) {
			return null;
		}

		Locale locale = LocaleUtil.fromLanguageId(i18nLanguageId);

		if (!LanguageUtil.isAvailableLocale(locale)) {
			return null;
		}

		String i18nPathLanguageId = PortalUtil.getI18nPathLanguageId(
			locale, i18nLanguageId);

		String i18nPath = StringPool.SLASH.concat(i18nPathLanguageId);

		if (requestURI.contains(i18nPath.concat(StringPool.SLASH))) {
			return null;
		}

		String redirect = contextPath + i18nPath + requestURI;

		LayoutSet layoutSet = (LayoutSet)request.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if ((layoutSet != null) &&
			(requestURI.startsWith(_PRIVATE_GROUP_SERVLET_MAPPING) ||
			 requestURI.startsWith(_PRIVATE_USER_SERVLET_MAPPING) ||
			 requestURI.startsWith(_PUBLIC_GROUP_SERVLET_MAPPING))) {

			int x = requestURI.indexOf(StringPool.SLASH, 1);

			if (x == -1) {

				// /web

				requestURI += StringPool.SLASH;

				x = requestURI.indexOf(CharPool.SLASH, 1);
			}

			int y = requestURI.indexOf(CharPool.SLASH, x + 1);

			if (y == -1) {

				// /web/alpha

				requestURI += StringPool.SLASH;

				y = requestURI.indexOf(CharPool.SLASH, x + 1);
			}

			String groupFriendlyURL = requestURI.substring(x, y);

			Group group = layoutSet.getGroup();

			if (group.getFriendlyURL().equals(groupFriendlyURL)) {
				redirect = contextPath + i18nPath + requestURI.substring(y);
			}
		}

		String queryString = request.getQueryString();

		if (Validator.isNotNull(queryString)) {
			redirect += StringPool.QUESTION + request.getQueryString();
		}

		return redirect;
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isForwardedByI18nServlet(HttpServletRequest request) {
		if ((request.getAttribute(WebKeys.I18N_LANGUAGE_ID) != null) ||
			(request.getAttribute(WebKeys.I18N_PATH) != null)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);

		String redirect = getRedirect(request);

		if (redirect == null) {
			processFilter(I18nFilter.class, request, response, filterChain);

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		response.sendRedirect(redirect);
	}

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static Log _log = LogFactoryUtil.getLog(I18nFilter.class);

	private static Set<String> _languageIds;

}