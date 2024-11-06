/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.content.internal.layout.type.controller.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
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
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.TestInfo;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;
import com.liferay.segments.service.SegmentsExperienceLocalService;

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
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class ContentLayoutTypeControllerTest {

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

	@Test(expected = PrincipalException.class)
	public void testContentLayoutTypeControllerDraftEditWithPreviewDraftPermission()
		throws Exception {

		_includeDraftLayoutContent(
			ActionKeys.PREVIEW_DRAFT,
			LayoutTestUtil.addTypeContentLayout(_group), Constants.EDIT);
	}

	@Test
	@TestInfo("LPS-125516")
	public void testContentLayoutTypeControllerDraftEditWithViewPermission()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				layout.getType());

		Assert.assertFalse(
			layoutTypeController.includeLayoutContent(
				_getHttpServletRequest(
					Constants.EDIT, _getUser(ActionKeys.VIEW)),
				new MockHttpServletResponse(), layout));
	}

	@Test
	public void testContentLayoutTypeControllerDraftPreviewWithPreviewDraftPermission()
		throws Exception {

		Assert.assertFalse(
			_includeDraftLayoutContent(
				ActionKeys.PREVIEW_DRAFT,
				LayoutTestUtil.addTypeContentLayout(_group),
				Constants.PREVIEW));
	}

	@Test
	public void testContentLayoutTypeControllerDraftPreviewWithUpdatePermission()
		throws Exception {

		Assert.assertFalse(
			_includeDraftLayoutContent(
				ActionKeys.UPDATE, LayoutTestUtil.addTypeContentLayout(_group),
				Constants.PREVIEW));
	}

	@Test(expected = PrincipalException.class)
	public void testContentLayoutTypeControllerDraftPreviewWithViewPermission()
		throws Exception {

		_includeDraftLayoutContent(
			ActionKeys.VIEW, LayoutTestUtil.addTypeContentLayout(_group),
			Constants.PREVIEW);
	}

	@Test
	public void testContentLayoutTypeControllerDraftViewWithPreviewDraftPermission()
		throws Exception {

		Assert.assertFalse(
			_includeDraftLayoutContent(
				ActionKeys.PREVIEW_DRAFT,
				LayoutTestUtil.addTypeContentLayout(_group), Constants.VIEW));
	}

	@Test
	public void testContentLayoutTypeControllerMainContentDiv()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				"BASIC_COMPONENT-heading");

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			null, fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
			fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), layout.fetchDraftLayout(),
			fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(), null,
			0,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		layout = _layoutLocalService.getLayout(layout.getPlid());

		String html = ContentLayoutTestUtil.getRenderLayoutHTML(
			layout, _layoutServiceContextHelper, _layoutStructureProvider,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));

		Assert.assertFalse(html.contains("main-content"));

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				StringUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_DRAFT,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		layout = _layoutLocalService.updateMasterLayoutPlid(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layoutPageTemplateEntry.getPlid());

		html = ContentLayoutTestUtil.getRenderLayoutHTML(
			layout, _layoutServiceContextHelper, _layoutStructureProvider,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));

		Assert.assertTrue(
			html.startsWith(
				"<div class=\"layout-content portlet-layout\"" +
					"id=\"main-content\" role=\"main\">"));

		Layout masterLayout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			null, fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
			fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), masterLayout.fetchDraftLayout(),
			fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(), null,
			0,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				masterLayout.getPlid()));

		ContentLayoutTestUtil.publishLayout(
			masterLayout.fetchDraftLayout(), masterLayout);

		html = ContentLayoutTestUtil.getRenderLayoutHTML(
			layout, _layoutServiceContextHelper, _layoutStructureProvider,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));

		Assert.assertTrue(html.contains("main-content"));
		Assert.assertFalse(
			html.startsWith(
				"<div class=\"layout-content portlet-layout\"" +
					"id=\"main-content\" role=\"main\">"));
	}

	@Test(expected = NoSuchLayoutException.class)
	public void testContentLayoutTypeControllerNoPublishedLayoutGuestUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		layoutTypeController.includeLayoutContent(
			_getHttpServletRequest(
				null, _userLocalService.getGuestUser(_group.getCompanyId())),
			new MockHttpServletResponse(),
			LayoutTestUtil.addTypeContentLayout(_group));
	}

	@Test
	public void testContentLayoutTypeControllerNoPublishedLayoutPermissionUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		Assert.assertFalse(
			layoutTypeController.includeLayoutContent(
				_getHttpServletRequest(null, TestPropsValues.getUser()),
				new MockHttpServletResponse(),
				LayoutTestUtil.addTypeContentLayout(_group)));
	}

	@Test(expected = PrincipalException.class)
	public void testContentLayoutTypeControllerPageTemplateDraftPreviewWithPreviewDraftPermission()
		throws Exception {

		_includeDraftLayoutContent(
			ActionKeys.PREVIEW_DRAFT, _addTypePageTemplateEntryLayout(),
			Constants.PREVIEW);
	}

	@Test
	public void testContentLayoutTypeControllerPageTemplateDraftPreviewWithUpdatePermission()
		throws Exception {

		Assert.assertFalse(
			_includeDraftLayoutContent(
				ActionKeys.UPDATE, _addTypePageTemplateEntryLayout(),
				Constants.PREVIEW));
	}

	@Test(expected = PrincipalException.class)
	public void testContentLayoutTypeControllerPageTemplateDraftPreviewWithViewPermission()
		throws Exception {

		_includeDraftLayoutContent(
			ActionKeys.VIEW, _addTypePageTemplateEntryLayout(),
			Constants.PREVIEW);
	}

	@Test
	public void testContentLayoutTypeControllerPublishedLayoutGuestUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		_layoutLocalService.updateStatus(
			TestPropsValues.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextThreadLocal.getServiceContext());

		Assert.assertFalse(
			layoutTypeController.includeLayoutContent(
				_getHttpServletRequest(
					null,
					_userLocalService.getGuestUser(_group.getCompanyId())),
				new MockHttpServletResponse(), layout));
	}

	@Test
	public void testContentLayoutTypeControllerPublishedLayoutPermissionUser()
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		_layoutLocalService.updateStatus(
			TestPropsValues.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextThreadLocal.getServiceContext());

		Assert.assertFalse(
			layoutTypeController.includeLayoutContent(
				_getHttpServletRequest(null, TestPropsValues.getUser()),
				new MockHttpServletResponse(), layout));
	}

	@Test(expected = PrincipalException.class)
	public void testContentLayoutTypeControllerUtilityPageDraftPreviewWithPreviewDraftPermission()
		throws Exception {

		_includeDraftLayoutContent(
			ActionKeys.PREVIEW_DRAFT, _addTypeUtilityPageEntryLayout(),
			Constants.PREVIEW);
	}

	@Test
	public void testContentLayoutTypeControllerUtilityPageDraftPreviewWithUpdatePermission()
		throws Exception {

		Assert.assertFalse(
			_includeDraftLayoutContent(
				ActionKeys.UPDATE, _addTypeUtilityPageEntryLayout(),
				Constants.PREVIEW));
	}

	@Test(expected = PrincipalException.class)
	public void testContentLayoutTypeControllerUtilityPageDraftPreviewWithViewPermission()
		throws Exception {

		_includeDraftLayoutContent(
			ActionKeys.VIEW, _addTypeUtilityPageEntryLayout(),
			Constants.PREVIEW);
	}

	@Test
	public void testContentLayoutTypeControllerWithLockedLayout()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		draftLayout.setStatus(WorkflowConstants.STATUS_DRAFT);

		draftLayout = _layoutLocalService.updateLayout(draftLayout);

		_lockLayout(draftLayout, TestPropsValues.getUser());

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_CONTENT);

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

	private Layout _addTypePageTemplateEntryLayout() throws Exception {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					null, _group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					ServiceContextThreadLocal.getServiceContext());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				null, _group.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.BASIC, 0,
				WorkflowConstants.STATUS_DRAFT,
				ServiceContextThreadLocal.getServiceContext());

		return _layoutLocalService.getLayout(layoutPageTemplateEntry.getPlid());
	}

	private Layout _addTypeUtilityPageEntryLayout() throws Exception {
		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				false, RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND, 0,
				ServiceContextThreadLocal.getServiceContext());

		return _layoutLocalService.getLayout(layoutUtilityPageEntry.getPlid());
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
			String actionId, Layout layout, String layoutMode)
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				layout.getType());

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

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutLockManager _layoutLockManager;

	@Inject
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Inject
	private LayoutServiceContextHelper _layoutServiceContextHelper;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private UserLocalService _userLocalService;

}