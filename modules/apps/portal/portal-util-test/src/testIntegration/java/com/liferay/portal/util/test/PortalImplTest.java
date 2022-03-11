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

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletRequest;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

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

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		themeDisplay.setCompany(company);

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

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private File _file;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

}