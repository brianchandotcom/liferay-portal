/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.teams.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
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
public class EditSiteTeamAssignmentsUsersDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_languageUtilMockedStatic.close();
		_teamLocalServiceUtilMockedStatic.close();
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

		long teamId = RandomTestUtil.randomLong();

		_userGroupLocalServiceUtilMockedStatic.when(
			() -> UserGroupLocalServiceUtil.getTeamUserGroups(teamId)
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

		EditSiteTeamAssignmentsUsersDisplayContext
			editSiteTeamAssignmentsUsersDisplayContext = _createDisplayContext(
				teamId);

		String membershipLabel =
			editSiteTeamAssignmentsUsersDisplayContext.getMembershipLabel(
				inheritedUserId);

		Assert.assertTrue(
			membershipLabel, membershipLabel.contains(userGroupName));
	}

	@Test
	public void testIsInheritedMember() {
		long directUserId = 1;
		long teamId = 2;

		_teamLocalServiceUtilMockedStatic.when(
			() -> TeamLocalServiceUtil.hasUserTeam(directUserId, teamId)
		).thenReturn(
			true
		);

		EditSiteTeamAssignmentsUsersDisplayContext
			editSiteTeamAssignmentsUsersDisplayContext = _createDisplayContext(
				teamId);

		Assert.assertFalse(
			editSiteTeamAssignmentsUsersDisplayContext.isInheritedMember(
				directUserId));
		Assert.assertTrue(
			editSiteTeamAssignmentsUsersDisplayContext.isInheritedMember(3));
	}

	private EditSiteTeamAssignmentsUsersDisplayContext _createDisplayContext(
		long teamId) {

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

		EditSiteTeamAssignmentsUsersDisplayContext
			editSiteTeamAssignmentsUsersDisplayContext =
				new EditSiteTeamAssignmentsUsersDisplayContext(
					httpServletRequest, null, null);

		ReflectionTestUtil.setFieldValue(
			editSiteTeamAssignmentsUsersDisplayContext, "_teamId", teamId);

		return editSiteTeamAssignmentsUsersDisplayContext;
	}

	private static final MockedStatic<LanguageUtil> _languageUtilMockedStatic =
		Mockito.mockStatic(LanguageUtil.class);
	private static final MockedStatic<TeamLocalServiceUtil>
		_teamLocalServiceUtilMockedStatic = Mockito.mockStatic(
			TeamLocalServiceUtil.class);
	private static final MockedStatic<UserGroupLocalServiceUtil>
		_userGroupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			UserGroupLocalServiceUtil.class);
	private static final MockedStatic<UserLocalServiceUtil>
		_userLocalServiceUtilMockedStatic = Mockito.mockStatic(
			UserLocalServiceUtil.class);

}