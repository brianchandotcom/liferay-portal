/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.product.navigation.control.menu.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rubén Pulido
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-62272"), @FeatureFlag("LPD-94003")}
)
@RunWith(Arquillian.class)
public class FragmentAIAssistantProductNavigationControlMenuEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-111343")
	public void testIsShow() throws Exception {
		Assert.assertTrue(
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(TestPropsValues.getUser())));
	}

	@Test
	@TestInfo("LPD-111343")
	public void testIsShowWithoutFragmentDisplayContext() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(TestPropsValues.getUser());

		mockHttpServletRequest.removeAttribute("FRAGMENT_DISPLAY_CONTEXT");

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(mockHttpServletRequest));
	}

	@Test
	@TestInfo("LPD-111343")
	public void testIsShowWithoutPermission() throws Exception {
		_user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(_user)));
	}

	private Object _getFragmentDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		bundle = BundleUtil.getBundle(
			bundle.getBundleContext(), "com.liferay.fragment.web");

		Class<?> clazz = bundle.loadClass(
			"com.liferay.fragment.web.internal.display.context." +
				"FragmentDisplayContext");

		Constructor<?> constructor = clazz.getConstructor(
			HttpServletRequest.class, RenderRequest.class,
			RenderResponse.class);

		return constructor.newInstance(httpServletRequest, null, null);
	}

	private MockHttpServletRequest _getMockHttpServletRequest(User user)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setPortletName(FragmentPortletKeys.FRAGMENT);

		themeDisplay.setScopeGroupId(_group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setAttribute(
			"FRAGMENT_DISPLAY_CONTEXT",
			_getFragmentDisplayContext(mockHttpServletRequest));

		return mockHttpServletRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.fragment.web.internal.product.navigation.control.menu.FragmentAIAssistantProductNavigationControlMenuEntry"
	)
	private ProductNavigationControlMenuEntry
		_productNavigationControlMenuEntry;

	@DeleteAfterTestRun
	private User _user;

}