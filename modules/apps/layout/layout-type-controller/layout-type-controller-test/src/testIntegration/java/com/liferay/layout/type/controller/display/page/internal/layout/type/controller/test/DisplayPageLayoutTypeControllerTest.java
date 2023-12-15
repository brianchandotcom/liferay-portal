/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.display.page.internal.layout.type.controller.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.LayoutTypeControllerTracker;

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
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class DisplayPageLayoutTypeControllerTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testDisplayPageTypeControllerWithoutContextInfoItem()
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				_group.getGroupId(), 0,
				_portal.getClassNameId(AssetCategory.class.getName()), 0,
				RandomTestUtil.randomString(), 0,
				WorkflowConstants.STATUS_DRAFT, _serviceContext);

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		Assert.assertTrue(layout.isPublished());

		_assertIncludeLayoutContent(
			false, draftLayout.getPlid(), TestPropsValues.getUser());
		_assertIncludeLayoutContent(
			false, layout.getPlid(), TestPropsValues.getUser());

		User guestUser = _userLocalService.getGuestUser(_group.getCompanyId());

		_assertIncludeLayoutContent(true, draftLayout.getPlid(), guestUser);
		_assertIncludeLayoutContent(true, layout.getPlid(), guestUser);
	}

	private void _assertIncludeLayoutContent(
			boolean noSuchLayoutExceptionExpected, long plid, User user)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(plid);

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_ASSET_DISPLAY);

		try {
			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout, user);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), user.getUserId());

			serviceContext.setRequest(mockHttpServletRequest);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			layoutTypeController.includeLayoutContent(
				mockHttpServletRequest, mockHttpServletResponse, layout);

			Assert.assertFalse(noSuchLayoutExceptionExpected);

			Assert.assertEquals(
				HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());
		}
		catch (NoSuchLayoutException noSuchLayoutException) {
			Assert.assertTrue(noSuchLayoutExceptionExpected);
		}
		finally {
			ServiceContextThreadLocal.pushServiceContext(_serviceContext);
		}
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			Layout layout, User user)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, _portal.getLayoutActualURL(layout));
		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());
		themeDisplay.setLocale(_portal.getSiteDefaultLocale(_group));

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		themeDisplay.setPermissionChecker(
			_permissionCheckerFactory.create(user));
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setPortalDomain(_company.getVirtualHostname());
		themeDisplay.setPortalURL(_company.getPortalURL(_group.getGroupId()));
		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerPort(8080);
		themeDisplay.setSignedIn(!user.isGuestUser());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Inject
	private UserLocalService _userLocalService;

}