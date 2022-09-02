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

package com.liferay.layout.internal.search.spi.model.index.contributor;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.layout.crawler.LayoutCrawler;
import com.liferay.layout.internal.search.util.LayoutPageTemplateStructureRenderUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ConcurrentHashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.segments.service.SegmentsExperienceLocalService;

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
import java.util.Set;

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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vagner B.C
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.Layout",
	service = ModelDocumentContributor.class
)
public class LayoutModelDocumentContributor
	implements ModelDocumentContributor<Layout> {

	public static final String CLASS_NAME = Layout.class.getName();

	@Override
	public void contribute(Document document, Layout layout) {
		if (layout.isSystem() ||
			(layout.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		document.addText(
			Field.DEFAULT_LANGUAGE_ID, layout.getDefaultLanguageId());
		document.addLocalizedText(Field.NAME, layout.getNameMap());
		document.addText(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));
		document.addKeyword(Field.STATUS, _getStatus(layout));
		document.addText(Field.TYPE, layout.getType());

		for (String languageId : layout.getAvailableLanguageIds()) {
			Locale locale = LocaleUtil.fromLanguageId(languageId);

			document.addText(
				Field.getLocalizedName(locale, Field.TITLE),
				layout.getName(locale));
		}

		if (!layout.isPublished()) {
			return;
		}

		Group group = layout.getGroup();

		if (group.isStagingGroup()) {
			return;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		if (layoutPageTemplateStructure == null) {
			return;
		}

		Set<Locale> locales = _language.getAvailableLocales(
			layout.getGroupId());

		if (!layout.isPrivateLayout()) {
			for (Locale locale : locales) {
				String content = StringPool.BLANK;

				try {
					content = _layoutCrawler.getLayoutContent(layout, locale);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to get layout content", exception);
					}
				}

				_addLocalizedContentField(content, document, locale);
			}

			return;
		}

		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext != null) && (serviceContext.getRequest() != null)) {
			httpServletRequest = DynamicServletRequest.addQueryString(
				serviceContext.getRequest(), "p_l_id=" + layout.getPlid(),
				false);

			httpServletResponse = serviceContext.getResponse();
		}

		if ((httpServletRequest == null) || (httpServletResponse == null)) {
			MockContextHelper mockContextHelper = new MockContextHelper(
				layout,
				_userLocalService.fetchDefaultUser(layout.getCompanyId()));
			long segmentsExperienceId =
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid());

			for (Locale locale : locales) {
				String content = StringPool.BLANK;

				try {
					content = _getLayoutContent(
						layoutPageTemplateStructure, locale, mockContextHelper,
						segmentsExperienceId, serviceContext);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to get layout content", exception);
					}
				}

				_addLocalizedContentField(content, document, locale);
			}

			return;
		}

		Layout originalRequestLayout = (Layout)httpServletRequest.getAttribute(
			WebKeys.LAYOUT);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout originalThemeDisplayLayout = themeDisplay.getLayout();
		long originalThemeDisplayPlid = themeDisplay.getPlid();

		try {
			httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

			themeDisplay.setLayout(layout);
			themeDisplay.setPlid(layout.getPlid());

			long segmentsExperienceId =
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid());

			for (Locale locale : locales) {
				String content = StringPool.BLANK;

				try {
					content =
						LayoutPageTemplateStructureRenderUtil.
							renderLayoutContent(
								_fragmentRendererController, httpServletRequest,
								httpServletResponse,
								layoutPageTemplateStructure,
								FragmentEntryLinkConstants.VIEW, locale,
								segmentsExperienceId);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to get layout content", exception);
					}
				}

				_addLocalizedContentField(content, document, locale);
			}
		}
		finally {
			httpServletRequest.setAttribute(
				WebKeys.LAYOUT, originalRequestLayout);

			themeDisplay.setLayout(originalThemeDisplayLayout);
			themeDisplay.setPlid(originalThemeDisplayPlid);
		}
	}

	private void _addLocalizedContentField(
		String content, Document document, Locale locale) {

		if (Validator.isNull(content)) {
			return;
		}

		content = _html.stripHtml(_getWrapper(content));

		if (Validator.isNull(content)) {
			return;
		}

		document.addText(
			Field.getLocalizedName(locale, Field.CONTENT), content);
	}

	private String _getLayoutContent(
			LayoutPageTemplateStructure layoutPageTemplateStructure,
			Locale locale, MockContextHelper mockContextHelper,
			long segmentsExperienceId, ServiceContext serviceContext)
		throws PortalException {

		long companyId = CompanyThreadLocal.getCompanyId();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			CompanyThreadLocal.setCompanyId(
				layoutPageTemplateStructure.getCompanyId());

			PermissionThreadLocal.setPermissionChecker(
				mockContextHelper.getPermissionChecker());

			HttpServletResponse httpServletResponse =
				new DummyHttpServletResponse();

			HttpServletRequest httpServletRequest =
				mockContextHelper.getHttpServletRequest(
					httpServletResponse, locale);

			ServiceContextThreadLocal.pushServiceContext(
				ServiceContextFactory.getInstance(httpServletRequest));

			return LayoutPageTemplateStructureRenderUtil.renderLayoutContent(
				_fragmentRendererController, httpServletRequest,
				httpServletResponse, layoutPageTemplateStructure,
				FragmentEntryLinkConstants.VIEW, locale, segmentsExperienceId);
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			ServiceContextThreadLocal.popServiceContext();

			if (serviceContext != null) {
				ServiceContextThreadLocal.pushServiceContext(serviceContext);
			}
		}
	}

	private int _getStatus(Layout layout) {
		if (layout.isPublished()) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		return WorkflowConstants.STATUS_DRAFT;
	}

	private String _getWrapper(String layoutContent) {
		int wrapperIndex = layoutContent.indexOf(_WRAPPER_ELEMENT);

		if (wrapperIndex == -1) {
			return layoutContent;
		}

		return layoutContent.substring(
			wrapperIndex + _WRAPPER_ELEMENT.length());
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

	private static final String _WRAPPER_ELEMENT = "id=\"wrapper\">";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutModelDocumentContributor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private LayoutCrawler _layoutCrawler;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private UserLocalService _userLocalService;

	private class MockContextHelper {

		public MockContextHelper(Layout layout, User user) {
			_layout = layout;
			_user = user;
		}

		public HttpServletRequest getHttpServletRequest(
				HttpServletResponse httpServletResponse, Locale locale)
			throws PortalException {

			HttpServletRequest httpServletRequest =
				DynamicServletRequest.addQueryString(
					new MockHttpServletRequest(), "p_l_id=" + _layout.getPlid(),
					false);

			ThemeDisplay themeDisplay = _getThemeDisplay();

			themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
			themeDisplay.setLocale(locale);
			themeDisplay.setRequest(httpServletRequest);
			themeDisplay.setResponse(httpServletResponse);

			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			httpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);
			httpServletRequest.setAttribute(WebKeys.USER, _user);
			httpServletRequest.setAttribute(WebKeys.USER_ID, _user.getUserId());

			return httpServletRequest;
		}

		public PermissionChecker getPermissionChecker() throws PortalException {
			if (_permissionChecker != null) {
				return _permissionChecker;
			}

			_permissionChecker = PermissionCheckerFactoryUtil.create(_user);

			return _permissionChecker;
		}

		private ThemeDisplay _getThemeDisplay() throws PortalException {
			if (_themeDisplay != null) {
				return _themeDisplay;
			}

			ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

			Company company = _companyLocalService.getCompany(
				_layout.getCompanyId());

			themeDisplay.setCompany(company);

			themeDisplay.setLayout(_layout);

			LayoutSet layoutSet = _layout.getLayoutSet();

			themeDisplay.setLayoutSet(layoutSet);
			themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);

			themeDisplay.setLayoutTypePortlet(
				(LayoutTypePortlet)_layout.getLayoutType());
			themeDisplay.setPermissionChecker(getPermissionChecker());
			themeDisplay.setPlid(_layout.getPlid());
			themeDisplay.setPortalDomain(company.getVirtualHostname());
			themeDisplay.setPortalURL(
				company.getPortalURL(_layout.getGroupId()));
			themeDisplay.setRealUser(_user);
			themeDisplay.setScopeGroupId(_layout.getGroupId());
			themeDisplay.setServerPort(
				_portal.getPortalServerPort(_isHttpsEnabled()));
			themeDisplay.setSiteGroupId(_layout.getGroupId());
			themeDisplay.setTimeZone(_user.getTimeZone());
			themeDisplay.setUser(_user);

			_themeDisplay = themeDisplay;

			return _themeDisplay;
		}

		private final Layout _layout;
		private PermissionChecker _permissionChecker;
		private ThemeDisplay _themeDisplay;
		private final User _user;

	}

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
				ServletContextPool.get(StringPool.BLANK), path);
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
			return ServletContextPool.get(StringPool.BLANK);
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
				WebKeys.CTX, ServletContextPool.get(StringPool.BLANK)
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

}