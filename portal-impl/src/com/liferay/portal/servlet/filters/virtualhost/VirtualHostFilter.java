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

package com.liferay.portal.servlet.filters.virtualhost;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.webserver.WebServerServlet;

import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * This filter is used to provide virtual host functionality.
 * </p>
 *
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public class VirtualHostFilter extends BasePortalFilter {

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();

		_originalContextPath = PortalUtil.getPathContext();

		String contextPath = _originalContextPath;

		String proxyPath = PortalUtil.getPathProxy();

		if (!contextPath.isEmpty() && !proxyPath.isEmpty() &&
			contextPath.startsWith(proxyPath)) {

			contextPath = contextPath.substring(proxyPath.length());
		}

		_contextPath = contextPath;
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		String uri = httpServletRequest.getRequestURI();

		for (String extension : PropsValues.VIRTUAL_HOSTS_IGNORE_EXTENSIONS) {
			if (uri.endsWith(extension)) {
				return false;
			}
		}

		return true;
	}

	protected boolean isDocumentFriendlyURL(
			HttpServletRequest httpServletRequest, long groupId,
			String friendlyURL)
		throws PortalException {

		if (friendlyURL.startsWith(_PATH_DOCUMENTS) &&
			WebServerServlet.hasFiles(httpServletRequest)) {

			String path = HttpUtil.fixPath(httpServletRequest.getPathInfo());

			String[] pathArray = StringUtil.split(path, CharPool.SLASH);

			if (pathArray.length == 2) {
				try {
					LayoutLocalServiceUtil.getFriendlyURLLayout(
						groupId, false, friendlyURL);
				}
				catch (NoSuchLayoutException noSuchLayoutException) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(
							noSuchLayoutException, noSuchLayoutException);
					}

					return true;
				}
			}
			else {
				return true;
			}
		}

		return false;
	}

	protected boolean isValidFriendlyURL(String friendlyURL) {
		friendlyURL = StringUtil.toLowerCase(friendlyURL);

		if (PortalInstances.isVirtualHostsIgnorePath(friendlyURL) ||
			friendlyURL.startsWith(_PATH_MODULE_SLASH) ||
			friendlyURL.startsWith(_PRIVATE_GROUP_SERVLET_MAPPING_SLASH) ||
			friendlyURL.startsWith(_PRIVATE_USER_SERVLET_MAPPING_SLASH) ||
			friendlyURL.startsWith(_PUBLIC_GROUP_SERVLET_MAPPING_SLASH)) {

			return false;
		}

		if (LayoutImpl.hasFriendlyURLKeyword(friendlyURL)) {
			return false;
		}

		int code = LayoutImpl.validateFriendlyURL(friendlyURL, false);

		if ((code > -1) &&
			(code != LayoutFriendlyURLException.ENDS_WITH_SLASH)) {

			return false;
		}

		return true;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		long companyId = PortalInstances.getCompanyId(httpServletRequest);

		String originalFriendlyURL = HttpUtil.normalizePath(
			httpServletRequest.getRequestURI());

		String friendlyURL = originalFriendlyURL;

		if (!friendlyURL.equals(StringPool.SLASH) && !_contextPath.isEmpty() &&
			(friendlyURL.length() > _contextPath.length()) &&
			friendlyURL.startsWith(_contextPath) &&
			(friendlyURL.charAt(_contextPath.length()) == CharPool.SLASH)) {

			friendlyURL = friendlyURL.substring(_contextPath.length());
		}

		int pos = friendlyURL.indexOf(CharPool.SEMICOLON);

		if (pos != -1) {
			friendlyURL = friendlyURL.substring(0, pos);
		}

		String i18nLanguageId = _findLanguageId(friendlyURL);

		if (i18nLanguageId != null) {
			friendlyURL = friendlyURL.substring(i18nLanguageId.length());
		}

		int widgetServletMappingPos = 0;

		if (friendlyURL.contains(_WIDGET_SERVLET_MAPPING_SLASH)) {
			friendlyURL = StringUtil.replaceFirst(
				friendlyURL, PropsValues.WIDGET_SERVLET_MAPPING,
				StringPool.BLANK);

			widgetServletMappingPos =
				PropsValues.WIDGET_SERVLET_MAPPING.length();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Friendly URL " + friendlyURL);
		}

		if (!friendlyURL.equals(StringPool.SLASH) &&
			!isValidFriendlyURL(friendlyURL)) {

			_log.debug("Friendly URL is not valid");

			if (i18nLanguageId != null) {
				int offset =
					originalFriendlyURL.length() - friendlyURL.length() -
						(i18nLanguageId.length() + widgetServletMappingPos);

				if (!originalFriendlyURL.regionMatches(
						offset, i18nLanguageId, 0, i18nLanguageId.length())) {

					String forwardURL = originalFriendlyURL;

					if (offset > 0) {
						String prefix = originalFriendlyURL.substring(
							0, offset);

						forwardURL = prefix.concat(i18nLanguageId);
					}
					else {
						forwardURL = i18nLanguageId;
					}

					forwardURL = forwardURL.concat(friendlyURL);

					RequestDispatcher requestDispatcher =
						_servletContext.getRequestDispatcher(forwardURL);

					requestDispatcher.forward(
						httpServletRequest, httpServletResponse);

					return;
				}
			}

			processFilter(
				VirtualHostFilter.class.getName(), httpServletRequest,
				httpServletResponse, filterChain);

			return;
		}

		LayoutSet layoutSet = (LayoutSet)httpServletRequest.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if (_log.isDebugEnabled()) {
			_log.debug("Layout set " + layoutSet);
		}

		if (layoutSet == null) {
			processFilter(
				VirtualHostFilter.class.getName(), httpServletRequest,
				httpServletResponse, filterChain);

			return;
		}

		try {
			Map<String, String[]> parameterMap =
				httpServletRequest.getParameterMap();

			String parameters = StringPool.BLANK;

			if (!parameterMap.isEmpty()) {
				parameters = HttpUtil.parameterMapToString(parameterMap);
			}

			LastPath lastPath = new LastPath(
				_originalContextPath, friendlyURL, parameters);

			httpServletRequest.setAttribute(WebKeys.LAST_PATH, lastPath);

			StringBundler sb = new StringBundler(5);

			if (i18nLanguageId != null) {
				sb.append(i18nLanguageId);
			}

			if (originalFriendlyURL.startsWith(
					PropsValues.WIDGET_SERVLET_MAPPING)) {

				sb.append(PropsValues.WIDGET_SERVLET_MAPPING);

				friendlyURL = StringUtil.replaceFirst(
					friendlyURL, PropsValues.WIDGET_SERVLET_MAPPING,
					StringPool.BLANK);
			}

			if (friendlyURL.equals(StringPool.SLASH) ||
				(PortalUtil.getPlidFromFriendlyURL(companyId, friendlyURL) <=
					0)) {

				Group group = layoutSet.getGroup();

				if (isDocumentFriendlyURL(
						httpServletRequest, group.getGroupId(), friendlyURL)) {

					processFilter(
						VirtualHostFilter.class.getName(), httpServletRequest,
						httpServletResponse, filterChain);

					return;
				}

				if (PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME.equals(
						group.getGroupKey()) &&
					friendlyURL.equals(StringPool.SLASH) &&
					!layoutSet.isPrivateLayout()) {

					String homeURL = PortalUtil.getRelativeHomeURL(
						httpServletRequest);

					if (Validator.isNotNull(homeURL)) {
						friendlyURL = homeURL;
					}
				}
				else {
					if (layoutSet.isPrivateLayout()) {
						if (group.isUser()) {
							sb.append(_PRIVATE_USER_SERVLET_MAPPING);
						}
						else {
							sb.append(_PRIVATE_GROUP_SERVLET_MAPPING);
						}
					}
					else {
						sb.append(_PUBLIC_GROUP_SERVLET_MAPPING);
					}

					sb.append(group.getFriendlyURL());
				}
			}

			String forwardURLString = friendlyURL;

			if (sb.index() > 0) {
				sb.append(friendlyURL);

				forwardURLString = sb.toString();
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Forward to " + forwardURLString);
			}

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(forwardURLString);

			requestDispatcher.forward(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			processFilter(
				VirtualHostFilter.class.getName(), httpServletRequest,
				httpServletResponse, filterChain);
		}
	}

	private String _findLanguageId(String friendlyURL) {
		if (friendlyURL.isEmpty() ||
			(friendlyURL.charAt(0) != CharPool.SLASH)) {

			return null;
		}

		String lowerCaseLanguageId = friendlyURL;

		int index = friendlyURL.indexOf(CharPool.SLASH, 1);

		if (index != -1) {
			lowerCaseLanguageId = friendlyURL.substring(0, index);
		}

		lowerCaseLanguageId = StringUtil.toLowerCase(lowerCaseLanguageId);

		Map<String, String> languageIds = I18nServlet.getLanguageIdsMap();

		String languageId = languageIds.get(lowerCaseLanguageId);

		if (languageId == null) {
			return null;
		}

		return languageId;
	}

	private static final String _PATH_DOCUMENTS = "/documents/";

	private static final String _PATH_MODULE_SLASH =
		Portal.PATH_MODULE + StringPool.SLASH;

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING_SLASH =
		_PRIVATE_GROUP_SERVLET_MAPPING + StringPool.SLASH;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING_SLASH =
		_PRIVATE_USER_SERVLET_MAPPING + StringPool.SLASH;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING_SLASH =
		_PUBLIC_GROUP_SERVLET_MAPPING + StringPool.SLASH;

	private static final String _WIDGET_SERVLET_MAPPING_SLASH =
		PropsValues.WIDGET_SERVLET_MAPPING + StringPool.SLASH;

	private static final Log _log = LogFactoryUtil.getLog(
		VirtualHostFilter.class);

	private String _contextPath;
	private String _originalContextPath;
	private ServletContext _servletContext;

}