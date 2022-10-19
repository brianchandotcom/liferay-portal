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

import com.liferay.friendly.url.internal.configuration.FriendlyURLRedirectionConfiguration;
import com.liferay.friendly.url.internal.configuration.admin.service.FriendlyURLRedirectionManagedServiceFactory;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.encryptor.EncryptorException;
import com.liferay.portal.kernel.exception.LayoutPermissionException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.login.AuthLoginGroupSettingsUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.portlet.LayoutFriendlyURLSeparatorComposite;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.InactiveRequestHandler;
import com.liferay.portal.kernel.servlet.PortalMessages;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.AsyncPortletServletRequest;
import com.liferay.redirect.provider.RedirectProvider;
import com.liferay.redirect.tracker.RedirectNotFoundTracker;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.service.SiteFriendlyURLLocalService;

import java.io.IOException;

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
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Shuyang Zhou
 * @author Marco Leo
 */
public class FriendlyURLServlet extends HttpServlet {

	public Redirect getRedirect(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String path)
		throws PortalException {

		if (path.length() <= 1) {
			return new Redirect();
		}

		String groupFriendlyURL = path;

		int pos = path.indexOf(CharPool.SLASH, 1);

		if (pos != -1) {
			groupFriendlyURL = path.substring(0, pos);
		}

		long companyId = PortalInstances.getCompanyId(httpServletRequest);

		Group group = _getGroup(path, groupFriendlyURL, companyId);

		Locale locale = portal.getLocale(httpServletRequest, null, false);

		SiteFriendlyURL alternativeSiteFriendlyURL =
			_getAlternativeSiteFriendlyURL(
				groupFriendlyURL, companyId, group, locale);

		String layoutFriendlyURL = null;
		Redirect redirectProviderRedirect = null;

		if ((pos != -1) && ((pos + 1) != path.length())) {
			layoutFriendlyURL = path.substring(pos);

			if (StringUtil.endsWith(layoutFriendlyURL, CharPool.SLASH)) {
				layoutFriendlyURL = layoutFriendlyURL.substring(
					0, layoutFriendlyURL.length() - 1);
			}

			redirectProviderRedirect = _getRedirectProviderRedirect(
				group.getGroupId(), httpServletRequest, layoutFriendlyURL,
				redirectProvider);

			if ((redirectProviderRedirect != null) &&
				!_isSkipRedirect(httpServletRequest)) {

				return redirectProviderRedirect;
			}
		}
		else {
			httpServletRequest.setAttribute(
				WebKeys.REDIRECT_TO_DEFAULT_LAYOUT, Boolean.TRUE);
		}

		Map<String, Object> requestContext = HashMapBuilder.<String, Object>put(
			"request", httpServletRequest
		).build();

		ServiceContextThreadLocal.pushServiceContext(
			_getServiceContext(group, httpServletRequest));

		Layout defaultLayout = null;

		Map<String, String[]> params = httpServletRequest.getParameterMap();

		try {
			LayoutFriendlyURLSeparatorComposite
				layoutFriendlyURLSeparatorComposite =
					portal.getLayoutFriendlyURLSeparatorComposite(
						group.getGroupId(), _private, layoutFriendlyURL, params,
						requestContext);

			if (layoutFriendlyURLSeparatorComposite.isRedirect()) {
				pos = path.indexOf(
					layoutFriendlyURLSeparatorComposite.getURLSeparator());

				if (pos != 1) {
					HttpServletRequest originalHttpServletRequest =
						portal.getOriginalServletRequest(httpServletRequest);

					String requestURL = HttpComponentsUtil.getRequestURL(
						originalHttpServletRequest);

					int friendlyURLPos = requestURL.indexOf(layoutFriendlyURL);

					String friendlyURL =
						layoutFriendlyURLSeparatorComposite.getFriendlyURL();

					String redirectURL =
						PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
							path.substring(0, pos) + friendlyURL;

					if (friendlyURLPos > 0) {
						redirectURL =
							requestURL.substring(0, friendlyURLPos) +
								friendlyURL;
					}

					String queryString = HttpComponentsUtil.getQueryString(
						originalHttpServletRequest);

					if (Validator.isNotNull(queryString)) {
						redirectURL += StringPool.QUESTION + queryString;
					}

					return new Redirect(
						redirectURL, true,
						_isPermanentRedirect(group.getCompanyId()));
				}
			}

			Layout layout = layoutFriendlyURLSeparatorComposite.getLayout();

			if (layout != null) {
				User user = _getUser(httpServletRequest);

				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(user);

				if (!LayoutPermissionUtil.contains(
						permissionChecker, layout, ActionKeys.VIEW)) {

					if (AuthLoginGroupSettingsUtil.isPromptEnabled(
							group.getGroupId())) {

						String redirect = portal.getLayoutActualURL(
							layout, Portal.PATH_MAIN);

						return new Redirect(redirect);
					}

					throw new LayoutPermissionException();
				}

				if (user.isDefaultUser() && layout.isSystem() &&
					Objects.equals(
						layout.getFriendlyURL(),
						PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL)) {

					throw new NoSuchLayoutException();
				}

				if ((redirectProviderRedirect != null) &&
					!LayoutPermissionUtil.containsLayoutUpdatePermission(
						permissionChecker, layout)) {

					return redirectProviderRedirect;
				}
			}

			defaultLayout = layout;

			httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

			if (Objects.equals(
					httpServletRequest.getRequestURI(),
					PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND)) {

				httpServletRequest.removeAttribute(
					NoSuchLayoutException.class.getName());
			}

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
						group.getGroupId(), i18nLanguageId) &&
					(!portal.isGroupControlPanelPath(path) ||
					 !LanguageUtil.isAvailableLocale(i18nLanguageId))) {

					localeUnavailable = true;
				}

				if (localeUnavailable || (alternativeSiteFriendlyURL != null) ||
					!_equalsLayoutFriendlyURL(
						layoutFriendlyURLSeparatorCompositeFriendlyURL, layout,
						locale)) {

					Locale originalLocale = _setAlternativeLayoutFriendlyURL(
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

					HttpServletRequest originalHttpServletRequest =
						portal.getOriginalServletRequest(httpServletRequest);

					if (redirect.equals(
							originalHttpServletRequest.getRequestURI())) {

						throw new NoSuchLayoutException();
					}

					boolean forcePermanentRedirect = true;

					if (Validator.isNull(i18nLanguageId)) {
						forcePermanentRedirect = _isPermanentRedirect(
							group.getCompanyId());
					}

					return new Redirect(redirect, true, forcePermanentRedirect);
				}
			}
		}
		catch (LayoutPermissionException | NoSuchLayoutException exception) {
			Layout redirectLayout = null;

			if (layoutFriendlyURL == null) {
				redirectLayout = defaultLayout;
			}
			else {
				List<Layout> layouts = layoutLocalService.getLayouts(
					group.getGroupId(), _private,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				for (Layout layout : layouts) {
					if (layout.matches(httpServletRequest, layoutFriendlyURL)) {
						redirectLayout = layout;

						break;
					}
				}
			}

			if (redirectLayout != null) {
				String redirect = portal.getLayoutActualURL(
					redirectLayout, Portal.PATH_MAIN);

				return new Redirect(redirect);
			}

			RedirectNotFoundTracker currentRedirectNotFoundTracker =
				redirectNotFoundTracker;

			if (currentRedirectNotFoundTracker != null) {
				currentRedirectNotFoundTracker.trackURL(
					group, _normalizeFriendlyURL(layoutFriendlyURL));
			}

			if (exception instanceof NoSuchLayoutException) {
				if (Validator.isNotNull(
						PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND)) {

					throw exception;
				}

				httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

				httpServletRequest.setAttribute(
					NoSuchLayoutException.class.getName(), Boolean.TRUE);
			}

			layoutFriendlyURL = null;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		String actualURL = portal.getActualURL(
			group.getGroupId(), _private, Portal.PATH_MAIN, layoutFriendlyURL,
			params, requestContext);
		String portalURL = portal.getPortalURL(httpServletRequest);

		if (actualURL.startsWith(portalURL)) {
			actualURL = StringUtil.removeSubstring(actualURL, portalURL);
		}

		long userId = portal.getUserId(httpServletRequest);

		boolean impersonated = _isImpersonated(httpServletRequest, userId);

		if ((userId > 0) && impersonated) {
			try {
				Company company = portal.getCompany(httpServletRequest);

				String encDoAsUserId = encryptor.encrypt(
					company.getKeyObj(), String.valueOf(userId));

				actualURL = HttpComponentsUtil.setParameter(
					actualURL, "doAsUserId", encDoAsUserId);
			}
			catch (EncryptorException encryptorException) {
				if (_log.isDebugEnabled()) {
					_log.debug(encryptorException);
				}

				return new Redirect(actualURL, false, false);
			}
		}

		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

		if ((layout != null) &&
			Objects.equals(layout.getType(), LayoutConstants.TYPE_URL)) {

			actualURL = actualURL.concat(
				HttpComponentsUtil.parameterMapToString(
					params, !actualURL.contains(StringPool.QUESTION)));
		}

		return new Redirect(
			actualURL, false,
			!impersonated && _isPermanentRedirect(group.getCompanyId()));
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

		String pathInfo = _getPathInfo(httpServletRequest);

		Redirect redirect = null;

		try {
			redirect = getRedirect(
				httpServletRequest, httpServletResponse, pathInfo);

			if (httpServletRequest.getAttribute(WebKeys.LAST_PATH) == null) {
				httpServletRequest.setAttribute(
					WebKeys.LAST_PATH,
					_getLastPath(httpServletRequest, pathInfo));
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			if (portalException instanceof NoSuchGroupException ||
				portalException instanceof NoSuchLayoutException) {

				portal.sendError(
					HttpServletResponse.SC_NOT_FOUND, portalException,
					httpServletRequest, httpServletResponse);

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
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof Redirect)) {
				return false;
			}

			Redirect redirect = (Redirect)object;

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

	@Reference
	protected Encryptor encryptor;

	@Reference
	protected FriendlyURLNormalizer friendlyURLNormalizer;

	@Reference
	protected FriendlyURLRedirectionManagedServiceFactory
		friendlyURLRedirectionManagedServiceFactory;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected InactiveRequestHandler inactiveRequestHandler;

	@Reference
	protected LayoutFriendlyURLLocalService layoutFriendlyURLLocalService;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected Portal portal;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile RedirectNotFoundTracker redirectNotFoundTracker;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile RedirectProvider redirectProvider;

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
				friendlyURLNormalizer.normalizeWithEncoding(
					layoutFriendlyURLSeparatorCompositeFriendlyURL),
				layoutFriendlyURL)) {

			return true;
		}

		return false;
	}

	private SiteFriendlyURL _getAlternativeSiteFriendlyURL(
		String friendlyURL, long companyId, Group group, Locale locale) {

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

		return alternativeSiteFriendlyURL;
	}

	private Group _getGroup(String path, String friendlyURL, long companyId)
		throws NoSuchGroupException {

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

		if ((group == null) ||
			(!group.isActive() &&
			 !inactiveRequestHandler.isShowInactiveRequestMessage() &&
			 !path.startsWith(GroupConstants.CONTROL_PANEL_FRIENDLY_URL) &&
			 !path.startsWith(
				 friendlyURL +
					 VirtualLayoutConstants.CANONICAL_URL_SEPARATOR))) {

			throw new NoSuchGroupException(
				StringBundler.concat(
					"{companyId=", companyId, ", friendlyURL=", friendlyURL,
					"}"));
		}

		return group;
	}

	private LastPath _getLastPath(
		HttpServletRequest httpServletRequest, String pathInfo) {

		String lifecycle = ParamUtil.getString(
			httpServletRequest, "p_p_lifecycle");

		if (lifecycle.equals("1")) {
			return new LastPath(_friendlyURLPathPrefix, pathInfo);
		}

		return new LastPath(
			_friendlyURLPathPrefix, pathInfo,
			HttpComponentsUtil.parameterMapToString(
				httpServletRequest.getParameterMap()));
	}

	private String _getPathInfo(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		int pos = requestURI.indexOf(Portal.JSESSIONID);

		if (pos == -1) {
			return requestURI.substring(_pathInfoOffset);
		}

		return requestURI.substring(_pathInfoOffset, pos);
	}

	private Redirect _getRedirectProviderRedirect(
		long groupId, HttpServletRequest httpServletRequest,
		String layoutFriendlyURL, RedirectProvider redirectProvider) {

		if ((redirectProvider == null) ||
			LiferayWindowState.isExclusive(httpServletRequest) ||
			LiferayWindowState.isPopUp(httpServletRequest)) {

			return null;
		}

		HttpServletRequest originalHttpServletRequest =
			portal.getOriginalServletRequest(httpServletRequest);

		RedirectProvider.Redirect redirect = redirectProvider.getRedirect(
			groupId, _normalizeFriendlyURL(layoutFriendlyURL),
			_normalizeFriendlyURL(originalHttpServletRequest.getRequestURI()));

		if (redirect == null) {
			return null;
		}

		return new Redirect(
			redirect.getDestinationURL(), true, redirect.isPermanent());
	}

	private ServiceContext _getServiceContext(
			Group group, HttpServletRequest httpServletRequest)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setScopeGroupId(group.getGroupId());

		return serviceContext;
	}

	private User _getUser(HttpServletRequest httpServletRequest)
		throws PortalException {

		User user = portal.getUser(httpServletRequest);

		if (user == null) {
			user = userLocalService.getDefaultUser(
				portal.getCompanyId(httpServletRequest));
		}

		return user;
	}

	private boolean _isImpersonated(
		HttpServletRequest httpServletRequest, long userId) {

		HttpSession httpSession = httpServletRequest.getSession();

		Long realUserId = (Long)httpSession.getAttribute(WebKeys.USER_ID);

		if ((realUserId == null) || (userId == realUserId)) {
			return false;
		}

		return true;
	}

	private boolean _isPermanentRedirect(long companyId) {
		FriendlyURLRedirectionConfiguration
			friendlyURLRedirectionConfiguration =
				friendlyURLRedirectionManagedServiceFactory.
					getCompanyFriendlyURLConfiguration(companyId);

		if (Objects.equals(
				friendlyURLRedirectionConfiguration.redirectionType(),
				"permanent")) {

			return true;
		}

		return false;
	}

	private boolean _isSkipRedirect(HttpServletRequest httpServletRequest) {
		String refererURL = httpServletRequest.getHeader(HttpHeaders.REFERER);

		if (Validator.isNotNull(refererURL)) {
			int index = refererURL.indexOf(CharPool.QUESTION);

			if (index != -1) {
				refererURL = refererURL.substring(0, index);
			}
		}

		if (Validator.isNotNull(refererURL)) {
			return refererURL.contains(
				VirtualLayoutConstants.CANONICAL_URL_SEPARATOR +
					GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		}

		return false;
	}

	private String _normalizeFriendlyURL(String friendlyURL) {
		if (Validator.isNull(friendlyURL)) {
			return friendlyURL;
		}

		String normalizedFriendlyURL =
			friendlyURLNormalizer.normalizeWithEncoding(
				HttpComponentsUtil.decodeURL(friendlyURL));

		if (normalizedFriendlyURL.startsWith(StringPool.SLASH)) {
			return normalizedFriendlyURL.substring(1);
		}

		return normalizedFriendlyURL;
	}

	private Locale _setAlternativeLayoutFriendlyURL(
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

		if (!LanguageUtil.isAvailableLocale(layout.getGroupId(), locale)) {
			return LocaleUtil.fromLanguageId(
				(String)httpServletRequest.getAttribute(
					WebKeys.I18N_LANGUAGE_ID));
		}

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

		return groupLocale;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLServlet.class);

	private String _friendlyURLPathPrefix;
	private int _pathInfoOffset;
	private boolean _private;
	private boolean _user;

}