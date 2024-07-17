/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class AutocompleteUserMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user1 = UserTestUtil.addUser();

		_permissionChecker = PermissionCheckerFactoryUtil.create(_user1);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.PUBLICATIONS_USER);

		RoleTestUtil.addResourcePermission(
			role, CTCollection.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_user1.getCompanyId()), CTActionKeys.INVITE_USERS);

		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_groupLocalService.addRoleGroup(
			role.getRoleId(), userGroup.getGroupId());

		_userLocalService.addUserGroupUser(
			userGroup.getUserGroupId(), _user1.getUserId());

		_user2 = UserTestUtil.addUser();

		_userLocalService.addUserGroupUser(
			userGroup.getUserGroupId(), _user2.getUserId());

		_user3 = UserTestUtil.addUser();

		_userLocalService.addUserGroupUser(
			userGroup.getUserGroupId(), _user3.getUserId());
	}

	@Test
	public void testGetInheritedRoles() throws Exception {
		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, "P1", null);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			_getMockLiferayResourceRequest(
				_user2.getScreenName(), ctCollection.getCtCollectionId()),
			mockLiferayResourceResponse);

		JSONArray jsonArray = _getUsersJSONArray(mockLiferayResourceResponse);

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			_user2.getFullName(), jsonObject.getString("fullName"));
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
			String userName, long ctCollectionId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(_permissionChecker);
		themeDisplay.setUser(_user1);

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayResourceRequest.setParameter(
			"ctCollectionId", String.valueOf(ctCollectionId));
		mockLiferayResourceRequest.setParameter("keywords", userName);

		return mockLiferayResourceRequest;
	}

	private JSONArray _getUsersJSONArray(
			MockLiferayResourceResponse mockLiferayResourceResponse)
		throws Exception {

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONArray(
			new String(byteArrayOutputStream.toByteArray()));
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(filter = "mvc.command.name=/change_tracking/autocomplete_user")
	private MVCResourceCommand _mvcResourceCommand;

	private PermissionChecker _permissionChecker;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user1;
	private User _user2;
	private User _user3;

	@Inject
	private UserLocalService _userLocalService;

}