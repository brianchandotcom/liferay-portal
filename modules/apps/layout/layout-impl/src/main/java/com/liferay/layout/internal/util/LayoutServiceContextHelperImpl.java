/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.util;

import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ConcurrentHashMapBuilder;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.PropsValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.Principal;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = LayoutServiceContextHelper.class)
public class LayoutServiceContextHelperImpl
	implements LayoutServiceContextHelper {

	@Override
	public AutoCloseable getServiceContextAutoCloseable(Company company)
		throws PortalException {

		return new ServiceContextTemporarySwapper(company);
	}

	@Override
	public AutoCloseable getServiceContextAutoCloseable(Layout layout)
		throws PortalException {

		return new ServiceContextTemporarySwapper(
			_companyLocalService.getCompany(layout.getCompanyId()), layout);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutServiceContextHelperImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

	private class MockHttpServletRequest implements HttpServletRequest {

		@Override
		public boolean authenticate(HttpServletResponse httpServletResponse)
			throws IOException, ServletException {

			return false;
		}

		@Override
		public String changeSessionId() {
			return null;
		}

		@Override
		public AsyncContext getAsyncContext() {
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			return _attributes.get(name);
		}

		@Override
		public Enumeration<String> getAttributeNames() {
			return Collections.enumeration(_attributes.keySet());
		}

		@Override
		public String getAuthType() {
			return null;
		}

		@Override
		public String getCharacterEncoding() {
			return null;
		}

		@Override
		public int getContentLength() {
			return 0;
		}

		@Override
		public long getContentLengthLong() {
			return 0;
		}

		@Override
		public String getContentType() {
			return null;
		}

		@Override
		public String getContextPath() {
			return null;
		}

		@Override
		public Cookie[] getCookies() {
			return new Cookie[0];
		}

		@Override
		public long getDateHeader(String name) {
			return 0;
		}

		@Override
		public DispatcherType getDispatcherType() {
			return null;
		}

		@Override
		public String getHeader(String name) {
			return null;
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			return Collections.emptyEnumeration();
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			return null;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			return null;
		}

		@Override
		public int getIntHeader(String name) {
			return 0;
		}

		@Override
		public String getLocalAddr() {
			return null;
		}

		@Override
		public Locale getLocale() {
			return null;
		}

		@Override
		public Enumeration<Locale> getLocales() {
			return null;
		}

		@Override
		public String getLocalName() {
			return null;
		}

		@Override
		public int getLocalPort() {
			return 0;
		}

		@Override
		public String getMethod() {
			return HttpMethods.GET;
		}

		@Override
		public String getParameter(String name) {
			return null;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return Collections.emptyMap();
		}

		@Override
		public Enumeration<String> getParameterNames() {
			return null;
		}

		@Override
		public String[] getParameterValues(String name) {
			return new String[0];
		}

		@Override
		public Part getPart(String name) throws IOException, ServletException {
			return null;
		}

		@Override
		public Collection<Part> getParts()
			throws IOException, ServletException {

			return null;
		}

		@Override
		public String getPathInfo() {
			return null;
		}

		@Override
		public String getPathTranslated() {
			return null;
		}

		@Override
		public String getProtocol() {
			return null;
		}

		@Override
		public String getQueryString() {
			return null;
		}

		@Override
		public BufferedReader getReader() throws IOException {
			return null;
		}

		@Override
		public String getRealPath(String path) {
			return null;
		}

		@Override
		public String getRemoteAddr() {
			return null;
		}

		@Override
		public String getRemoteHost() {
			return null;
		}

		@Override
		public int getRemotePort() {
			return 0;
		}

		@Override
		public String getRemoteUser() {
			return null;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			return DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				ServletContextPool.get(_portal.getServletContextName()), path);
		}

		@Override
		public String getRequestedSessionId() {
			return null;
		}

		@Override
		public String getRequestURI() {
			return StringPool.BLANK;
		}

		@Override
		public StringBuffer getRequestURL() {
			return null;
		}

		@Override
		public String getScheme() {
			return null;
		}

		@Override
		public String getServerName() {
			return null;
		}

		@Override
		public int getServerPort() {
			return 0;
		}

		@Override
		public ServletContext getServletContext() {
			return ServletContextPool.get(_portal.getServletContextName());
		}

		@Override
		public String getServletPath() {
			return null;
		}

		@Override
		public HttpSession getSession() {
			return _httpSession;
		}

		@Override
		public HttpSession getSession(boolean create) {
			return _httpSession;
		}

		@Override
		public Principal getUserPrincipal() {
			return null;
		}

		@Override
		public boolean isAsyncStarted() {
			return false;
		}

		@Override
		public boolean isAsyncSupported() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromCookie() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromUrl() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromURL() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdValid() {
			return false;
		}

		@Override
		public boolean isSecure() {
			return false;
		}

		@Override
		public boolean isUserInRole(String role) {
			return false;
		}

		@Override
		public void login(String userName, String password)
			throws ServletException {
		}

		@Override
		public void logout() throws ServletException {
		}

		@Override
		public void removeAttribute(String name) {
			_attributes.remove(name);
		}

		@Override
		public void setAttribute(String name, Object value) {
			if ((name != null) && (value != null)) {
				_attributes.put(name, value);
			}
		}

		@Override
		public void setCharacterEncoding(String encoding)
			throws UnsupportedEncodingException {
		}

		@Override
		public AsyncContext startAsync() throws IllegalStateException {
			return null;
		}

		@Override
		public AsyncContext startAsync(
				ServletRequest servletRequest, ServletResponse servletResponse)
			throws IllegalStateException {

			return null;
		}

		@Override
		public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass)
			throws IOException, ServletException {

			return null;
		}

		private final Map<String, Object> _attributes =
			ConcurrentHashMapBuilder.<String, Object>put(
				WebKeys.CTX,
				ServletContextPool.get(_portal.getServletContextName())
			).build();

		private final HttpSession _httpSession = new HttpSession() {

			@Override
			public Object getAttribute(String name) {
				return _attributes.get(name);
			}

			@Override
			public Enumeration<String> getAttributeNames() {
				return Collections.enumeration(_attributes.keySet());
			}

			@Override
			public long getCreationTime() {
				return 0;
			}

			@Override
			public String getId() {
				return StringPool.BLANK;
			}

			@Override
			public long getLastAccessedTime() {
				return 0;
			}

			@Override
			public int getMaxInactiveInterval() {
				return 0;
			}

			@Override
			public ServletContext getServletContext() {
				return null;
			}

			@Override
			public HttpSessionContext getSessionContext() {
				return null;
			}

			@Override
			public Object getValue(String name) {
				return null;
			}

			@Override
			public String[] getValueNames() {
				return new String[0];
			}

			@Override
			public void invalidate() {
			}

			@Override
			public boolean isNew() {
				return true;
			}

			@Override
			public void putValue(String name, Object value) {
			}

			@Override
			public void removeAttribute(String name) {
			}

			@Override
			public void removeValue(String name) {
			}

			@Override
			public void setAttribute(String name, Object value) {
				_attributes.put(name, value);
			}

			@Override
			public void setMaxInactiveInterval(int interval) {
			}

		};

	}

	private class ServiceContextTemporarySwapper implements AutoCloseable {

		public ServiceContextTemporarySwapper(Company company)
			throws PortalException {

			this(company, null);
		}

		public ServiceContextTemporarySwapper(Company company, Layout layout)
			throws PortalException {

			_company = company;

			_originalCompanyId = CompanyThreadLocal.getCompanyId();
			_originalPermissionChecker =
				PermissionThreadLocal.getPermissionChecker();
			_originalName = PrincipalThreadLocal.getName();

			_originalServiceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (_originalServiceContext == null) {
				_httpServletRequest = new MockHttpServletRequest();
				_httpServletResponse = new DummyHttpServletResponse();
			}
			else {
				ThemeDisplay themeDisplay =
					_originalServiceContext.getThemeDisplay();

				if (_originalServiceContext.getRequest() != null) {
					_httpServletRequest = _originalServiceContext.getRequest();
				}
				else if ((themeDisplay != null) &&
						 (themeDisplay.getRequest() != null)) {

					_httpServletRequest = themeDisplay.getRequest();
				}
				else {
					_httpServletRequest = new MockHttpServletRequest();
				}

				if (_originalServiceContext.getResponse() != null) {
					_httpServletResponse =
						_originalServiceContext.getResponse();
				}
				else if ((themeDisplay != null) &&
						 (themeDisplay.getResponse() != null)) {

					_httpServletResponse = themeDisplay.getResponse();
				}
				else {
					_httpServletResponse = new DummyHttpServletResponse();
				}
			}

			if (layout == null) {
				_group = _groupLocalService.getGroup(
					company.getCompanyId(), GroupConstants.GUEST);

				String friendlyURL =
					_friendlyURLNormalizer.normalizeWithEncoding(
						PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL);

				layout = _layoutLocalService.fetchLayoutByFriendlyURL(
					_group.getGroupId(), false, friendlyURL);
			}
			else {
				_group = _groupLocalService.getGroup(layout.getGroupId());
			}

			if (layout == null) {
				layout = _layoutLocalService.fetchFirstLayout(
					_group.getGroupId(), false,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, false);
			}

			if (layout == null) {
				layout = _layoutLocalService.fetchFirstLayout(
					_group.getGroupId(), false,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
			}

			_layout = layout;

			_user = _userLocalService.fetchGuestUser(company.getCompanyId());

			_permissionChecker = PermissionCheckerFactoryUtil.create(_user);

			_setCompanyServiceContext();
		}

		@Override
		public void close() {
			CompanyThreadLocal.setCompanyId(_originalCompanyId);
			PermissionThreadLocal.setPermissionChecker(
				_originalPermissionChecker);
			PrincipalThreadLocal.setName(_originalName);
			ServiceContextThreadLocal.pushServiceContext(
				_originalServiceContext);
		}

		private HttpServletRequest _getHttpServletRequest(
				PermissionChecker permissionChecker, User user)
			throws PortalException {

			ThemeDisplay themeDisplay = _getThemeDisplay(
				_company, permissionChecker, user);

			HttpServletRequest companyHttpServletRequest =
				new HttpServletRequestWrapper(_httpServletRequest) {

					@Override
					public Object getAttribute(String name) {
						if (Objects.equals(name, WebKeys.COMPANY_ID)) {
							return _company.getCompanyId();
						}

						if (Objects.equals(name, WebKeys.LAYOUT)) {
							return themeDisplay.getLayout();
						}

						if (Objects.equals(name, WebKeys.THEME_DISPLAY)) {
							return themeDisplay;
						}

						if (Objects.equals(name, WebKeys.USER)) {
							return user;
						}

						if (Objects.equals(name, WebKeys.USER_ID)) {
							return user.getUserId();
						}

						return super.getAttribute(name);
					}

				};

			themeDisplay.setRequest(companyHttpServletRequest);

			themeDisplay.setResponse(_httpServletResponse);

			return companyHttpServletRequest;
		}

		private ThemeDisplay _getThemeDisplay(
				Company company, PermissionChecker permissionChecker, User user)
			throws PortalException {

			ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

			themeDisplay.setCompany(company);

			if (_layout != null) {
				themeDisplay.setLanguageId(_layout.getDefaultLanguageId());
				themeDisplay.setLayout(_layout);

				LayoutSet layoutSet = _layout.getLayoutSet();

				themeDisplay.setLayoutSet(layoutSet);

				themeDisplay.setLayoutTypePortlet(
					(LayoutTypePortlet)_layout.getLayoutType());
				themeDisplay.setLocale(
					LocaleUtil.fromLanguageId(_layout.getDefaultLanguageId()));

				Theme theme = _themeLocalService.fetchTheme(
					company.getCompanyId(), layoutSet.getThemeId());

				if (theme != null) {
					themeDisplay.setLookAndFeel(
						layoutSet.getTheme(), layoutSet.getColorScheme());
				}
				else if (_log.isDebugEnabled()) {
					_log.debug(layoutSet.getThemeId() + " is not registered");
				}

				themeDisplay.setPlid(_layout.getPlid());
			}
			else {
				Locale locale = _portal.getSiteDefaultLocale(
					_group.getGroupId());

				themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
				themeDisplay.setLocale(locale);
			}

			themeDisplay.setPermissionChecker(permissionChecker);
			themeDisplay.setPortalDomain(company.getVirtualHostname());

			boolean secure = _isHttpsEnabled();

			int portalServerPort = _portal.getPortalServerPort(secure);

			themeDisplay.setPortalURL(
				_portal.getPortalURL(
					company.getVirtualHostname(), portalServerPort, secure));

			themeDisplay.setRealUser(user);
			themeDisplay.setScopeGroupId(_group.getGroupId());
			themeDisplay.setServerPort(portalServerPort);
			themeDisplay.setSiteGroupId(_group.getGroupId());
			themeDisplay.setTimeZone(user.getTimeZone());
			themeDisplay.setUser(user);

			return themeDisplay;
		}

		private boolean _isHttpsEnabled() {
			if (Objects.equals(
					Http.HTTPS,
					PropsUtil.get(PropsKeys.PORTAL_INSTANCE_PROTOCOL)) ||
				Objects.equals(
					Http.HTTPS, PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL))) {

				return true;
			}

			return false;
		}

		private void _setCompanyServiceContext() throws PortalException {
			CompanyThreadLocal.setCompanyId(_company.getCompanyId());

			PermissionThreadLocal.setPermissionChecker(_permissionChecker);

			PrincipalThreadLocal.setName(_user.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(_company.getCompanyId());
			serviceContext.setRequest(
				_getHttpServletRequest(_permissionChecker, _user));
			serviceContext.setUserId(_user.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		private final Company _company;
		private final Group _group;
		private final HttpServletRequest _httpServletRequest;
		private final HttpServletResponse _httpServletResponse;
		private final Layout _layout;
		private final long _originalCompanyId;
		private final String _originalName;
		private final PermissionChecker _originalPermissionChecker;
		private final ServiceContext _originalServiceContext;
		private final PermissionChecker _permissionChecker;
		private final User _user;

	}

}