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

package com.liferay.segments.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Stefan Tanasie
 */
@RunWith(Arquillian.class)
public class SegmentsCompanyConfigurationDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws InvalidSyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(
			SegmentsCompanyConfigurationDisplayContextTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<?>[] serviceReferences =
			bundleContext.getServiceReferences(
				"com.liferay.segments.web.internal.configuration.display." +
					"SegmentsCompanyConfigurationScreen",
				"(component.name=com.liferay.segments.web.internal." +
					"configuration.display." +
						"SegmentsCompanyConfigurationScreen)");

		if (ArrayUtil.isEmpty(serviceReferences)) {
			throw new IllegalStateException(
				"Unable to get reference to " +
					"SegmentsCompanyConfigurationScreen");
		}

		_configurationScreen = (ConfigurationScreen)bundleContext.getService(
			serviceReferences[0]);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testGetBindConfigurationActionURL() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		_configurationScreen.render(
			mockHttpServletRequest, new MockHttpServletResponse());

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest(mockHttpServletRequest);

		Object displayContext = mockLiferayPortletRenderRequest.getAttribute(
			"com.liferay.segments.web.internal.display.context." +
				"SegmentsCompanyConfigurationDisplayContext");

		String bindActionURL = ReflectionTestUtil.invoke(
			displayContext, "getBindConfigurationActionURL", new Class<?>[0],
			null);

		Assert.assertTrue(
			bindActionURL.contains(
				"/instance_settings/bind_segments_company_configuration"));
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletRenderResponse());

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_layout.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private static ConfigurationScreen _configurationScreen;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

}