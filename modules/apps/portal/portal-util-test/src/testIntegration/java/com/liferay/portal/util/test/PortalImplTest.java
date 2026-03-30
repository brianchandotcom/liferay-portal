/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.portlet.MockPortletRequest;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadServletRequestImpl;

import jakarta.servlet.http.HttpServletRequest;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Peter Fellwock
 */
@RunWith(Arquillian.class)
public class PortalImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetLayoutFriendlyURLWithPublicServletMappingDisabled()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING_ENABLED",
					false)) {

			Company company = _companyLocalService.getCompany(
				TestPropsValues.getCompanyId());

			Group group = _groupLocalService.fetchGroup(
				company.getCompanyId(), GroupConstants.GUEST);

			Layout layout = _layoutLocalService.fetchDefaultLayout(
				group.getGroupId(), false);

			_assertLayoutFriendlyURL(
				company, group, layout, "localhost",
				group.getFriendlyURL() + layout.getFriendlyURL());
		}
	}

	@Test
	public void testGetLayoutFriendlyURLWithPublicServletMappingDisabledAndCompanyVirtualHost()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING_ENABLED",
					false)) {

			Company company = _companyLocalService.getCompany(
				TestPropsValues.getCompanyId());

			Group group = _groupLocalService.fetchGroup(
				company.getCompanyId(), GroupConstants.GUEST);

			Layout layout = _layoutLocalService.fetchDefaultLayout(
				group.getGroupId(), false);

			_assertLayoutFriendlyURL(
				company, group, layout, company.getVirtualHostname(),
				group.getFriendlyURL() + layout.getFriendlyURL());
		}
	}

	@Test
	public void testGetLayoutFriendlyURLWithPublicServletMappingDisabledAndLayoutSetVirtualHost()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING_ENABLED",
					false)) {

			Company company = CompanyTestUtil.addCompany();

			try {
				Group group = _groupLocalService.fetchGroup(
					company.getCompanyId(), GroupConstants.GUEST);

				Layout layout = _layoutLocalService.fetchDefaultLayout(
					group.getGroupId(), false);

				String hostname = _setLayoutSetVirtualHost(company, group);

				_assertLayoutFriendlyURL(
					company, group, layout, hostname, layout.getFriendlyURL());
			}
			finally {
				_companyLocalService.deleteCompany(company);
			}
		}
	}

	@Test
	public void testGetPortletTitleFromPortletRequestWithDeployedPortletId()
		throws Exception {

		Assert.assertEquals(
			"Server Administration",
			_portal.getPortletTitle(
				_mockPortletRequest(PortletKeys.SERVER_ADMIN)));
	}

	@Test
	public void testGetPortletTitleFromPortletRequestWithUndeployedPortletId()
		throws Exception {

		String portletId = "TEST_PORTLET_" + RandomTestUtil.randomString();

		Assert.assertEquals(
			portletId, _portal.getPortletTitle(_mockPortletRequest(portletId)));
	}

	@Test
	public void testGetPortletTitleWithDeployedPortletId() {
		String portletId = PortletKeys.SERVER_ADMIN;

		Assert.assertEquals(
			"Server Administration",
			_portal.getPortletTitle(portletId, LocaleUtil.US));
	}

	@Test
	public void testGetPortletTitleWithUndeployedPortletId() {
		String portletId = "TEST_PORTLET_" + RandomTestUtil.randomString();

		Assert.assertEquals(
			portletId, _portal.getPortletTitle(portletId, LocaleUtil.US));
	}

	@Test
	public void testGetUploadPortletRequestWithInvalidHttpServletRequest() {
		try {
			_portal.getUploadPortletRequest(new MockPortletRequest());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(exception instanceof RuntimeException);
			Assert.assertEquals(
				"Unable to unwrap the portlet request from " +
					MockPortletRequest.class,
				exception.getMessage());
		}
	}

	@Test
	public void testGetUploadPortletRequestWithValidHttpServletRequest()
		throws Exception {

		Class<?> clazz = getClass();

		try (InputStream inputStream = clazz.getResourceAsStream(
				"/com/liferay/portal/util/test/dependencies/test.txt")) {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					"fileParameterName", _file.getBytes(inputStream));

			UploadServletRequest uploadServletRequest =
				_portal.getUploadServletRequest(
					(HttpServletRequest)liferayServletRequest.getRequest());

			Assert.assertTrue(
				uploadServletRequest instanceof UploadServletRequestImpl);
		}
	}

	private void _assertLayoutFriendlyURL(
			Company company, Group group, Layout layout, String virtualHostname,
			String expectedURL)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(company);
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setPortalDomain(virtualHostname);
		themeDisplay.setServerName(virtualHostname);
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		Assert.assertEquals(
			expectedURL, _portal.getLayoutFriendlyURL(themeDisplay));
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		_group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());

		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setPortalURL("http://localhost:8080");
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private MockPortletRequest _mockPortletRequest(String portletId)
		throws Exception {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					setAttribute(WebKeys.CTX, getServletContext());
					setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
				}
			};

		return new MockPortletRequest() {
			{
				setAttribute(WebKeys.PORTLET_ID, portletId);
				setAttribute(
					PortletServlet.PORTLET_SERVLET_REQUEST,
					mockHttpServletRequest);
				setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
			}
		};
	}

	private String _setLayoutSetVirtualHost(Company company, Group group) {
		LayoutSet layoutSet = group.getPublicLayoutSet();

		String hostname =
			RandomTestUtil.randomString() + "." +
				RandomTestUtil.randomString(3);

		_virtualHostLocalService.updateVirtualHosts(
			company.getCompanyId(), layoutSet.getLayoutSetId(),
			TreeMapBuilder.put(
				hostname, StringPool.BLANK
			).build());

		return hostname;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private File _file;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Language _language;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}