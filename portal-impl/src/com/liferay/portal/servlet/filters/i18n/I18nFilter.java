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

package com.liferay.portal.servlet.filters.i18n;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

/**
 * @author Brian Wing Shun Chan
 */
public class I18nFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		I18nFilter.class.getName() + "#SKIP_FILTER";

	public static Set<String> getLanguageIds() {
		return _languageIds;
	}

	public static void setLanguageIds(Set<String> languageIds) {
		_languageIds = new HashSet<>();

		for (String languageId : languageIds) {
			languageId = languageId.substring(1);

			_languageIds.add(languageId);
		}

		_languageIds = Collections.unmodifiableSet(_languageIds);
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (!isAlreadyFiltered(request) && !isForwardedByI18nServlet(request) &&
			!isWidget(request)) {

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

		String i18nLanguageId = prependI18nLanguageId(
			request, PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE);

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
			requestURI.startsWith(
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING)) {

			int[] groupFriendlyURLIndex = PortalUtil.getGroupFriendlyURLIndex(
				requestURI);

			if (groupFriendlyURLIndex != null) {
				int x = groupFriendlyURLIndex[0];
				int y = groupFriendlyURLIndex[1];

				String groupFriendlyURL = requestURI.substring(x, y);

				Group group = layoutSet.getGroup();

				if (groupFriendlyURL.equals(group.getFriendlyURL())) {
					redirect = contextPath + i18nPath + requestURI.substring(y);
				}
			}
		}

		String queryString = request.getQueryString();

		if (queryString == null) {
			queryString = (String)request.getAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING);
		}

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

	protected boolean isWidget(HttpServletRequest request) {
		if (request.getAttribute(WebKeys.WIDGET) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected String prependI18nLanguageId(
		HttpServletRequest request, int prependFriendlyUrlStyle) {

		String userLanguageId = null;

		User user = (User)request.getAttribute(WebKeys.USER);

		if (user != null) {
			userLanguageId = user.getLanguageId();
		}

		String guestLanguageId = userLanguageId;

		if (Validator.isNull(guestLanguageId)) {
			guestLanguageId = CookieKeys.getCookie(
				request, CookieKeys.GUEST_LANGUAGE_ID, false);
		}

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if (Validator.isNull(guestLanguageId)) {
			guestLanguageId = defaultLanguageId;
		}

		if (prependFriendlyUrlStyle == 1) {
			return prependIfRequestedLocaleDiffersFromDefaultLocale(
				defaultLanguageId, guestLanguageId);
		}
		else if (prependFriendlyUrlStyle == 2) {
			return LocaleUtil.toLanguageId(PortalUtil.getLocale(request));
		}
		else if (prependFriendlyUrlStyle == 3) {
			if (user != null) {
				HttpSession session = request.getSession();

				Locale locale = (Locale)session.getAttribute(
					Globals.LOCALE_KEY);

				if (userLanguageId.equals(LocaleUtil.toLanguageId(locale))) {
					return null;
				}

				return LocaleUtil.toLanguageId(locale);
			}
			else {
				return prependIfRequestedLocaleDiffersFromDefaultLocale(
					defaultLanguageId, guestLanguageId);
			}
		}

		return null;
	}

	protected String prependIfRequestedLocaleDiffersFromDefaultLocale(
		String defaultLanguageId, String guestLanguageId) {

		if (defaultLanguageId.equals(guestLanguageId)) {
			return null;
		}

		return guestLanguageId;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);

		String redirect = getRedirect(request);

		if (redirect == null) {
			processFilter(
				I18nFilter.class.getName(), request, response, filterChain);

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		response.sendRedirect(redirect);
	}

	private static final Log _log = LogFactoryUtil.getLog(I18nFilter.class);

	private static Set<String> _languageIds;

}