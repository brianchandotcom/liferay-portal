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

package com.liferay.blogs.web.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.IOException;
import java.io.Writer;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.portlet.ActionParameters;
import javax.portlet.ActionURL;
import javax.portlet.MimeResponse;
import javax.portlet.MutableRenderParameters;
import javax.portlet.MutableResourceParameters;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.RenderParameters;
import javax.portlet.RenderURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.portlet.annotations.PortletSerializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.xml.namespace.QName;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockPortletConfig;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
@Sync
public class BlogEntriesDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			"com.liferay.blogs.web.internal.display.context." +
				"BlogEntriesDisplayContextProvider");

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testGetSearchContainer() throws Exception {
		for (int i = 0; i < 25; i++) {
			_addBlogEntry("alpha_" + i);
		}

		SearchContainer searchContainer = _getSearchContainer(
			_getMockHttpServletRequest());

		Assert.assertEquals(25, searchContainer.getTotal());
	}

	@Test
	public void testGetSearchContainerWithSearch() throws Exception {
		for (int i = 0; i < 25; i++) {
			_addBlogEntry("alpha_" + i);
		}

		SearchContainer searchContainer = _getSearchContainer(
			_getMockHttpServletRequestWithSearch("alpha"));

		Assert.assertEquals(25, searchContainer.getTotal());
	}

	private BlogsEntry _addBlogEntry(String title) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		return _blogsEntryService.addEntry(
			title, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
			new String[0], RandomTestUtil.randomString(), null, null,
			serviceContext);
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private MockHttpServletRequest _getMockHttpServletRequestWithSearch(
			String keywords)
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"mvcRenderCommandName", "/blogs/view");
		mockHttpServletRequest.setParameter("keywords", keywords);

		return mockHttpServletRequest;
	}

	private SearchContainer _getSearchContainer(
		HttpServletRequest httpServletRequest) {

		Object blogEntriesDisplayContextProvider = _serviceTracker.getService();

		Object blogEntriesDisplayContext = ReflectionTestUtil.invoke(
			blogEntriesDisplayContextProvider, "getBlogEntriesDisplayContext",
			new Class<?>[] {
				LiferayPortletRequest.class, LiferayPortletResponse.class
			},
			new MockLiferayPortletRequest(httpServletRequest),
			new MockLiferayPortletresponse());

		return ReflectionTestUtil.invoke(
			blogEntriesDisplayContext, "getSearchContainer", new Class<?>[0],
			null);
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(_layout.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private static ServiceTracker<Object, Object> _serviceTracker;

	@Inject
	private BlogsEntryService _blogsEntryService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	private static class MockLiferayPortletConfig
		extends MockPortletConfig implements LiferayPortletConfig {

		@Override
		public Portlet getPortlet() {
			return null;
		}

		@Override
		public String getPortletId() {
			return "testPortlet";
		}

		@Override
		public Enumeration<PortletMode> getPortletModes(String mimeType) {
			return null;
		}

		@Override
		public Map<String, QName> getPublicRenderParameterDefinitions() {
			return null;
		}

		@Override
		public Enumeration<WindowState> getWindowStates(String mimeType) {
			return null;
		}

		@Override
		public boolean isCopyRequestParameters() {
			return false;
		}

		@Override
		public boolean isWARFile() {
			return false;
		}

	}

	private static class MockLiferayPortletRequest
		extends MockActionRequest implements LiferayPortletRequest {

		public MockLiferayPortletRequest(
			HttpServletRequest httpServletRequest) {

			_httpServletRequest = httpServletRequest;
		}

		@Override
		public void addParameter(String name, String value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void cleanUp() {
		}

		@Override
		public Map<String, String[]> clearRenderParameters() {
			return null;
		}

		@Override
		public void defineObjects(
			PortletConfig portletConfig, PortletResponse portletResponse) {
		}

		@Override
		public ActionParameters getActionParameters() {
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			if (Objects.equals(name, JavaConstants.JAVAX_PORTLET_CONFIG)) {
				return new MockLiferayPortletConfig();
			}

			return super.getAttribute(name);
		}

		@Override
		public long getContentLengthLong() {
			return 0;
		}

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return _httpServletRequest;
		}

		@Override
		public String getLifecycle() {
			return null;
		}

		@Override
		public HttpServletRequest getOriginalHttpServletRequest() {
			return _httpServletRequest;
		}

		@Override
		public Part getPart(String name) {
			return null;
		}

		@Override
		public Collection<Part> getParts() {
			return null;
		}

		@Override
		public long getPlid() {
			return 0;
		}

		@Override
		public Portlet getPortlet() {
			return null;
		}

		@Override
		public PortletContext getPortletContext() {
			return null;
		}

		@Override
		public String getPortletName() {
			return null;
		}

		@Override
		public HttpServletRequest getPortletRequestDispatcherRequest() {
			return null;
		}

		@Override
		public RenderParameters getRenderParameters() {
			return null;
		}

		@Override
		public String getUserAgent() {
			return null;
		}

		@Override
		public void invalidateSession() {
		}

		@Override
		public void setPortletRequestDispatcherRequest(
			HttpServletRequest httpServletRequest) {
		}

		private final HttpServletRequest _httpServletRequest;

	}

	private static class MockLiferayPortletresponse
		implements LiferayPortletResponse {

		@Override
		public void addDateHeader(String name, long date) {
		}

		@Override
		public void addHeader(String name, String value) {
		}

		@Override
		public void addIntHeader(String name, int value) {
		}

		@Override
		public void addProperty(Cookie cookie) {
		}

		@Override
		public void addProperty(String key, Element element) {
		}

		@Override
		public void addProperty(String key, String value) {
		}

		@Override
		public <T extends PortletURL & ActionURL> T createActionURL() {
			return null;
		}

		@Override
		public ActionURL createActionURL(MimeResponse.Copy copy) {
			return null;
		}

		@Override
		public LiferayPortletURL createActionURL(String portletName) {
			return null;
		}

		@Override
		public LiferayPortletURL createActionURL(
			String portletName, MimeResponse.Copy copy) {

			return null;
		}

		@Override
		public Element createElement(String tagName) throws DOMException {
			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			long plid, String portletName, String lifecycle) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			long plid, String portletName, String lifecycle,
			boolean includeLinkToLayoutUuid) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			long plid, String portletName, String lifecycle,
			MimeResponse.Copy copy) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			long plid, String portletName, String lifecycle,
			MimeResponse.Copy copy, boolean includeLinkToLayoutUuid) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(String lifecycle) {
			return new LiferayPortletURL() {

				@Override
				public void addParameterIncludedInPath(String name) {
				}

				@Override
				public void addProperty(String key, String value) {
				}

				@Override
				public Appendable append(Appendable out) throws IOException {
					return null;
				}

				@Override
				public Appendable append(Appendable out, boolean escapeXML)
					throws IOException {

					return null;
				}

				@Override
				public String getCacheability() {
					return null;
				}

				@Override
				public String getLifecycle() {
					return null;
				}

				@Override
				public String getParameter(String name) {
					return null;
				}

				@Override
				public Map<String, String[]> getParameterMap() {
					return null;
				}

				@Override
				public Set<String> getParametersIncludedInPath() {
					return null;
				}

				@Override
				public long getPlid() {
					return 0;
				}

				@Override
				public String getPortletId() {
					return null;
				}

				@Override
				public PortletMode getPortletMode() {
					return null;
				}

				@Override
				public Set<String> getRemovedParameterNames() {
					return null;
				}

				@Override
				public MutableRenderParameters getRenderParameters() {
					return null;
				}

				@Override
				public String getResourceID() {
					return null;
				}

				@Override
				public MutableResourceParameters getResourceParameters() {
					return null;
				}

				@Override
				public WindowState getWindowState() {
					return null;
				}

				@Override
				public boolean isAnchor() {
					return false;
				}

				@Override
				public boolean isCopyCurrentRenderParameters() {
					return false;
				}

				@Override
				public boolean isEncrypt() {
					return false;
				}

				@Override
				public boolean isEscapeXml() {
					return false;
				}

				@Override
				public boolean isParameterIncludedInPath(String name) {
					return false;
				}

				@Override
				public boolean isSecure() {
					return false;
				}

				@Override
				public void removePublicRenderParameter(String name) {
				}

				@Override
				public void setAnchor(boolean anchor) {
				}

				@Override
				public void setBeanParameter(PortletSerializable bean) {
				}

				@Override
				public void setCacheability(String cacheLevel) {
				}

				@Override
				public void setCopyCurrentRenderParameters(
					boolean copyCurrentRenderParameters) {
				}

				@Override
				public void setDoAsGroupId(long doAsGroupId) {
				}

				@Override
				public void setDoAsUserId(long doAsUserId) {
				}

				@Override
				public void setDoAsUserLanguageId(String doAsUserLanguageId) {
				}

				@Override
				public void setEncrypt(boolean encrypt) {
				}

				@Override
				public void setEscapeXml(boolean escapeXml) {
				}

				@Override
				public void setLifecycle(String lifecycle) {
				}

				@Override
				public void setParameter(String name, String value) {
				}

				@Override
				public void setParameter(String name, String... values) {
				}

				@Override
				public void setParameter(
					String name, String value, boolean append) {
				}

				@Override
				public void setParameter(
					String name, String[] values, boolean append) {
				}

				@Override
				public void setParameters(Map<String, String[]> parameters) {
				}

				@Override
				public void setPlid(long plid) {
				}

				@Override
				public void setPortletId(String portletId) {
				}

				@Override
				public void setPortletMode(PortletMode portletMode)
					throws PortletModeException {
				}

				@Override
				public void setProperty(String key, String value) {
				}

				@Override
				public void setRefererGroupId(long refererGroupId) {
				}

				@Override
				public void setRefererPlid(long refererPlid) {
				}

				@Override
				public void setRemovedParameterNames(
					Set<String> removedParamNames) {
				}

				@Override
				public void setResourceID(String resourceID) {
				}

				@Override
				public void setSecure(boolean secure)
					throws PortletSecurityException {
				}

				@Override
				public void setWindowState(WindowState windowState)
					throws WindowStateException {
				}

				@Override
				public void setWindowStateRestoreCurrentView(
					boolean windowStateRestoreCurrentView) {
				}

				@Override
				public void visitReservedParameters(
					BiConsumer<String, String> biConsumer) {
				}

				@Override
				public void write(Writer out) throws IOException {
				}

				@Override
				public void write(Writer out, boolean escapeXML)
					throws IOException {
				}

			};
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			String portletName, String lifecycle) {

			return null;
		}

		@Override
		public LiferayPortletURL createLiferayPortletURL(
			String portletName, String lifecycle, MimeResponse.Copy copy) {

			return null;
		}

		@Override
		public MockLiferayPortletURL createRenderURL() {
			return new MockLiferayPortletURL();
		}

		@Override
		public RenderURL createRenderURL(MimeResponse.Copy copy) {
			return null;
		}

		@Override
		public LiferayPortletURL createRenderURL(String portletName) {
			return null;
		}

		@Override
		public LiferayPortletURL createRenderURL(
			String portletName, MimeResponse.Copy copy) {

			return null;
		}

		@Override
		public ResourceURL createResourceURL() {
			return null;
		}

		@Override
		public LiferayPortletURL createResourceURL(String portletName) {
			return null;
		}

		@Override
		public String encodeURL(String path) {
			return null;
		}

		@Override
		public HttpServletResponse getHttpServletResponse() {
			return null;
		}

		@Override
		public String getLifecycle() {
			return null;
		}

		@Override
		public String getNamespace() {
			return null;
		}

		@Override
		public Portlet getPortlet() {
			return null;
		}

		@Override
		public Map<String, String[]> getProperties() {
			return null;
		}

		@Override
		public String getProperty(String key) {
			return null;
		}

		@Override
		public Collection<String> getPropertyNames() {
			return null;
		}

		@Override
		public Collection<String> getPropertyValues(String name) {
			return null;
		}

		@Override
		public void setDateHeader(String name, long date) {
		}

		@Override
		public void setHeader(String name, String value) {
		}

		@Override
		public void setIntHeader(String name, int value) {
		}

		@Override
		public void setProperty(String key, String value) {
		}

		@Override
		public void setURLEncoder(URLEncoder urlEncoder) {
		}

		@Override
		public void transferHeaders(HttpServletResponse response) {
		}

		@Override
		public void transferMarkupHeadElements() {
		}

	}

	private static class MockLiferayPortletURL implements LiferayPortletURL {

		@Override
		public void addParameterIncludedInPath(String name) {
		}

		@Override
		public void addProperty(String key, String value) {
		}

		@Override
		public Appendable append(Appendable out) throws IOException {
			return null;
		}

		@Override
		public Appendable append(Appendable out, boolean escapeXML)
			throws IOException {

			return null;
		}

		@Override
		public String getCacheability() {
			return null;
		}

		@Override
		public String getLifecycle() {
			return null;
		}

		@Override
		public String getParameter(String name) {
			return null;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return null;
		}

		@Override
		public Set<String> getParametersIncludedInPath() {
			return null;
		}

		@Override
		public long getPlid() {
			return 0;
		}

		@Override
		public String getPortletId() {
			return null;
		}

		@Override
		public PortletMode getPortletMode() {
			return null;
		}

		@Override
		public Set<String> getRemovedParameterNames() {
			return null;
		}

		@Override
		public MutableRenderParameters getRenderParameters() {
			return null;
		}

		@Override
		public String getResourceID() {
			return null;
		}

		@Override
		public MutableResourceParameters getResourceParameters() {
			return null;
		}

		@Override
		public WindowState getWindowState() {
			return null;
		}

		@Override
		public boolean isAnchor() {
			return false;
		}

		@Override
		public boolean isCopyCurrentRenderParameters() {
			return false;
		}

		@Override
		public boolean isEncrypt() {
			return false;
		}

		@Override
		public boolean isEscapeXml() {
			return false;
		}

		@Override
		public boolean isParameterIncludedInPath(String name) {
			return false;
		}

		@Override
		public boolean isSecure() {
			return false;
		}

		@Override
		public void removePublicRenderParameter(String name) {
		}

		@Override
		public void setAnchor(boolean anchor) {
		}

		@Override
		public void setBeanParameter(PortletSerializable bean) {
		}

		@Override
		public void setCacheability(String cacheLevel) {
		}

		@Override
		public void setCopyCurrentRenderParameters(
			boolean copyCurrentRenderParameters) {
		}

		@Override
		public void setDoAsGroupId(long doAsGroupId) {
		}

		@Override
		public void setDoAsUserId(long doAsUserId) {
		}

		@Override
		public void setDoAsUserLanguageId(String doAsUserLanguageId) {
		}

		@Override
		public void setEncrypt(boolean encrypt) {
		}

		@Override
		public void setEscapeXml(boolean escapeXml) {
		}

		@Override
		public void setLifecycle(String lifecycle) {
		}

		@Override
		public void setParameter(String name, String value) {
		}

		@Override
		public void setParameter(String name, String... values) {
		}

		@Override
		public void setParameter(String name, String value, boolean append) {
		}

		@Override
		public void setParameter(String name, String[] values, boolean append) {
		}

		@Override
		public void setParameters(Map<String, String[]> parameters) {
		}

		@Override
		public void setPlid(long plid) {
		}

		@Override
		public void setPortletId(String portletId) {
		}

		@Override
		public void setPortletMode(PortletMode portletMode)
			throws PortletModeException {
		}

		@Override
		public void setProperty(String key, String value) {
		}

		@Override
		public void setRefererGroupId(long refererGroupId) {
		}

		@Override
		public void setRefererPlid(long refererPlid) {
		}

		@Override
		public void setRemovedParameterNames(Set<String> removedParamNames) {
		}

		@Override
		public void setResourceID(String resourceID) {
		}

		@Override
		public void setSecure(boolean secure) throws PortletSecurityException {
		}

		@Override
		public void setWindowState(WindowState windowState)
			throws WindowStateException {
		}

		@Override
		public void setWindowStateRestoreCurrentView(
			boolean windowStateRestoreCurrentView) {
		}

		@Override
		public void visitReservedParameters(
			BiConsumer<String, String> biConsumer) {
		}

		@Override
		public void write(Writer out) throws IOException {
		}

		@Override
		public void write(Writer out, boolean escapeXML) throws IOException {
		}

	}

}