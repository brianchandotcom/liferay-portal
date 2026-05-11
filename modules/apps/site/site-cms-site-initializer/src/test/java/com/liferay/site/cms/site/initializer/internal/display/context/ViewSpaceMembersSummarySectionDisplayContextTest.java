/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class ViewSpaceMembersSummarySectionDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	@TestInfo("LPD-89584")
	public void testGetCreationMenu() throws Exception {
		_testGetCreationMenuReturnsMenuWhenUserHasAssignMembersPermission();
		_testGetCreationMenuReturnsEmptyMenuWhenUserLacksAssignMembersPermission();
	}

	private ViewSpaceMembersSummarySectionDisplayContext _getDisplayContext(
			boolean hasAssignMembersPermission)
		throws Exception {

		DepotEntryLocalService depotEntryLocalService = Mockito.mock(
			DepotEntryLocalService.class);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getExternalReferenceCode()
		).thenReturn(
			StringUtil.randomString()
		);

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		Mockito.when(
			groupLocalService.getGroup(Mockito.anyLong())
		).thenReturn(
			group
		);

		@SuppressWarnings("unchecked")
		ModelResourcePermission<Group> groupModelResourcePermission =
			Mockito.mock(ModelResourcePermission.class);

		Mockito.when(
			groupModelResourcePermission.contains(
				Mockito.any(), Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			hasAssignMembersPermission
		);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, new ThemeDisplay());

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenReturn(
			StringUtil.randomString()
		);

		return new ViewSpaceMembersSummarySectionDisplayContext(
			depotEntryLocalService, _GROUP_ID, groupLocalService,
			groupModelResourcePermission, mockHttpServletRequest, language,
			Mockito.mock(UserGroupLocalService.class),
			Mockito.mock(UserLocalService.class));
	}

	private void _testGetCreationMenuReturnsEmptyMenuWhenUserLacksAssignMembersPermission()
		throws Exception {

		ViewSpaceMembersSummarySectionDisplayContext displayContext =
			_getDisplayContext(false);

		CreationMenu creationMenu = displayContext.getCreationMenu();

		Assert.assertNotNull(creationMenu);
		Assert.assertTrue(creationMenu.isEmpty());
	}

	private void _testGetCreationMenuReturnsMenuWhenUserHasAssignMembersPermission()
		throws Exception {

		ViewSpaceMembersSummarySectionDisplayContext displayContext =
			_getDisplayContext(true);

		CreationMenu creationMenu = displayContext.getCreationMenu();

		Assert.assertNotNull(creationMenu);
		Assert.assertFalse(creationMenu.isEmpty());
	}

	private static final long _GROUP_ID = 1L;

}