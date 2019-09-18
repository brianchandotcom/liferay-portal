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

package com.liferay.friendly.url.internal.servlet;

import com.liferay.petra.encryptor.Encryptor;
import com.liferay.petra.encryptor.EncryptorException;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
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
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.AsyncPortletServletRequest;
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
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Shuyang Zhou
 * @author Marco Leo
 */
public class FriendlyURLServlet extends HttpServlet {

	public Redirect getRedirect(
			HttpServletRequest httpServletRequest, String path)
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

		long companyId = PortalInstances.getCompanyId(httpServletRequest);

		Group group = groupLocalService.fetchFriendlyURLGroup(
			companyId, friendlyURL);

		if (group == null) {
			String screenName = friendlyURL.substring(1);

			User user = userLocalService.fetchUserByScreenName(
				companyId, screenName);

			if (user != null) {
				group = user.getGroup();
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("No user exists with friendly URL " + screenName);
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

		Locale locale = portal.getLocale(httpServletRequest, null, false);

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
			httpServletRequest.setAttribute(
				WebKeys.REDIRECT_TO_DEFAULT_LAYOUT, Boolean.TRUE);
		}

		Map<String, Object> requestContext = new HashMap<>();

		requestContext.put("request", httpServletRequest);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		Map<String, String[]> params = httpServletRequest.getParameterMap();

		try {
			LayoutFriendlyURLSeparatorComposite
				layoutFriendlyURLSeparatorComposite =
					portal.getLayoutFriendlyURLSeparatorComposite(
						group.getGroupId(), _private, friendlyURL, params,
						requestContext);

			Layout layout = layoutFriendlyURLSeparatorComposite.getLayout();

			httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

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

				String i18nLanguageId = (String)httpServletRequest.getAttribute(
					WebKeys.I18N_LANGUAGE_ID);

				boolean localeUnavailable = false;

				if (Validator.isNotNull(i18nLanguageId) &&
					!LanguageUtil.isAvailableLocale(
						group.getGroupId(), i18nLanguageId)) {

					localeUnavailable = true;
				}

				if (localeUnavailable || (alternativeSiteFriendlyURL != null) ||
					!_equalsLayoutFriendlyURL(
						layoutFriendlyURLSeparatorCompositeFriendlyURL, layout,
						locale)) {

					Locale originalLocale = setAlternativeLayoutFriendlyURL(
						httpServletRequest, layout,
						layoutFriendlyURLSeparatorCompositeFriendlyURL,
						alternativeSiteFriendlyURL);

					if (localeUnavailable &&
						PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE) {

						locale = LocaleUtil.fromLanguageId(
							group.getDefaultLanguageId());
					}

					String redirect = portal.getLocalizedFriendlyURL(
						httpServletRequest, layout, locale, originalLocale);

					boolean forcePermanentRedirect = true;

					if (Validator.isNull(i18nLanguageId)) {
						forcePermanentRedirect = false;
					}

					return new Redirect(redirect, true, forcePermanentRedirect);
				}
			}
		}
		catch (NoSuchLayoutException nsle) {
			List<Layout> layouts = layoutLocalService.getLayouts(
				group.getGroupId(), _private,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			for (Layout layout : layouts) {
				if (layout.matches(httpServletRequest, friendlyURL)) {
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
		String portalURL = portal.getPortalURL(httpServletRequest);

		if (actualURL.startsWith(portalURL)) {
			actualURL = StringUtil.removeSubstring(actualURL, portalURL);
		}

		long userId = portal.getUserId(httpServletRequest);

		if ((userId > 0) && _isImpersonated(httpServletRequest, userId)) {
			try {
				Company company = portal.getCompany(httpServletRequest);

				String encDoAsUserId = Encryptor.encrypt(
					company.getKeyObj(), String.valueOf(userId));

				actualURL = HttpUtil.setParameter(
					actualURL, "doAsUserId", encDoAsUserId);
			}
			catch (EncryptorException ee) {
				return new Redirect(actualURL);
			}
		}

		return new Redirect(actualURL);
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ServletContext servletContext = servletConfig.getServletContext();

		if (servletContext != ServletContextPool.get(
				portal.getServletContextName())) {

			return;
		}

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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		// Do not set the entire full main path. See LEP-456.

		String pathInfo = getPathInfo(httpServletRequest);

		Redirect redirect = null;

		try {
			redirect = getRedirect(httpServletRequest, pathInfo);

			if (httpServletRequest.getAttribute(WebKeys.LAST_PATH) == null) {
				httpServletRequest.setAttribute(
					WebKeys.LAST_PATH,
					getLastPath(httpServletRequest, pathInfo));
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			if (pe instanceof NoSuchGroupException ||
				pe instanceof NoSuchLayoutException) {

				portal.sendError(
					HttpServletResponse.SC_NOT_FOUND, pe, httpServletRequest,
					httpServletResponse);

				return;
			}
		}

		if (redirect == null) {
			redirect = new Redirect();
		}

		if (redirect.isValidForward()) {
			ServletContext servletContext = getServletContext();

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect.getPath());

			if (httpServletRequest.isAsyncSupported()) {
				AsyncPortletServletRequest asyncPortletServletRequest =
					AsyncPortletServletRequest.getAsyncPortletServletRequest(
						httpServletRequest);

				if (asyncPortletServletRequest != null) {
					asyncPortletServletRequest.update(
						servletContext.getContextPath(), redirect.getPath());
				}
			}

			if (requestDispatcher != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Forward from ", httpServletRequest.getRequestURI(),
							" to ", redirect.getPath()));
				}

				requestDispatcher.forward(
					httpServletRequest, httpServletResponse);
			}
		}
		else {
			if (redirect.isPermanent()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Location moved permanently from ",
							httpServletRequest.getRequestURI(), " to ",
							redirect.getPath()));
				}

				httpServletResponse.setHeader("Location", redirect.getPath());
				httpServletResponse.setStatus(
					HttpServletResponse.SC_MOVED_PERMANENTLY);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Redirect from ",
							httpServletRequest.getRequestURI(), " to ",
							redirect.getPath()));
				}

				httpServletResponse.sendRedirect(redirect.getPath());
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

			return false;
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
			if (isForce()) {
				return false;
			}

			String path = getPath();

			if (path.equals(Portal.PATH_MAIN) || path.startsWith("/c/")) {
				return true;
			}

			return false;
		}

		private final boolean _force;
		private final String _path;
		private final boolean _permanent;

	}

	protected LastPath getLastPath(
		HttpServletRequest httpServletRequest, String pathInfo) {

		String lifecycle = ParamUtil.getString(
			httpServletRequest, "p_p_lifecycle");

		if (lifecycle.equals("1")) {
			return new LastPath(_friendlyURLPathPrefix, pathInfo);
		}

		return new LastPath(
			_friendlyURLPathPrefix, pathInfo,
			HttpUtil.parameterMapToString(
				httpServletRequest.getParameterMap()));
	}

	protected String getPathInfo(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		int pos = requestURI.indexOf(Portal.JSESSIONID);

		if (pos == -1) {
			return requestURI.substring(_pathInfoOffset);
		}

		return requestURI.substring(_pathInfoOffset, pos);
	}

	protected Locale setAlternativeLayoutFriendlyURL(
		HttpServletRequest httpServletRequest, Layout layout,
		String friendlyURL, SiteFriendlyURL siteFriendlyURL) {

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
			httpServletRequest, layout, groupLocale, locale);

		SessionMessages.add(
			httpServletRequest, "alternativeLayoutFriendlyURL",
			alternativeLayoutFriendlyURL);

		PortalMessages.add(
			httpServletRequest, PortalMessages.KEY_JSP_PATH,
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

	private boolean _equalsLayoutFriendlyURL(
		String layoutFriendlyURLSeparatorCompositeFriendlyURL, Layout layout,
		Locale locale) {

		String layoutFriendlyURL = layout.getFriendlyURL(locale);

		if (StringUtil.equalsIgnoreCase(
				layoutFriendlyURLSeparatorCompositeFriendlyURL,
				layoutFriendlyURL) ||
			StringUtil.equalsIgnoreCase(
				FriendlyURLNormalizerUtil.normalizeWithEncoding(
					layoutFriendlyURLSeparatorCompositeFriendlyURL),
				layoutFriendlyURL)) {

			return true;
		}

		return false;
	}

	private boolean _isImpersonated(
		HttpServletRequest httpServletRequest, long userId) {

		HttpSession session = httpServletRequest.getSession();

		Long realUserId = (Long)session.getAttribute(WebKeys.USER_ID);

		if ((realUserId == null) || (userId == realUserId)) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLServlet.class);

	private String _friendlyURLPathPrefix;
	private int _pathInfoOffset;
	private boolean _private;
	private boolean _user;

}