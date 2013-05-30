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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
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
 */
public class FriendlyURLServlet extends HttpServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		_private = GetterUtil.getBoolean(
			servletConfig.getInitParameter("private"));
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
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		// Do not set the entire full main path. See LEP-456.

		//String mainPath = (String)ctx.getAttribute(WebKeys.MAIN_PATH);
		String mainPath = Portal.PATH_MAIN;

		String redirect = mainPath;

		String pathInfo = request.getPathInfo();

		request.setAttribute(
			WebKeys.FRIENDLY_URL, _friendlyURLPathPrefix.concat(pathInfo));

		Object[] object = null;

		boolean forceRedirect = false;

		try {
			object = getRedirect(
				request, pathInfo, mainPath, request.getParameterMap());

			redirect = (String)object[0];
			forceRedirect = GetterUtil.getBoolean(object[1]);

			if (request.getAttribute(WebKeys.LAST_PATH) == null) {
				LastPath lastPath = new LastPath(
					_friendlyURLPathPrefix, pathInfo,
					request.getParameterMap());

				request.setAttribute(WebKeys.LAST_PATH, lastPath);
			}
		}
		catch (NoSuchLayoutException nsle) {
			_log.warn(nsle);

			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsle, request, response);

			return;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		if (Validator.isNull(redirect)) {
			redirect = mainPath;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		if ((redirect.charAt(0) == CharPool.SLASH) && !forceRedirect) {
			ServletContext servletContext = getServletContext();

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else {
			response.sendRedirect(redirect);
		}
	}

	protected Object[] getRedirect(
			HttpServletRequest request, String path, String mainPath,
			Map<String, String[]> params)
		throws Exception {

		if (Validator.isNull(path) || (path.charAt(0) != CharPool.SLASH)) {
			return new Object[] {mainPath, false};
		}

		// Group friendly URL

		String friendlyURL = null;

		int pos = path.indexOf(CharPool.SLASH, 1);

		if (pos != -1) {
			friendlyURL = path.substring(0, pos);
		}
		else if (path.length() > 1) {
			friendlyURL = path;
		}

		if (Validator.isNull(friendlyURL)) {
			return new Object[] {mainPath, false};
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
			return new Object[] {mainPath, false};
		}

		// Layout friendly URL

		friendlyURL = null;

		if ((pos != -1) && ((pos + 1) != path.length())) {
			friendlyURL = path.substring(pos);
		}

		if (Validator.isNull(friendlyURL)) {
			request.setAttribute(
				WebKeys.REDIRECT_TO_DEFAULT_LAYOUT, Boolean.TRUE);
		}

		Map<String, Object> requestContext = new HashMap<String, Object>();

		requestContext.put("request", request);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = ServiceContextFactory.getInstance(request);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		String actualURL = PortalUtil.getActualURL(
			group.getGroupId(), _private, mainPath, friendlyURL, params,
			requestContext);

		if (Validator.isNotNull(friendlyURL)) {
			Locale locale = PortalUtil.getLocale(request);

			Object[] object = PortalUtil.getLayout(
				group.getGroupId(), _private, friendlyURL, params,
				requestContext);

			Layout layout = (Layout)object[0];
			friendlyURL = (String)object[1];

			if (isInvalidLocalizedFriendlyURL(layout, friendlyURL, locale)) {
				String redirect = PortalUtil.getLocalizedFriendlyURL(
					request, layout, locale);

				return new Object[] {redirect, true};
			}
		}

		return new Object[] {actualURL, false};
	}

	protected boolean isInvalidLocalizedFriendlyURL(
			Layout layout, String friendlyURL, Locale locale)
		throws Exception {

		if (!friendlyURL.startsWith(layout.getFriendlyURL(locale))) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(FriendlyURLServlet.class);

	private String _friendlyURLPathPrefix;
	private boolean _private;
	private boolean _user;

}