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

import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LayoutFriendlyURLSeparatorComposite;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PortalMessages;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalInstances;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Shuyang Zhou
 * @author Marco Leo
 */
public class FriendlyURLServlet extends HttpServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		_private = GetterUtil.getBoolean(
			servletConfig.getInitParameter("private"));

		String proxyPath = PortalUtil.getPathProxy();

		_user = GetterUtil.getBoolean(servletConfig.getInitParameter("user"));

		if (_private) {
			if (_user) {
				_friendlyURLPathPrefix =
					PortalUtil.getPathFriendlyURLPrivateUser();
			}
			else {
				_friendlyURLPathPrefix =
					PortalUtil.getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			_friendlyURLPathPrefix = PortalUtil.getPathFriendlyURLPublic();
		}

		_pathInfoOffset = _friendlyURLPathPrefix.length() - proxyPath.length();
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		// Do not set the entire full main path. See LEP-456.

		String redirect = Portal.PATH_MAIN;

		String pathInfo = getPathInfo(request);

		boolean forcePermanentRedirect = false;

		try {
			Object[] redirectArray = _getRedirect(request, pathInfo);

			redirect = (String)redirectArray[0];
			forcePermanentRedirect = (Boolean)redirectArray[1];

			if (request.getAttribute(WebKeys.LAST_PATH) == null) {
				LastPath lastPath = null;

				String lifecycle = ParamUtil.getString(
					request, "p_p_lifecycle");

				if (lifecycle.equals("1")) {
					lastPath = new LastPath(_friendlyURLPathPrefix, pathInfo);
				}
				else {
					lastPath = new LastPath(
						_friendlyURLPathPrefix, pathInfo,
						HttpUtil.parameterMapToString(
							request.getParameterMap()));
				}

				request.setAttribute(WebKeys.LAST_PATH, lastPath);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}

			if (e instanceof NoSuchGroupException ||
				e instanceof NoSuchLayoutException) {

				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND, e, request, response);

				return;
			}
		}

		if (Validator.isNull(redirect)) {
			redirect = Portal.PATH_MAIN;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		if ((redirect.charAt(0) == CharPool.SLASH) && !forcePermanentRedirect) {
			ServletContext servletContext = getServletContext();

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else {
			if (forcePermanentRedirect) {
				response.setHeader("Location", redirect);
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			}
			else {
				response.sendRedirect(redirect);
			}
		}
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.taglib.util.FriendlyURLUtil.getFriendlyURL(
	 *             HttpServletRequest, javax.servlet.jsp.PageContext)}
	 */
	@Deprecated
	protected String getFriendlyURL(String pathInfo) {
		String friendlyURL = _friendlyURLPathPrefix;

		if (Validator.isNotNull(pathInfo)) {
			friendlyURL = friendlyURL.concat(pathInfo);
		}

		return friendlyURL;
	}

	protected String getPathInfo(HttpServletRequest request) {
		String requestURI = request.getRequestURI();

		int pos = requestURI.indexOf(Portal.JSESSIONID);

		if (pos == -1) {
			return requestURI.substring(_pathInfoOffset);
		}

		return requestURI.substring(_pathInfoOffset, pos);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected Object[] getRedirect(
			HttpServletRequest request, String path, String mainPath,
			Map<String, String[]> params)
		throws Exception {

		return _getRedirect(request, path);
	}

	protected Locale setAlternativeLayoutFriendlyURL(
			HttpServletRequest request, Layout layout, String friendlyURL)
		throws Exception {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid(), friendlyURL, 0, 1);

		if (layoutFriendlyURLs.isEmpty()) {
			return null;
		}

		LayoutFriendlyURL layoutFriendlyURL = layoutFriendlyURLs.get(0);

		Locale locale = LocaleUtil.fromLanguageId(
			layoutFriendlyURL.getLanguageId());

		String alternativeLayoutFriendlyURL =
			PortalUtil.getLocalizedFriendlyURL(request, layout, locale, locale);

		SessionMessages.add(
			request, "alternativeLayoutFriendlyURL",
			alternativeLayoutFriendlyURL);

		PortalMessages.add(
			request, PortalMessages.KEY_JSP_PATH,
			"/html/common/themes/layout_friendly_url_redirect.jsp");

		return locale;
	}

	private Object[] _getRedirect(HttpServletRequest request, String path)
		throws Exception {

		if (path.length() <= 1) {
			return new Object[] {Portal.PATH_MAIN, Boolean.FALSE};
		}

		// Group friendly URL

		String friendlyURL = path;

		int pos = path.indexOf(CharPool.SLASH, 1);

		if (pos != -1) {
			friendlyURL = path.substring(0, pos);
		}

		long companyId = PortalInstances.getCompanyId(request);

		Group group = GroupLocalServiceUtil.fetchFriendlyURLGroup(
			companyId, friendlyURL);

		if (group == null) {
			String screenName = friendlyURL.substring(1);

			if (_user || !Validator.isNumber(screenName)) {
				User user = UserLocalServiceUtil.fetchUserByScreenName(
					companyId, screenName);

				if (user != null) {
					group = user.getGroup();
				}
				else if (_log.isWarnEnabled()) {
					_log.warn("No user exists with friendly URL " + screenName);
				}
			}
			else {
				long groupId = GetterUtil.getLong(screenName);

				group = GroupLocalServiceUtil.fetchGroup(groupId);

				if (group == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"No group exists with friendly URL " + groupId +
								". Try fetching by screen name instead.");
					}

					User user = UserLocalServiceUtil.fetchUserByScreenName(
						companyId, screenName);

					if (user != null) {
						group = user.getGroup();
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(
							"No user or group exists with friendly URL " +
								groupId);
					}
				}
			}
		}

		if (group == null) {
			StringBundler sb = new StringBundler(5);

			sb.append("{companyId=");
			sb.append(companyId);
			sb.append(", friendlyURL=");
			sb.append(friendlyURL);
			sb.append("}");

			throw new NoSuchGroupException(sb.toString());
		}

		// Layout friendly URL

		friendlyURL = null;

		if ((pos != -1) && ((pos + 1) != path.length())) {
			friendlyURL = path.substring(pos);
		}
		else {
			request.setAttribute(
				WebKeys.REDIRECT_TO_DEFAULT_LAYOUT, Boolean.TRUE);
		}

		Map<String, Object> requestContext = new HashMap<>();

		requestContext.put("request", request);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = ServiceContextFactory.getInstance(request);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		Map<String, String[]> params = request.getParameterMap();

		try {
			LayoutFriendlyURLSeparatorComposite
				layoutFriendlyURLSeparatorComposite =
					PortalUtil.getLayoutFriendlyURLSeparatorComposite(
						group.getGroupId(), _private, friendlyURL, params,
						requestContext);

			Layout layout = layoutFriendlyURLSeparatorComposite.getLayout();

			request.setAttribute(WebKeys.LAYOUT, layout);

			Locale locale = PortalUtil.getLocale(request);

			String layoutFriendlyURLSeparatorCompositeFriendlyURL =
				layoutFriendlyURLSeparatorComposite.getFriendlyURL();

			if (Validator.isNull(
					layoutFriendlyURLSeparatorCompositeFriendlyURL)) {

				layoutFriendlyURLSeparatorCompositeFriendlyURL =
					layout.getFriendlyURL(locale);
			}

			pos = layoutFriendlyURLSeparatorCompositeFriendlyURL.indexOf(
				layoutFriendlyURLSeparatorComposite.getURLSeparator());

			if (pos != 0) {
				if (pos != -1) {
					layoutFriendlyURLSeparatorCompositeFriendlyURL =
						layoutFriendlyURLSeparatorCompositeFriendlyURL.
							substring(0, pos);
				}

				String i18nLanguageId = (String)request.getAttribute(
					WebKeys.I18N_LANGUAGE_ID);

				if ((Validator.isNotNull(i18nLanguageId) &&
					 !LanguageUtil.isAvailableLocale(
						 group.getGroupId(), i18nLanguageId)) ||
					!StringUtil.equalsIgnoreCase(
						layoutFriendlyURLSeparatorCompositeFriendlyURL,
						layout.getFriendlyURL(locale))) {

					Locale originalLocale = setAlternativeLayoutFriendlyURL(
						request, layout,
						layoutFriendlyURLSeparatorCompositeFriendlyURL);

					String redirect = PortalUtil.getLocalizedFriendlyURL(
						request, layout, locale, originalLocale);

					return new Object[] {redirect, Boolean.TRUE};
				}
			}
		}
		catch (NoSuchLayoutException nsle) {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				group.getGroupId(), _private,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			for (Layout layout : layouts) {
				if (layout.matches(request, friendlyURL)) {
					String redirect = PortalUtil.getLayoutActualURL(
						layout, Portal.PATH_MAIN);

					return new Object[] {redirect, Boolean.FALSE};
				}
			}

			throw nsle;
		}

		String actualURL = PortalUtil.getActualURL(
			group.getGroupId(), _private, Portal.PATH_MAIN, friendlyURL, params,
			requestContext);

		return new Object[] {actualURL, Boolean.FALSE};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLServlet.class);

	private String _friendlyURLPathPrefix;
	private int _pathInfoOffset;
	private boolean _private;
	private boolean _user;

}