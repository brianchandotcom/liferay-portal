/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
@Sync
public class CopyLayoutMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = _getServiceContext(_group);

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testDoProcessActionCopyLayout() throws Exception {
		Layout expectedLayout = LayoutTestUtil.addTypeContentPublishedLayout(
			_group, "Test layout", WorkflowConstants.STATUS_APPROVED);

		expectedLayout.setFriendlyURL("/test-layout");

		expectedLayout = _layoutLocalService.updateLayout(expectedLayout);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockLiferayPortletActionRequest.addParameter(
			"privateLayout", String.valueOf(expectedLayout.isPrivateLayout()));
		mockLiferayPortletActionRequest.addParameter(
			"name", "Copy test layout");
		mockLiferayPortletActionRequest.addParameter(
			"sourcePlid", String.valueOf(expectedLayout.getPlid()));

		_addFragmentEntryLinkToLayout(
			expectedLayout,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				expectedLayout.getPlid()));

		Role role = _roleLocalService.addRole(
			_serviceContext.getUserId(), null, 0, StringUtil.randomString(),
			Collections.emptyMap(), Collections.emptyMap(),
			RoleConstants.TYPE_REGULAR, StringPool.BLANK, _serviceContext);

		_addModelResources(role, expectedLayout);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Layout actualLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
			expectedLayout.getGroupId(), expectedLayout.isPrivateLayout(),
			"/copy-test-layout");

		_validateCopiedLayout(expectedLayout, actualLayout);

		List<ResourcePermission> expectedResourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				expectedLayout.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(expectedLayout.getPlid()));

		List<ResourcePermission> actualResourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				expectedLayout.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(actualLayout.getPlid()));

		Assert.assertNotEquals(
			expectedResourcePermissions.toString(),
			expectedResourcePermissions.size(),
			actualResourcePermissions.size());
	}

	@Test
	public void testDoProcessActionCopyLayoutWithPermissions()
		throws Exception {

		Layout expectedLayout = LayoutTestUtil.addTypeContentPublishedLayout(
			_group, "Test layout with permissions",
			WorkflowConstants.STATUS_APPROVED);

		expectedLayout.setFriendlyURL("/test-layout-with-permissions");

		expectedLayout = _layoutLocalService.updateLayout(expectedLayout);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockLiferayPortletActionRequest.addParameter(
			"privateLayout", String.valueOf(expectedLayout.isPrivateLayout()));
		mockLiferayPortletActionRequest.addParameter(
			"name", "Copy test layout with permissions");
		mockLiferayPortletActionRequest.addParameter(
			"copyPermissions", StringPool.TRUE);
		mockLiferayPortletActionRequest.addParameter(
			"sourcePlid", String.valueOf(expectedLayout.getPlid()));

		_addFragmentEntryLinkToLayout(
			expectedLayout,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				expectedLayout.getPlid()));

		Role role = _roleLocalService.addRole(
			_serviceContext.getUserId(), null, 0, StringUtil.randomString(),
			Collections.emptyMap(), Collections.emptyMap(),
			RoleConstants.TYPE_REGULAR, StringPool.BLANK, _serviceContext);

		_addModelResources(role, expectedLayout);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Layout actualLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
			expectedLayout.getGroupId(), expectedLayout.isPrivateLayout(),
			"/copy-test-layout-with-permissions");

		_validateCopiedLayout(expectedLayout, actualLayout);

		List<ResourcePermission> expectedResourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				expectedLayout.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(expectedLayout.getPlid()));

		List<ResourcePermission> actualResourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				expectedLayout.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(actualLayout.getPlid()));

		Assert.assertEquals(
			expectedResourcePermissions.toString(),
			expectedResourcePermissions.size(),
			actualResourcePermissions.size());

		Assert.assertNotNull(
			_resourcePermissionLocalService.getResourcePermission(
				expectedLayout.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(actualLayout.getPlid()), role.getRoleId()));
	}

	private FragmentEntryLink _addFragmentEntryLinkToLayout(
			Layout layout, long segmentsExperienceId)
		throws Exception {

		FragmentEntry fragmentEntry = _getFragmentEntry();

		return ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			null, fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
			fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), layout, fragmentEntry.getFragmentEntryKey(),
			segmentsExperienceId, fragmentEntry.getType());
	}

	private void _addModelResources(Role role, Layout expectedLayout)
		throws Exception {

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			Layout.class.getName());

		modelPermissions.addRolePermissions(role.getName(), ActionKeys.VIEW);

		_resourceLocalService.addModelResources(
			expectedLayout.getCompanyId(), expectedLayout.getGroupId(),
			expectedLayout.getUserId(), Layout.class.getName(),
			expectedLayout.getPlid(), modelPermissions);
	}

	private FragmentEntry _getFragmentEntry() throws Exception {
		if (_fragmentEntry != null) {
			return _fragmentEntry;
		}

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		_fragmentEntry = _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(), "fragment-entry-key",
			RandomTestUtil.randomString(), StringPool.BLANK,
			"<div data-lfr-styles><span>Test</span>Fragment</div>",
			StringPool.BLANK, false, StringPool.BLANK, null, 0,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		return _fragmentEntry;
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockLiferayPortletActionRequest;
	}

	private ServiceContext _getServiceContext(Group group) throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			group, TestPropsValues.getUserId());
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(_group.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private void _validateCopiedLayout(
		Layout expectedLayout, Layout actualLayout) {

		Assert.assertNotNull(actualLayout);

		List<FragmentEntryLink>
			expectedLayoutSegmentsExperienceLayoutFragmentEntryLinks =
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksBySegmentsExperienceId(
						_group.getGroupId(),
						_segmentsExperienceLocalService.
							fetchDefaultSegmentsExperienceId(
								expectedLayout.getPlid()),
						expectedLayout.getPlid());

		List<FragmentEntryLink>
			actualLayoutSegmentsExperienceLayoutFragmentEntryLinks =
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksBySegmentsExperienceId(
						_group.getGroupId(),
						_segmentsExperienceLocalService.
							fetchDefaultSegmentsExperienceId(
								actualLayout.getPlid()),
						actualLayout.getPlid());

		Assert.assertEquals(
			actualLayoutSegmentsExperienceLayoutFragmentEntryLinks.toString(),
			expectedLayoutSegmentsExperienceLayoutFragmentEntryLinks.size(),
			actualLayoutSegmentsExperienceLayoutFragmentEntryLinks.size());

		FragmentEntryLink expectedLayoutFragmentEntryLink =
			expectedLayoutSegmentsExperienceLayoutFragmentEntryLinks.get(0);

		FragmentEntryLink actualLayoutFragmentEntryLink =
			actualLayoutSegmentsExperienceLayoutFragmentEntryLinks.get(0);

		Assert.assertEquals(
			expectedLayoutFragmentEntryLink.getConfiguration(),
			actualLayoutFragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			expectedLayoutFragmentEntryLink.getCss(),
			actualLayoutFragmentEntryLink.getCss());
		Assert.assertEquals(
			expectedLayoutFragmentEntryLink.getEditableValues(),
			actualLayoutFragmentEntryLink.getEditableValues());
		Assert.assertEquals(
			expectedLayoutFragmentEntryLink.getHtml(),
			actualLayoutFragmentEntryLink.getHtml());
		Assert.assertEquals(
			expectedLayoutFragmentEntryLink.getJs(),
			actualLayoutFragmentEntryLink.getJs());
		Assert.assertEquals(
			expectedLayoutFragmentEntryLink.getRendererKey(),
			actualLayoutFragmentEntryLink.getRendererKey());
		Assert.assertEquals(
			expectedLayoutFragmentEntryLink.getEditableValues(),
			actualLayoutFragmentEntryLink.getEditableValues());
		Assert.assertEquals(
			expectedLayoutFragmentEntryLink.getPosition(),
			actualLayoutFragmentEntryLink.getPosition());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	private FragmentEntry _fragmentEntry;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/layout_admin/copy_layout")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private ResourceLocalService _resourceLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}