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

package com.liferay.portal.servlet.filters.cache;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.filters.CacheResponseData;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Alexander Chow
 * @author Javier de Ros
 * @author Raymond Augé
 */
public class CacheFilter extends BasePortalFilter {

	public static final String SKIP_FILTER = CacheFilter.class + "SKIP_FILTER";

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_pattern = GetterUtil.getInteger(
			filterConfig.getInitParameter("pattern"));

		if ((_pattern != _PATTERN_FRIENDLY) &&
			(_pattern != _PATTERN_LAYOUT) &&
			(_pattern != _PATTERN_RESOURCE)) {

			_log.error("Cache pattern is invalid");
		}
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (isCacheableRequest(request) && !isInclude(request) &&
			!isAlreadyFiltered(request)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected String getCacheKey(HttpServletRequest request) {
		StringBundler sb = new StringBundler(13);

		// Url

		sb.append(HttpUtil.getProtocol(request));
		sb.append(Http.PROTOCOL_DELIMITER);
		sb.append(request.getContextPath());
		sb.append(request.getServletPath());
		sb.append(request.getPathInfo());
		sb.append(StringPool.QUESTION);

		String queryString = request.getQueryString();

		if (queryString == null) {
			queryString = (String)request.getAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING);

			if (queryString == null) {
				String url = PortalUtil.getCurrentCompleteURL(request);

				int pos = url.indexOf(StringPool.QUESTION);

				if (pos > -1) {
					queryString = url.substring(pos + 1);
				}
			}
		}

		if (queryString != null) {
			sb.append(queryString);
		}

		// Language

		sb.append(StringPool.POUND);

		String languageId = (String)request.getAttribute(
			WebKeys.I18N_LANGUAGE_ID);

		if (Validator.isNull(languageId)) {
			languageId = LanguageUtil.getLanguageId(request);
		}

		sb.append(languageId);

		// User agent

		String userAgent = GetterUtil.getString(
			request.getHeader(HttpHeaders.USER_AGENT));

		sb.append(StringPool.POUND);
		sb.append(StringUtil.toLowerCase(userAgent).hashCode());

		// Gzip compression

		sb.append(StringPool.POUND);
		sb.append(BrowserSnifferUtil.acceptsGzip(request));

		return StringUtil.toUpperCase(sb.toString().trim());
	}

	protected long getPlid(
		long companyId, String pathInfo, String servletPath, long defaultPlid) {

		if (_pattern == _PATTERN_LAYOUT) {
			return defaultPlid;
		}

		if (Validator.isNull(pathInfo) ||
			!pathInfo.startsWith(StringPool.SLASH)) {

			return 0;
		}

		// Group friendly URL

		String friendlyURL = null;

		int pos = pathInfo.indexOf(CharPool.SLASH, 1);

		if (pos != -1) {
			friendlyURL = pathInfo.substring(0, pos);
		}
		else if (pathInfo.length() > 1) {
			friendlyURL = pathInfo;
		}

		if (Validator.isNull(friendlyURL)) {
			return 0;
		}

		long groupId = 0;
		boolean privateLayout = false;

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, friendlyURL);

			groupId = group.getGroupId();

			if (servletPath.startsWith(
					PropsValues.
						LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING) ||
				servletPath.startsWith(
					PropsValues.
						LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING)) {

				privateLayout = true;
			}
			else if (servletPath.startsWith(
						PropsValues.
							LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING)) {

				privateLayout = false;
			}
		}
		catch (NoSuchLayoutException nsle) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsle);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}

			return 0;
		}

		// Layout friendly URL

		friendlyURL = null;

		if ((pos != -1) && ((pos + 1) != pathInfo.length())) {
			friendlyURL = pathInfo.substring(pos);
		}

		if (Validator.isNull(friendlyURL)) {
			try {
				long plid = LayoutLocalServiceUtil.getDefaultPlid(
					groupId, privateLayout);

				return plid;
			}
			catch (Exception e) {
				_log.warn(e);

				return 0;
			}
		}
		else if (friendlyURL.endsWith(StringPool.FORWARD_SLASH)) {
			friendlyURL = friendlyURL.substring(0, friendlyURL.length() - 1);
		}

		// If there is no layout path take the first from the group or user

		try {
			Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				groupId, privateLayout, friendlyURL);

			return layout.getPlid();
		}
		catch (NoSuchLayoutException nsle) {
			_log.warn(nsle);

			return 0;
		}
		catch (Exception e) {
			_log.error(e);

			return 0;
		}
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isCacheableData(
		long companyId, HttpServletRequest request) {

		try {
			if (_pattern == _PATTERN_RESOURCE) {
				return true;
			}

			long plid = getPlid(
				companyId, request.getPathInfo(), request.getServletPath(),
				ParamUtil.getLong(request, "p_l_id"));

			if (plid <= 0) {
				return false;
			}

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!layout.isTypePortlet()) {
				return false;
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			return layoutTypePortlet.isCacheable();
		}
		catch (Exception e) {
			return false;
		}
	}

	protected boolean isCacheableRequest(HttpServletRequest request) {
		String portletId = ParamUtil.getString(request, "p_p_id");

		if (Validator.isNotNull(portletId)) {
			return false;
		}

		if ((_pattern == _PATTERN_FRIENDLY) || (_pattern == _PATTERN_LAYOUT)) {
			long userId = PortalUtil.getUserId(request);
			String remoteUser = request.getRemoteUser();

			if ((userId > 0) || Validator.isNotNull(remoteUser)) {
				return false;
			}
		}

		if (_pattern == _PATTERN_LAYOUT) {
			String plid = ParamUtil.getString(request, "p_l_id");

			if (Validator.isNull(plid)) {
				return false;
			}
		}

		return true;
	}

	protected boolean isCacheableResponse(
		BufferCacheServletResponse bufferCacheServletResponse) {

		if ((bufferCacheServletResponse.getStatus() ==
				HttpServletResponse.SC_OK) &&
			(bufferCacheServletResponse.getBufferSize() <
				PropsValues.CACHE_CONTENT_THRESHOLD_SIZE)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isInclude(HttpServletRequest request) {
		String uri = (String)request.getAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);

		if (uri == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);

		String key = getCacheKey(request);

		String pAuth = request.getParameter("p_auth");

		if (Validator.isNotNull(pAuth)) {
			try {
				AuthTokenUtil.checkCSRFToken(
					request, CacheFilter.class.getName());
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Request is not cacheable " + key +
							", invalid token received");
				}

				processFilter(
					CacheFilter.class, request, response, filterChain);

				return;
			}

			key = key.replace(StringUtil.toUpperCase(pAuth), "VALID");
		}

		long companyId = PortalInstances.getCompanyId(request);

		CacheResponseData cacheResponseData = CacheUtil.getCacheResponseData(
			companyId, key);

		if (cacheResponseData == null) {
			if (!isCacheableData(companyId, request)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Request is not cacheable " + key);
				}

				processFilter(
					CacheFilter.class, request, response, filterChain);

				return;
			}

			if (_log.isInfoEnabled()) {
				_log.info("Caching request " + key);
			}

			BufferCacheServletResponse bufferCacheServletResponse =
				new BufferCacheServletResponse(response);

			processFilter(
				CacheFilter.class, request, bufferCacheServletResponse,
				filterChain);

			cacheResponseData = new CacheResponseData(
				bufferCacheServletResponse);

			LastPath lastPath = (LastPath)request.getAttribute(
				WebKeys.LAST_PATH);

			if (lastPath != null) {
				cacheResponseData.setAttribute(WebKeys.LAST_PATH, lastPath);
			}

			// Cache the result if and only if there is a result and the request
			// is cacheable. We have to test the cacheability of a request twice
			// because the user could have been authenticated after the initial
			// test.

			String cacheControl = GetterUtil.getString(
				bufferCacheServletResponse.getHeader(
					HttpHeaders.CACHE_CONTROL));

			if ((bufferCacheServletResponse.getStatus() ==
					HttpServletResponse.SC_OK) &&
				!cacheControl.contains(HttpHeaders.PRAGMA_NO_CACHE_VALUE) &&
				isCacheableRequest(request) &&
				isCacheableResponse(bufferCacheServletResponse)) {

				CacheUtil.putCacheResponseData(
					companyId, key, cacheResponseData);
			}
		}
		else {
			LastPath lastPath = (LastPath)cacheResponseData.getAttribute(
				WebKeys.LAST_PATH);

			if (lastPath != null) {
				HttpSession session = request.getSession();

				session.setAttribute(WebKeys.LAST_PATH, lastPath);
			}
		}

		CacheResponseUtil.write(response, cacheResponseData);
	}

	private static final int _PATTERN_FRIENDLY = 0;

	private static final int _PATTERN_LAYOUT = 1;

	private static final int _PATTERN_RESOURCE = 2;

	private static final Log _log = LogFactoryUtil.getLog(CacheFilter.class);

	private int _pattern;

}