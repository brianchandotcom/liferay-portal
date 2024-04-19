/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.welcome.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Nikoletta Buza
 * @author Istvan Sajtos
 */
@FeatureFlags("LPD-6378")
@RunWith(Arquillian.class)
public class WelcomeSiteInitializerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testInitialize() throws PortalException {
		UserTestUtil.setUser(TestPropsValues.getUser());

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.site.initializer.welcome");

		siteInitializer.initialize(_group.getGroupId());

		_assertLayoutUtilityPageEntry(
			"Create Account",
			"com_liferay_login_web_portlet_CreateAccountPortlet",
			_layoutUtilityPageEntryLocalService.
				fetchDefaultLayoutUtilityPageEntry(
					_group.getGroupId(),
					LayoutUtilityPageEntryConstants.TYPE_CREATE_ACCOUNT));
		_assertLayoutUtilityPageEntry(
			"Forgot Password",
			"com_liferay_login_web_portlet_ForgotPasswordPortlet",
			_layoutUtilityPageEntryLocalService.
				fetchDefaultLayoutUtilityPageEntry(
					_group.getGroupId(),
					LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD));
		_assertLayoutUtilityPageEntry(
			"Sign In", "com_liferay_login_web_portlet_LoginPortlet",
			_layoutUtilityPageEntryLocalService.
				fetchDefaultLayoutUtilityPageEntry(
					_group.getGroupId(),
					LayoutUtilityPageEntryConstants.TYPE_LOGIN));
	}

	private void _assertLayoutUtilityPageEntry(
		String expectedName, String expectedPortletId,
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		Assert.assertNotNull(layoutUtilityPageEntry);

		Assert.assertEquals(expectedName, layoutUtilityPageEntry.getName());

		long layoutUtilityPageEntryPlid = layoutUtilityPageEntry.getPlid();

		List<PortletPreferences> portletPreferencesByPlid =
			_portletPreferencesLocalService.getPortletPreferencesByPlid(
				layoutUtilityPageEntryPlid);

		Assert.assertEquals(
			portletPreferencesByPlid.toString(), 1,
			portletPreferencesByPlid.size());

		PortletPreferences portletPreferences = portletPreferencesByPlid.get(0);

		Assert.assertEquals(
			expectedPortletId, portletPreferences.getPortletId());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private ServiceContext _serviceContext;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

}