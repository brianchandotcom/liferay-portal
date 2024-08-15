/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.language.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;

import javax.portlet.PortletPreferences;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class SiteNavigationLanguageConfigurationActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	@TestInfo("LPD-33733")
	public void testUpdateDisplayStyleGroupPreferencesWithDifferentGroup()
		throws Exception {

		Group group = _getGroup(RandomTestUtil.randomLong());

		SiteNavigationLanguageConfigurationAction
			siteNavigationLanguageConfigurationAction =
				_getSiteNavigationLanguageConfigurationAction(group);

		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		siteNavigationLanguageConfigurationAction.postProcess(
			_COMPANY_ID,
			_getMockActionRequest(
				group.getGroupKey(),
				_getThemeDisplay(RandomTestUtil.randomLong())),
			portletPreferences);

		Mockito.verify(
			_groupLocalService
		).fetchGroup(
			0, group.getGroupKey()
		);
		Mockito.verify(
			portletPreferences
		).setValue(
			"displayStyleGroupExternalReferenceCode",
			group.getExternalReferenceCode()
		);
	}

	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithNoSelection()
		throws Exception {

		SiteNavigationLanguageConfigurationAction
			siteNavigationLanguageConfigurationAction =
				_getSiteNavigationLanguageConfigurationAction(
					_getGroup(RandomTestUtil.randomLong()));

		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		siteNavigationLanguageConfigurationAction.postProcess(
			_COMPANY_ID,
			_getMockActionRequest(
				StringPool.BLANK,
				_getThemeDisplay(RandomTestUtil.randomLong())),
			portletPreferences);

		Mockito.verify(
			_groupLocalService
		).fetchGroup(
			0, StringPool.BLANK
		);
		Mockito.verify(
			portletPreferences
		).reset(
			"displayStyleGroupExternalReferenceCode"
		);
	}

	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithSameGroup()
		throws Exception {

		Group group = _getGroup(RandomTestUtil.randomLong());

		SiteNavigationLanguageConfigurationAction
			siteNavigationLanguageConfigurationAction =
				_getSiteNavigationLanguageConfigurationAction(group);

		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		siteNavigationLanguageConfigurationAction.postProcess(
			_COMPANY_ID,
			_getMockActionRequest(
				group.getGroupKey(), _getThemeDisplay(group.getGroupId())),
			portletPreferences);

		Mockito.verify(
			_groupLocalService
		).fetchGroup(
			0, group.getGroupKey()
		);
		Mockito.verify(
			portletPreferences
		).reset(
			"displayStyleGroupExternalReferenceCode"
		);
	}

	private Group _getGroup(long groupId) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);
		Mockito.when(
			group.getGroupId()
		).thenReturn(
			groupId
		);
		Mockito.when(
			group.getGroupKey()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		return group;
	}

	private MockActionRequest _getMockActionRequest(
		String displayStyleGroupKey, ThemeDisplay themeDisplay) {

		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
		mockActionRequest.setParameter(
			"preferences--displayStyleGroupKey--", displayStyleGroupKey);

		return mockActionRequest;
	}

	private SiteNavigationLanguageConfigurationAction
			_getSiteNavigationLanguageConfigurationAction(Group group)
		throws Exception {

		SiteNavigationLanguageConfigurationAction
			siteNavigationLanguageConfigurationAction =
				new SiteNavigationLanguageConfigurationAction();

		_setUpGroupLocalService(group);

		ReflectionTestUtil.setFieldValue(
			siteNavigationLanguageConfigurationAction, "_groupLocalService",
			_groupLocalService);

		return siteNavigationLanguageConfigurationAction;
	}

	private ThemeDisplay _getThemeDisplay(long groupId) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(Mockito.mock(Company.class));
		themeDisplay.setScopeGroupId(groupId);

		return themeDisplay;
	}

	private void _setUpGroupLocalService(Group group) throws Exception {
		Mockito.reset(_groupLocalService);

		if (group == null) {
			Mockito.when(
				_groupLocalService.fetchGroup(
					Mockito.anyLong(), Mockito.anyString())
			).thenReturn(
				null
			);
			Mockito.when(
				_groupLocalService.getGroup(Mockito.anyLong())
			).thenReturn(
				null
			);
		}
		else {
			Mockito.when(
				_groupLocalService.fetchGroup(0, group.getGroupKey())
			).thenReturn(
				group
			);
			Mockito.when(
				_groupLocalService.getGroup(group.getGroupId())
			).thenReturn(
				group
			);
		}
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);

}