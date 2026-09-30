/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryRoleLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import jakarta.portlet.PortletException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Nilton Vieira
 */
@RunWith(Arquillian.class)
public class UpdateSegmentsEntrySiteRolesMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_segmentsEntry = SegmentsTestUtil.addSegmentsEntry(_group.getGroupId());
	}

	@Test
	public void testProcessAction() throws Exception {
		_testProcessAction();
		_testProcessActionWithoutPermission();
	}

	private Role _addRole() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		_roles.add(role);

		return role;
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Role role, User user)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_CONFIG,
			(LiferayPortletConfig)PortletConfigFactoryUtil.create(
				_portletLocalService.getPortletById(
					SegmentsPortletKeys.SEGMENTS),
				null));

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.PORTLET_ID, SegmentsPortletKeys.SEGMENTS);
		mockLiferayPortletActionRequest.setParameter(
			"segmentsEntryId",
			String.valueOf(_segmentsEntry.getSegmentsEntryId()));
		mockLiferayPortletActionRequest.setParameter(
			"siteRoleIds", String.valueOf(role.getRoleId()));
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(user));
		mockLiferayPortletActionRequest.setAttribute(WebKeys.USER, user);

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay(User user) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLanguageId(
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()));

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());

		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		return themeDisplay;
	}

	private void _testProcessAction() throws Exception {
		Role role = _addRole();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(
				role, TestPropsValues.getUser()),
			new MockLiferayPortletActionResponse());

		Assert.assertTrue(
			_segmentsEntryRoleLocalService.hasSegmentEntryRole(
				_segmentsEntry.getSegmentsEntryId(), role.getRoleId()));
	}

	private void _testProcessActionWithoutPermission() throws Exception {
		Role role = _addRole();

		_user = UserTestUtil.addUser();

		try {
			_mvcActionCommand.processAction(
				_getMockLiferayPortletActionRequest(role, _user),
				new MockLiferayPortletActionResponse());

			Assert.fail();
		}
		catch (PortletException portletException) {
			Assert.assertTrue(
				portletException.getCause() instanceof PrincipalException);
		}

		Assert.assertFalse(
			_segmentsEntryRoleLocalService.hasSegmentEntryRole(
				_segmentsEntry.getSegmentsEntryId(), role.getRoleId()));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/segments/update_segments_entry_site_roles",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private PortletLocalService _portletLocalService;

	@DeleteAfterTestRun
	private final List<Role> _roles = new ArrayList<>();

	@DeleteAfterTestRun
	private SegmentsEntry _segmentsEntry;

	@Inject
	private SegmentsEntryRoleLocalService _segmentsEntryRoleLocalService;

	@DeleteAfterTestRun
	private User _user;

}