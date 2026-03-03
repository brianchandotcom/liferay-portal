/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.web.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.EntryLocalService;
import com.liferay.contacts.web.internal.constants.ContactsPortletKeys;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.Portlet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Tancredi Covioli
 */
@RunWith(Arquillian.class)
public class ContactsCenterPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testProcessActionUpdateEntry() throws Exception {
		Group group = _groupLocalService.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.GUEST);
		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		Entry entry = _entryLocalService.addEntry(
			user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(UniqueStringRandomizerBumper.INSTANCE) +
				"@liferay.com",
			RandomTestUtil.randomString());

		_assertSuccess(entry, false, false, group, user2);
		_assertSuccess(entry, true, true, group, user1);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), Entry.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ActionKeys.VIEW);

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), user2);

		_assertSuccess(entry, true, false, group, user2);
	}

	private void _assertSuccess(
			Entry entry, boolean expectSuccess, boolean expectUpdate,
			Group group, User user)
		throws Exception {

		String emailAddress =
			RandomTestUtil.randomString(UniqueStringRandomizerBumper.INSTANCE) +
				"@liferay.com";
		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			_portlet.processAction(
				_getMockLiferayPortletActionRequest(
					entry, group, emailAddress, user),
				mockLiferayPortletActionResponse);
		}

		Entry entry2 = _entryLocalService.getEntry(entry.getEntryId());

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayPortletActionResponse.getHttpServletResponse();

		String content = mockHttpServletResponse.getContentAsString();

		JSONObject jsonObject = _jsonFactory.createJSONObject(content);

		boolean result = jsonObject.getBoolean("success");

		if (expectSuccess) {
			Assert.assertTrue(result);
		}
		else {
			Assert.assertFalse(result);
		}

		if (expectUpdate) {
			Assert.assertEquals(entry2.getEmailAddress(), emailAddress);
		}
		else {
			Assert.assertNotEquals(entry2.getEmailAddress(), emailAddress);
		}
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Entry entry, Group group, String emailAddress, User user)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			ActionRequest.ACTION_NAME, "updateEntry");
		mockLiferayPortletActionRequest.setParameter(
			"comments", RandomTestUtil.randomString());
		mockLiferayPortletActionRequest.setParameter(
			"emailAddress", emailAddress);
		mockLiferayPortletActionRequest.setParameter(
			"entryId", String.valueOf(entry.getEntryId()));
		mockLiferayPortletActionRequest.setParameter(
			"fullName", RandomTestUtil.randomString());
		mockLiferayPortletActionRequest.setParameter(
			"redirect", RandomTestUtil.randomString());

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			CompanyLocalServiceUtil.fetchCompany(
				TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());

		if (user != null) {
			themeDisplay.setSignedIn(true);
			themeDisplay.setUser(user);
		}

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockLiferayPortletActionRequest;
	}

	@Inject
	private EntryLocalService _entryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(
		filter = "jakarta.portlet.name=" + ContactsPortletKeys.CONTACTS_CENTER
	)
	private Portlet _portlet;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}