/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class AddLayoutUtilityPageEntryMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testAddLayoutUtilityPageEntry() throws Exception {
		String name = RandomTestUtil.randomString();

		ReflectionTestUtil.invoke(
			_addLayoutUtilityPageEntryMVCActionCommand,
			"_addLayoutUtilityPageEntry", new Class<?>[] {ActionRequest.class},
			_getMockLiferayPortletActionRequest(
				name, TestPropsValues.getUser(),
				LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND));

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.fetchLayoutUtilityPageEntry(
				TestPropsValues.getGroupId(), name,
				LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND);

		Assert.assertNotNull(layoutUtilityPageEntry);
	}

	@Test
	public void testAddLayoutUtilityPageEntryWithoutPermissions()
		throws Exception {

		User user = UserTestUtil.addGroupUser(
			_groupLocalService.fetchGroup(TestPropsValues.getGroupId()),
			RoleConstants.POWER_USER);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			Assert.assertThrows(
				PrincipalException.class,
				() -> ReflectionTestUtil.invoke(
					_addLayoutUtilityPageEntryMVCActionCommand,
					"_addLayoutUtilityPageEntry",
					new Class<?>[] {ActionRequest.class},
					_getMockLiferayPortletActionRequest(
						RandomTestUtil.randomString(), user,
						LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND)));
		}
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			String name, User user, String type)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRequest(
			mockLiferayPortletActionRequest.getHttpServletRequest());
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(user);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.addParameter("name", name);
		mockLiferayPortletActionRequest.addParameter("type", type);

		return mockLiferayPortletActionRequest;
	}

	@Inject(
		filter = "mvc.command.name=/layout_admin/add_layout_utility_page_entry"
	)
	private MVCActionCommand _addLayoutUtilityPageEntryMVCActionCommand;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}