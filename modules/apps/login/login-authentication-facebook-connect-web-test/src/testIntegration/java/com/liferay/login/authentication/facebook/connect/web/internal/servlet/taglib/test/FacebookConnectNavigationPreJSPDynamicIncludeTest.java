/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.authentication.facebook.connect.web.internal.servlet.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.kernel.provider.LayoutUtilityPageEntryLayoutProvider;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.facebook.connect.configuration.FacebookConnectConfiguration;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectWebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
public class FacebookConnectNavigationPreJSPDynamicIncludeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testIncludeOnUtilityPageWithFacebookConnectEnabled()
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					FacebookConnectConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).put(
						"oauthAuthURL", RandomTestUtil.randomString()
					).build())) {

			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				true, RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_LOGIN, 0, _serviceContext);

			Layout layout =
				_layoutUtilityPageEntryLayoutProvider.
					getDefaultLayoutUtilityPageEntryLayout(
						_group.getGroupId(),
						LayoutUtilityPageEntryConstants.TYPE_LOGIN);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout);

			_dynamicInclude.include(
				mockHttpServletRequest, new MockHttpServletResponse(),
				RandomTestUtil.randomString());

			Assert.assertNull(
				mockHttpServletRequest.getAttribute(
					FacebookConnectWebKeys.FACEBOOK_AUTH_URL));
		}
	}

	@Test
	public void testIncludeWithFacebookConnectDisabled() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					FacebookConnectConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", false
					).put(
						"oauthAuthURL", RandomTestUtil.randomString()
					).build())) {

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest();

			_dynamicInclude.include(
				mockHttpServletRequest, new MockHttpServletResponse(),
				RandomTestUtil.randomString());

			Assert.assertNull(
				mockHttpServletRequest.getAttribute(
					FacebookConnectWebKeys.FACEBOOK_AUTH_URL));
		}
	}

	@Test
	public void testIncludeWithFacebookConnectEnabled() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					FacebookConnectConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).put(
						"oauthAuthURL", RandomTestUtil.randomString()
					).build())) {

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest();

			_dynamicInclude.include(
				mockHttpServletRequest, new MockHttpServletResponse(),
				RandomTestUtil.randomString());

			Assert.assertNotNull(
				mockHttpServletRequest.getAttribute(
					FacebookConnectWebKeys.FACEBOOK_AUTH_URL));
		}
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		Layout layout = _layoutLocalService.fetchDefaultLayout(
			TestPropsValues.getGroupId(), false);

		return _getMockHttpServletRequest(layout);
	}

	private MockHttpServletRequest _getMockHttpServletRequest(Layout layout)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(layout);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.login.authentication.facebook.connect.web.internal.servlet.taglib.FacebookConnectNavigationPreJSPDynamicInclude"
	)
	private DynamicInclude _dynamicInclude;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutUtilityPageEntryLayoutProvider
		_layoutUtilityPageEntryLayoutProvider;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	private ServiceContext _serviceContext;

}