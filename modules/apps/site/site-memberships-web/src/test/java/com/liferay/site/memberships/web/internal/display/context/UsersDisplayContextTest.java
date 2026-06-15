/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.memberships.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Matyas Wollner
 */
public class UsersDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_languageUtilMockedStatic.close();
		_organizationLocalServiceUtilMockedStatic.close();
		_portletPreferencesFactoryUtilMockedStatic.close();
		_userGroupLocalServiceUtilMockedStatic.close();
		_userLocalServiceUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_languageUtilMockedStatic.when(
			() -> LanguageUtil.format(
				Mockito.any(Locale.class), Mockito.anyString(),
				Mockito.any(Object.class))
		).thenAnswer(
			invocation -> {
				Object[] arguments = invocation.getArguments();

				return arguments[2];
			}
		);
	}

	@Test
	public void testGetMembershipLabel() {
		UserGroup userGroup = Mockito.mock(UserGroup.class);
		long userGroupId = RandomTestUtil.randomLong();

		Mockito.when(
			userGroup.getUserGroupId()
		).thenReturn(
			userGroupId
		);

		String userGroupName = RandomTestUtil.randomString();

		Mockito.when(
			userGroup.getName()
		).thenReturn(
			userGroupName
		);

		long groupId = RandomTestUtil.randomLong();

		_userGroupLocalServiceUtilMockedStatic.when(
			() -> UserGroupLocalServiceUtil.getGroupUserGroups(groupId)
		).thenReturn(
			Collections.singletonList(userGroup)
		);

		long inheritedUserId = RandomTestUtil.randomLong();

		_userLocalServiceUtilMockedStatic.when(
			() -> UserLocalServiceUtil.hasUserGroupUser(
				userGroupId, inheritedUserId)
		).thenReturn(
			true
		);

		Organization organization = Mockito.mock(Organization.class);
		long organizationId = RandomTestUtil.randomLong();

		Mockito.when(
			organization.getOrganizationId()
		).thenReturn(
			organizationId
		);

		String organizationName = RandomTestUtil.randomString();

		Mockito.when(
			organization.getName()
		).thenReturn(
			organizationName
		);

		_organizationLocalServiceUtilMockedStatic.when(
			() -> OrganizationLocalServiceUtil.getGroupOrganizations(groupId)
		).thenReturn(
			Collections.singletonList(organization)
		);

		_userLocalServiceUtilMockedStatic.when(
			() -> UserLocalServiceUtil.hasOrganizationUser(
				organizationId, inheritedUserId)
		).thenReturn(
			true
		);

		UsersDisplayContext usersDisplayContext = _createUsersDisplayContext(
			groupId);

		String membershipLabel = usersDisplayContext.getMembershipLabel(
			inheritedUserId);

		Assert.assertTrue(
			membershipLabel, membershipLabel.contains(userGroupName));
		Assert.assertTrue(
			membershipLabel, membershipLabel.contains(organizationName));
	}

	@Test
	public void testIsInheritedMember() {
		long directUserId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();

		_userLocalServiceUtilMockedStatic.when(
			() -> UserLocalServiceUtil.getGroupUserIds(groupId)
		).thenReturn(
			new long[] {directUserId}
		);

		UsersDisplayContext usersDisplayContext = _createUsersDisplayContext(
			groupId);

		Assert.assertFalse(usersDisplayContext.isInheritedMember(directUserId));
		Assert.assertTrue(
			usersDisplayContext.isInheritedMember(RandomTestUtil.randomLong()));
	}

	private UsersDisplayContext _createUsersDisplayContext(long groupId) {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		UsersDisplayContext usersDisplayContext = new UsersDisplayContext(
			httpServletRequest, null, null);

		ReflectionTestUtil.setFieldValue(
			usersDisplayContext, "_groupId", groupId);

		return usersDisplayContext;
	}

	private static final MockedStatic<LanguageUtil> _languageUtilMockedStatic =
		Mockito.mockStatic(LanguageUtil.class);
	private static final MockedStatic<OrganizationLocalServiceUtil>
		_organizationLocalServiceUtilMockedStatic = Mockito.mockStatic(
			OrganizationLocalServiceUtil.class);
	private static final MockedStatic<PortletPreferencesFactoryUtil>
		_portletPreferencesFactoryUtilMockedStatic = Mockito.mockStatic(
			PortletPreferencesFactoryUtil.class);
	private static final MockedStatic<UserGroupLocalServiceUtil>
		_userGroupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			UserGroupLocalServiceUtil.class);
	private static final MockedStatic<UserLocalServiceUtil>
		_userLocalServiceUtilMockedStatic = Mockito.mockStatic(
			UserLocalServiceUtil.class);

}