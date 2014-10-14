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

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.webserver.WebServerServlet;

import java.util.Set;

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
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		StringBuffer requestURL = request.getRequestURL();

		if (isValidRequestURL(requestURL)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isDocumentFriendlyURL(
			HttpServletRequest request, long groupId, String friendlyURL)
		throws PortalException {

		if (friendlyURL.startsWith(_PATH_DOCUMENTS) &&
			WebServerServlet.hasFiles(request)) {

			String path = HttpUtil.fixPath(request.getPathInfo());

			String[] pathArray = StringUtil.split(path, CharPool.SLASH);

			if (pathArray.length == 2) {
				try {
					LayoutLocalServiceUtil.getFriendlyURLLayout(
						groupId, false, friendlyURL);
				}
				catch (NoSuchLayoutException nsle) {
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

	protected boolean isValidRequestURL(StringBuffer requestURL) {
		if (requestURL == null) {
			return false;
		}

		String url = requestURL.toString();

		for (String extension : PropsValues.VIRTUAL_HOSTS_IGNORE_EXTENSIONS) {
			if (url.endsWith(extension)) {
				return false;
			}
		}

		return true;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		long companyId = PortalInstances.getCompanyId(request);

		String contextPath = PortalUtil.getPathContext();

		String originalFriendlyURL = request.getRequestURI();

		String friendlyURL = originalFriendlyURL;

		if (Validator.isNotNull(contextPath) &&
			friendlyURL.contains(contextPath)) {

			friendlyURL = friendlyURL.substring(contextPath.length());
		}

		int pos = friendlyURL.indexOf(StringPool.SEMICOLON);

		if (pos != -1) {
			friendlyURL = friendlyURL.substring(0, pos);
		}

		friendlyURL = StringUtil.replace(
			friendlyURL, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		String i18nLanguageId = null;

		Set<String> languageIds = I18nServlet.getLanguageIds();

		for (String languageId : languageIds) {
			if (StringUtil.startsWith(friendlyURL, languageId)) {
				pos = friendlyURL.indexOf(CharPool.SLASH, 1);

				if (((pos != -1) && (pos != languageId.length())) ||
					((pos == -1) &&
					 !StringUtil.equalsIgnoreCase(friendlyURL, languageId))) {

					continue;
				}

				if (pos == -1) {
					i18nLanguageId = languageId;
					friendlyURL = StringPool.SLASH;
				}
				else {
					i18nLanguageId = languageId.substring(0, pos);
					friendlyURL = friendlyURL.substring(pos);
				}

				break;
			}
		}

		friendlyURL = StringUtil.replace(
			friendlyURL, PropsValues.WIDGET_SERVLET_MAPPING, StringPool.BLANK);

		if (_log.isDebugEnabled()) {
			_log.debug("Friendly URL " + friendlyURL);
		}

		if (!friendlyURL.equals(StringPool.SLASH) &&
			!isValidFriendlyURL(friendlyURL)) {

			_log.debug("Friendly URL is not valid");

			processFilter(
				VirtualHostFilter.class, request, response, filterChain);

			return;
		}

		LayoutSet layoutSet = (LayoutSet)request.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if (_log.isDebugEnabled()) {
			_log.debug("Layout set " + layoutSet);
		}

		if (layoutSet == null) {
			processFilter(
				VirtualHostFilter.class, request, response, filterChain);

			return;
		}

		try {
			LastPath lastPath = new LastPath(
				contextPath, friendlyURL, request.getParameterMap());

			request.setAttribute(WebKeys.LAST_PATH, lastPath);

			StringBundler forwardURL = new StringBundler(5);

			if (i18nLanguageId != null) {
				forwardURL.append(i18nLanguageId);
			}

			if (originalFriendlyURL.startsWith(
					PropsValues.WIDGET_SERVLET_MAPPING)) {

				forwardURL.append(PropsValues.WIDGET_SERVLET_MAPPING);

				friendlyURL = StringUtil.replaceFirst(
					friendlyURL, PropsValues.WIDGET_SERVLET_MAPPING,
					StringPool.BLANK);
			}

			long plid = PortalUtil.getPlidFromFriendlyURL(
				companyId, friendlyURL);

			if (plid <= 0) {
				Group group = GroupLocalServiceUtil.getGroup(
					layoutSet.getGroupId());

				if (isDocumentFriendlyURL(
						request, group.getGroupId(), friendlyURL)) {

					processFilter(
						VirtualHostFilter.class, request, response,
						filterChain);

					return;
				}

				if (group.isGuest() && friendlyURL.equals(StringPool.SLASH) &&
					!layoutSet.isPrivateLayout()) {

					String homeURL = PortalUtil.getRelativeHomeURL(request);

					if (Validator.isNotNull(homeURL)) {
						friendlyURL = homeURL;
					}
				}
				else {
					if (layoutSet.isPrivateLayout()) {
						if (group.isUser()) {
							forwardURL.append(_PRIVATE_USER_SERVLET_MAPPING);
						}
						else {
							forwardURL.append(_PRIVATE_GROUP_SERVLET_MAPPING);
						}
					}
					else {
						forwardURL.append(_PUBLIC_GROUP_SERVLET_MAPPING);
					}

					forwardURL.append(group.getFriendlyURL());
				}
			}

			forwardURL.append(friendlyURL);

			if (_log.isDebugEnabled()) {
				_log.debug("Forward to " + forwardURL);
			}

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(forwardURL.toString());

			requestDispatcher.forward(request, response);
		}
		catch (Exception e) {
			_log.error(e, e);

			processFilter(
				VirtualHostFilter.class, request, response, filterChain);
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		VirtualHostFilter.class);

	private ServletContext _servletContext;

}