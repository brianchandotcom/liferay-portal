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

package com.liferay.friendly.url.servlet;

import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LayoutFriendlyURLSeparatorComposite;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.PortalMessages;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalInstances;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.service.SiteFriendlyURLLocalService;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Shuyang Zhou
 * @author Marco Leo
 */
public class FriendlyURLServlet extends HttpServlet {

	public Redirect getRedirect(HttpServletRequest request, String path)
		throws PortalException {

		if (path.length() <= 1) {
			return new Redirect();
		}

		// Group friendly URL

		String friendlyURL = path;

		int pos = path.indexOf(CharPool.SLASH, 1);

		if (pos != -1) {
			friendlyURL = path.substring(0, pos);
		}

		long companyId = PortalInstances.getCompanyId(request);

		Group group = groupLocalService.fetchFriendlyURLGroup(
			companyId, friendlyURL);

		if (group == null) {
			String screenName = friendlyURL.substring(1);

			if (_user || !Validator.isNumber(screenName)) {
				User user = userLocalService.fetchUserByScreenName(
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

				group = groupLocalService.fetchGroup(groupId);

				if (group == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"No group exists with friendly URL " + groupId +
								". Try fetching by screen name instead.");
					}

					User user = userLocalService.fetchUserByScreenName(
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

		Locale locale = portal.getLocale(request);

		SiteFriendlyURL siteFriendlyURL =
			siteFriendlyURLLocalService.fetchSiteFriendlyURL(
				companyId, group.getGroupId(), LocaleUtil.toLanguageId(locale));

		if (siteFriendlyURL == null) {
			siteFriendlyURL =
				siteFriendlyURLLocalService.fetchSiteFriendlyURLByFriendlyURL(
					companyId, friendlyURL);
		}

		SiteFriendlyURL alternativeSiteFriendlyURL = null;

		if ((siteFriendlyURL != null) &&
			!StringUtil.equalsIgnoreCase(
				siteFriendlyURL.getFriendlyURL(), friendlyURL)) {

			alternativeSiteFriendlyURL =
				siteFriendlyURLLocalService.fetchSiteFriendlyURLByFriendlyURL(
					siteFriendlyURL.getCompanyId(), friendlyURL);
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
					portal.getLayoutFriendlyURLSeparatorComposite(
						group.getGroupId(), _private, friendlyURL, params,
						requestContext);

			Layout layout = layoutFriendlyURLSeparatorComposite.getLayout();

			request.setAttribute(WebKeys.LAYOUT, layout);

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
						layout.getFriendlyURL(locale)) ||
					(alternativeSiteFriendlyURL != null)) {

					Locale originalLocale = setAlternativeLayoutFriendlyURL(
						request, layout,
						layoutFriendlyURLSeparatorCompositeFriendlyURL,
						alternativeSiteFriendlyURL);

					String redirect = portal.getLocalizedFriendlyURL(
						request, layout, locale, originalLocale);

					Boolean forcePermanentRedirect = Boolean.TRUE;

					if (Validator.isNull(i18nLanguageId)) {
						forcePermanentRedirect = Boolean.FALSE;
					}

					return new Redirect(
						redirect, Boolean.TRUE, forcePermanentRedirect);
				}
			}
		}
		catch (NoSuchLayoutException nsle) {
			List<Layout> layouts = layoutLocalService.getLayouts(
				group.getGroupId(), _private,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			for (Layout layout : layouts) {
				if (layout.matches(request, friendlyURL)) {
					String redirect = portal.getLayoutActualURL(
						layout, Portal.PATH_MAIN);

					return new Redirect(redirect);
				}
			}

			throw nsle;
		}

		String actualURL = portal.getActualURL(
			group.getGroupId(), _private, Portal.PATH_MAIN, friendlyURL, params,
			requestContext);

		return new Redirect(actualURL);
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		_private = GetterUtil.getBoolean(
			servletConfig.getInitParameter("servlet.init.private"));

		String proxyPath = portal.getPathProxy();

		_user = GetterUtil.getBoolean(
			servletConfig.getInitParameter("servlet.init.user"));

		if (_private) {
			if (_user) {
				_friendlyURLPathPrefix = portal.getPathFriendlyURLPrivateUser();
			}
			else {
				_friendlyURLPathPrefix =
					portal.getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			_friendlyURLPathPrefix = portal.getPathFriendlyURLPublic();
		}

		_pathInfoOffset = _friendlyURLPathPrefix.length() - proxyPath.length();
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		// Do not set the entire full main path. See LEP-456.

		String pathInfo = getPathInfo(request);

		Redirect redirect = null;

		try {
			redirect = getRedirect(request, pathInfo);

			if (request.getAttribute(WebKeys.LAST_PATH) == null) {
				request.setAttribute(
					WebKeys.LAST_PATH, getLastPath(request, pathInfo));
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}

			if (pe instanceof NoSuchGroupException ||
				pe instanceof NoSuchLayoutException) {

				portal.sendError(
					HttpServletResponse.SC_NOT_FOUND, pe, request, response);

				return;
			}
		}

		if (redirect == null) {
			redirect = new Redirect();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect.getPath());
		}

		if (redirect.isValidForward()) {
			ServletContext servletContext = getServletContext();

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect.getPath());

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else {
			if (redirect.isPermanent()) {
				response.setHeader("Location", redirect.getPath());
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			}
			else {
				response.sendRedirect(redirect.getPath());
			}
		}
	}

	public static class Redirect {

		public Redirect() {
			this(Portal.PATH_MAIN);
		}

		public Redirect(String path) {
			this(path, false, false);
		}

		public Redirect(String path, boolean force, boolean permanent) {
			_path = path;
			_force = force;
			_permanent = permanent;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof Redirect)) {
				return false;
			}

			Redirect redirect = (Redirect)obj;

			if (Objects.equals(getPath(), redirect.getPath()) &&
				(isForce() == redirect.isForce()) &&
				(isPermanent() == redirect.isPermanent())) {

				return true;
			}
			else {
				return false;
			}
		}

		public String getPath() {
			if (Validator.isNull(_path)) {
				return Portal.PATH_MAIN;
			}

			return _path;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _path);

			hash = HashUtil.hash(hash, _force);
			hash = HashUtil.hash(hash, _permanent);

			return hash;
		}

		public boolean isForce() {
			return _force;
		}

		public boolean isPermanent() {
			return _permanent;
		}

		public boolean isValidForward() {
			String path = getPath();

			if (path.charAt(0) != CharPool.SLASH) {
				return false;
			}

			if (isForce()) {
				return false;
			}

			return true;
		}

		private final boolean _force;
		private final String _path;
		private final boolean _permanent;

	}

	protected LastPath getLastPath(
		HttpServletRequest request, String pathInfo) {

		String lifecycle = ParamUtil.getString(request, "p_p_lifecycle");

		if (lifecycle.equals("1")) {
			return new LastPath(_friendlyURLPathPrefix, pathInfo);
		}
		else {
			return new LastPath(
				_friendlyURLPathPrefix, pathInfo,
				HttpUtil.parameterMapToString(request.getParameterMap()));
		}
	}

	protected String getPathInfo(HttpServletRequest request) {
		String requestURI = request.getRequestURI();

		int pos = requestURI.indexOf(Portal.JSESSIONID);

		if (pos == -1) {
			return requestURI.substring(_pathInfoOffset);
		}

		return requestURI.substring(_pathInfoOffset, pos);
	}

	protected Locale setAlternativeLayoutFriendlyURL(
		HttpServletRequest request, Layout layout, String friendlyURL,
		SiteFriendlyURL siteFriendlyURL) {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			layoutFriendlyURLLocalService.getLayoutFriendlyURLs(
				layout.getPlid(), friendlyURL, 0, 1);

		if (layoutFriendlyURLs.isEmpty()) {
			return null;
		}

		LayoutFriendlyURL layoutFriendlyURL = layoutFriendlyURLs.get(0);

		Locale locale = LocaleUtil.fromLanguageId(
			layoutFriendlyURL.getLanguageId());

		Locale groupLocale = locale;

		if (siteFriendlyURL != null) {
			groupLocale = LocaleUtil.fromLanguageId(
				siteFriendlyURL.getLanguageId());
		}

		String alternativeLayoutFriendlyURL = portal.getLocalizedFriendlyURL(
			request, layout, groupLocale, locale);

		SessionMessages.add(
			request, "alternativeLayoutFriendlyURL",
			alternativeLayoutFriendlyURL);

		PortalMessages.add(
			request, PortalMessages.KEY_JSP_PATH,
			"/html/common/themes/layout_friendly_url_redirect.jsp");

		if (!locale.equals(groupLocale)) {
			locale = groupLocale;
		}

		return locale;
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected LayoutFriendlyURLLocalService layoutFriendlyURLLocalService;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected SiteFriendlyURLLocalService siteFriendlyURLLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLServlet.class);

	private String _friendlyURLPathPrefix;
	private int _pathInfoOffset;
	private boolean _private;
	private boolean _user;

}