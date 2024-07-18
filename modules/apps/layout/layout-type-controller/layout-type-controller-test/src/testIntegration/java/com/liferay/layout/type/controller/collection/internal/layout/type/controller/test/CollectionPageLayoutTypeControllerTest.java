/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.collection.internal.layout.type.controller.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;

import javax.servlet.http.HttpServletRequest;

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
 * @author Javier Moral Gonzalez
 */
@RunWith(Arquillian.class)
public class CollectionPageLayoutTypeControllerTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		LayoutTestUtil.addTypePortletLayout(_group);

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testCollectionPageDraftEditWithLockedLayout() throws Exception {
		Layout layout = _addTypeCollectionLayout();

		Layout draftLayout = layout.fetchDraftLayout();

		draftLayout.setStatus(WorkflowConstants.STATUS_DRAFT);

		draftLayout = _layoutLocalService.updateLayout(draftLayout);

		_lockLayout(draftLayout, TestPropsValues.getUser());

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_COLLECTION);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			Constants.EDIT, UserTestUtil.addGroupAdminUser(_group));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		layoutTypeController.includeLayoutContent(
			httpServletRequest, mockHttpServletResponse, draftLayout);

		Assert.assertEquals(
			_layoutLockManager.getLockedLayoutURL(httpServletRequest),
			mockHttpServletResponse.getRedirectedUrl());
	}

	@Test(expected = PrincipalException.class)
	public void testCollectionPageDraftEditWithPreviewDraftPermission()
		throws Exception {

		_includeDraftLayoutContent(ActionKeys.PREVIEW_DRAFT, Constants.EDIT);
	}

	@Test
	public void testCollectionPageDraftPreviewWithPreviewDraftPermission()
		throws Exception {

		Assert.assertFalse(
			_includeDraftLayoutContent(
				ActionKeys.PREVIEW_DRAFT, Constants.PREVIEW));
	}

	@Test
	public void testCollectionPageDraftPreviewWithUpdatePermission()
		throws Exception {

		Assert.assertFalse(
			_includeDraftLayoutContent(ActionKeys.UPDATE, Constants.PREVIEW));
	}

	@Test(expected = PrincipalException.class)
	public void testCollectionPageDraftPreviewWithViewPermission()
		throws Exception {

		_includeDraftLayoutContent(ActionKeys.VIEW, Constants.PREVIEW);
	}

	@Test
	public void testCollectionPageDraftViewWithPreviewDraftPermission()
		throws Exception {

		Assert.assertFalse(
			_includeDraftLayoutContent(
				ActionKeys.PREVIEW_DRAFT, Constants.VIEW));
	}

	private Layout _addTypeCollectionLayout() throws Exception {
		return _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			LayoutConstants.TYPE_COLLECTION, false, StringPool.BLANK,
			ServiceContextThreadLocal.getServiceContext());
	}

	private HttpServletRequest _getHttpServletRequest(
			String layoutMode, User user)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "http://www.liferay.com");

		UserTestUtil.setUser(user);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			_getThemeDisplay(user, mockHttpServletRequest));

		if (Validator.isNotNull(layoutMode)) {
			mockHttpServletRequest.setParameter("p_l_mode", layoutMode);
		}

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(
			User user, HttpServletRequest mockHttpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLanguageId(_group.getDefaultLanguageId());
		themeDisplay.setLayout(LayoutTestUtil.addTypePortletLayout(_group));
		themeDisplay.setLayoutSet(
			_layoutSetLocalService.getLayoutSet(_group.getGroupId(), false));
		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setPortalDomain(company.getVirtualHostname());
		themeDisplay.setPortalURL(company.getPortalURL(_group.getGroupId()));
		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerPort(8080);
		themeDisplay.setSignedIn(true);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		return themeDisplay;
	}

	private User _getUser(String actionId) throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			role, Layout.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_group.getCompanyId()), actionId);

		User user = UserTestUtil.addUser();

		_roleLocalService.clearUserRoles(user.getUserId());

		_roleLocalService.addUserRole(user.getUserId(), role);

		return user;
	}

	private boolean _includeDraftLayoutContent(
			String actionId, String layoutMode)
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_COLLECTION);

		Layout layout = _addTypeCollectionLayout();

		return layoutTypeController.includeLayoutContent(
			_getHttpServletRequest(layoutMode, _getUser(actionId)),
			new MockHttpServletResponse(), layout.fetchDraftLayout());
	}

	private void _lockLayout(Layout layout, User user) throws Exception {
		MockActionRequest mockActionRequest = new MockActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(layout);
		themeDisplay.setUser(user);

		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		_layoutLockManager.getLock(mockActionRequest);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutLockManager _layoutLockManager;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}