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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (!isAlreadyFiltered(httpServletRequest) &&
			!isForwardedByI18nServlet(httpServletRequest) &&
			!isWidget(httpServletRequest)) {

			return true;
		}

		return false;
	}

	protected String getDefaultLanguageId(
		HttpServletRequest httpServletRequest) {

		String defaultLanguageId = getSiteDefaultLanguageId(httpServletRequest);

		if (Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getDefault());
		}

		return defaultLanguageId;
	}

	protected String getFriendlyURL(HttpServletRequest httpServletRequest) {
		String friendlyURL = StringPool.BLANK;

		String pathInfo = httpServletRequest.getPathInfo();

		if (Validator.isNotNull(pathInfo)) {
			String[] pathInfoElements = pathInfo.split("/");

			if ((pathInfoElements != null) && (pathInfoElements.length > 1)) {
				friendlyURL = StringPool.SLASH + pathInfoElements[1];
			}
		}

		return friendlyURL;
	}

	protected String getRedirect(HttpServletRequest httpServletRequest)
		throws Exception {

		if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) {
			return null;
		}

		String method = httpServletRequest.getMethod();

		if (method.equals(HttpMethods.POST)) {
			return null;
		}

		String contextPath = PortalUtil.getPathContext();

		String requestURI = httpServletRequest.getRequestURI();

		if (Validator.isNotNull(contextPath) &&
			requestURI.contains(contextPath)) {

			requestURI = requestURI.substring(contextPath.length());
		}

		requestURI = StringUtil.replace(
			requestURI, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		String i18nLanguageId = prependI18nLanguageId(
			httpServletRequest, PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE);

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

		int[] groupFriendlyURLIndex = PortalUtil.getGroupFriendlyURLIndex(
			requestURI);

		String groupFriendlyURL = StringPool.BLANK;

		int friendlyURLEnd = 0;

		if (groupFriendlyURLIndex != null) {
			int friendlyURLStart = groupFriendlyURLIndex[0];
			friendlyURLEnd = groupFriendlyURLIndex[1];

			groupFriendlyURL = requestURI.substring(
				friendlyURLStart, friendlyURLEnd);
		}

		Group friendlyURLGroup = GroupLocalServiceUtil.fetchFriendlyURLGroup(
			PortalUtil.getCompanyId(httpServletRequest), groupFriendlyURL);

		if ((friendlyURLGroup != null) &&
			!LanguageUtil.isAvailableLocale(
				friendlyURLGroup.getGroupId(), i18nLanguageId)) {

			return null;
		}

		LayoutSet layoutSet = (LayoutSet)httpServletRequest.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if ((layoutSet != null) && !layoutSet.isPrivateLayout() &&
			requestURI.startsWith(
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING)) {

			Group group = layoutSet.getGroup();

			if (groupFriendlyURL.equals(group.getFriendlyURL())) {
				redirect =
					contextPath + i18nPath +
						requestURI.substring(friendlyURLEnd);
			}
		}

		String queryString = httpServletRequest.getQueryString();

		if (Validator.isNull(queryString)) {
			queryString = (String)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING);
		}

		if (Validator.isNotNull(queryString)) {
			redirect += StringPool.QUESTION + queryString;
		}

		return redirect;
	}

	protected String getRequestedLanguageId(
		HttpServletRequest httpServletRequest, String userLanguageId) {

		HttpSession session = httpServletRequest.getSession();

		Locale locale = (Locale)session.getAttribute(WebKeys.LOCALE);

		String requestedLanguageId = null;

		if (locale != null) {
			requestedLanguageId = LocaleUtil.toLanguageId(locale);
		}

		if (Validator.isNull(requestedLanguageId)) {
			requestedLanguageId = userLanguageId;
		}

		if (Validator.isNull(requestedLanguageId)) {
			requestedLanguageId = CookieKeys.getCookie(
				httpServletRequest, CookieKeys.GUEST_LANGUAGE_ID, false);
		}

		return requestedLanguageId;
	}

	protected String getSiteDefaultLanguageId(
		HttpServletRequest httpServletRequest) {

		String friendlyURL = getFriendlyURL(httpServletRequest);

		long companyId = PortalUtil.getCompanyId(httpServletRequest);

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, friendlyURL);

			Locale siteDefaultLocale = PortalUtil.getSiteDefaultLocale(
				group.getGroupId());

			return LocaleUtil.toLanguageId(siteDefaultLocale);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe.getMessage(), pe);
			}

			return StringPool.BLANK;
		}
	}

	protected boolean isAlreadyFiltered(HttpServletRequest httpServletRequest) {
		if (httpServletRequest.getAttribute(SKIP_FILTER) != null) {
			return true;
		}

		return false;
	}

	protected boolean isForwardedByI18nServlet(
		HttpServletRequest httpServletRequest) {

		if ((httpServletRequest.getAttribute(WebKeys.I18N_LANGUAGE_ID) !=
				null) ||
			(httpServletRequest.getAttribute(WebKeys.I18N_PATH) != null)) {

			return true;
		}

		return false;
	}

	protected boolean isWidget(HttpServletRequest httpServletRequest) {
		if (httpServletRequest.getAttribute(WebKeys.WIDGET) != null) {
			return true;
		}

		return false;
	}

	protected String prependI18nLanguageId(
		HttpServletRequest httpServletRequest, int prependFriendlyUrlStyle) {

		User user = (User)httpServletRequest.getAttribute(WebKeys.USER);

		String userLanguageId = null;

		if (user != null) {
			userLanguageId = user.getLanguageId();
		}

		String requestedLanguageId = getRequestedLanguageId(
			httpServletRequest, userLanguageId);

		String defaultLanguageId = getDefaultLanguageId(httpServletRequest);

		if (Validator.isNull(requestedLanguageId)) {
			requestedLanguageId = defaultLanguageId;
		}

		if (prependFriendlyUrlStyle == 1) {
			return prependIfRequestedLocaleDiffersFromDefaultLocale(
				defaultLanguageId, requestedLanguageId);
		}
		else if (prependFriendlyUrlStyle == 2) {
			if (PropsValues.LOCALE_DEFAULT_REQUEST) {
				return LocaleUtil.toLanguageId(
					PortalUtil.getLocale(httpServletRequest));
			}

			return requestedLanguageId;
		}
		else if (prependFriendlyUrlStyle == 3) {
			if (user != null) {
				if (userLanguageId.equals(requestedLanguageId)) {
					return null;
				}

				return requestedLanguageId;
			}

			return prependIfRequestedLocaleDiffersFromDefaultLocale(
				defaultLanguageId, requestedLanguageId);
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		httpServletRequest.setAttribute(SKIP_FILTER, Boolean.TRUE);

		String redirect = getRedirect(httpServletRequest);

		if (redirect == null) {
			processFilter(
				I18nFilter.class.getName(), httpServletRequest,
				httpServletResponse, filterChain);

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		httpServletResponse.sendRedirect(redirect);
	}

	private static final Log _log = LogFactoryUtil.getLog(I18nFilter.class);

	private static Set<String> _languageIds;

}