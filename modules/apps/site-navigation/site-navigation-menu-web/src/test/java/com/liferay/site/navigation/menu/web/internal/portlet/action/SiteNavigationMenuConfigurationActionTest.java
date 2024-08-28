/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.portlet.action;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuService;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * @author Javier Moral
 */
public class SiteNavigationMenuConfigurationActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpSettings();
	}

	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithFeatureFlagDisabled()
		throws Exception {

		_setUpSiteNavigationMenuConfigurationAction(null, null, null);

		_siteNavigationMenuConfigurationAction.postProcess(
			_COMPANY_ID, _getPortletRequest(RandomTestUtil.randomLong()),
			_settings);

		Assert.assertNull(
			_modifiableSettings.getValue(
				"displayStyleGroupExternalReferenceCode", null));
		Assert.assertEquals(
			_DISPLAY_STYLE_GROUP_ID,
			_modifiableSettings.getValue("displayStyleGroupId", null));
		Assert.assertEquals(
			_DISPLAY_STYLE_GROUP_KEY,
			_modifiableSettings.getValue("displayStyleGroupKey", null));
	}

	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithFeatureFlagEnabledDifferentScope()
		throws Exception {

		Group group = _getGroup(RandomTestUtil.randomLong());

		_setUpSiteNavigationMenuConfigurationAction(group, null, null);

		_siteNavigationMenuConfigurationAction.postProcess(
			_COMPANY_ID, _getPortletRequest(RandomTestUtil.randomLong()),
			_settings);

		Assert.assertEquals(
			group.getExternalReferenceCode(),
			_modifiableSettings.getValue(
				"displayStyleGroupExternalReferenceCode", null));
		Assert.assertEquals(
			_DISPLAY_STYLE_GROUP_ID,
			_modifiableSettings.getValue("displayStyleGroupId", null));
		Assert.assertEquals(
			_DISPLAY_STYLE_GROUP_KEY,
			_modifiableSettings.getValue("displayStyleGroupKey", null));
	}

	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithFeatureFlagEnabledSameScope()
		throws Exception {

		Group group = _getGroup(RandomTestUtil.randomLong());

		_setUpSiteNavigationMenuConfigurationAction(group, null, null);

		_siteNavigationMenuConfigurationAction.postProcess(
			_COMPANY_ID, _getPortletRequest(group.getGroupId()), _settings);

		Assert.assertNull(
			_modifiableSettings.getValue(
				"displayStyleGroupExternalReferenceCode", null));
		Assert.assertEquals(
			_DISPLAY_STYLE_GROUP_ID,
			_modifiableSettings.getValue("displayStyleGroupId", null));
		Assert.assertEquals(
			_DISPLAY_STYLE_GROUP_KEY,
			_modifiableSettings.getValue("displayStyleGroupKey", null));
	}

	@Test
	public void testUpdateRootMenuItemPreferencesWithFeatureFlagDisabled()
		throws Exception {

		_setUpSiteNavigationMenuConfigurationAction(
			null, null, RandomTestUtil.randomString());

		_siteNavigationMenuConfigurationAction.postProcess(
			_COMPANY_ID, _getPortletRequest(RandomTestUtil.randomLong()),
			_settings);

		Assert.assertNull(
			_modifiableSettings.getValue(
				"rootMenuItemExternalReferenceCode", null));
		Assert.assertEquals(
			_ROOT_MENU_ITEM_ID,
			_modifiableSettings.getValue("rootMenuItemId", null));
	}

	@Test
	public void testUpdateRootMenuItemPreferencesWithFeatureFlagEnabled()
		throws Exception {

		String rootMenuItemExternalReferenceCode =
			RandomTestUtil.randomString();

		_setUpSiteNavigationMenuConfigurationAction(
			null, null, rootMenuItemExternalReferenceCode);

		_siteNavigationMenuConfigurationAction.postProcess(
			_COMPANY_ID, _getPortletRequest(RandomTestUtil.randomLong()),
			_settings);

		Assert.assertEquals(
			rootMenuItemExternalReferenceCode,
			_modifiableSettings.getValue(
				"rootMenuItemExternalReferenceCode", null));
		Assert.assertEquals(
			_ROOT_MENU_ITEM_ID,
			_modifiableSettings.getValue("rootMenuItemId", null));
	}

	@Test
	public void testUpdateSiteNavigationMenuPreferencesWithFeatureFlagDisabled()
		throws Exception {

		_setUpSiteNavigationMenuConfigurationAction(
			null, RandomTestUtil.randomString(), null);

		_siteNavigationMenuConfigurationAction.postProcess(
			_COMPANY_ID, _getPortletRequest(RandomTestUtil.randomLong()),
			_settings);

		Assert.assertNull(
			_modifiableSettings.getValue(
				"siteNavigationMenuExternalReferenceCode", null));
		Assert.assertEquals(
			_SITE_NAVIGATION_MENU_ITEM_ID,
			_modifiableSettings.getValue("siteNavigationMenuId", null));
	}

	@Test
	public void testUpdateSiteNavigationMenuPreferencesWithFeatureFlagEnabled()
		throws Exception {

		String siteNavigationMenuExternalReferenceCode =
			RandomTestUtil.randomString();

		_setUpSiteNavigationMenuConfigurationAction(
			null, siteNavigationMenuExternalReferenceCode, null);

		_siteNavigationMenuConfigurationAction.postProcess(
			_COMPANY_ID, _getPortletRequest(RandomTestUtil.randomLong()),
			_settings);

		Assert.assertEquals(
			siteNavigationMenuExternalReferenceCode,
			_modifiableSettings.getValue(
				"siteNavigationMenuExternalReferenceCode", null));
		Assert.assertEquals(
			_SITE_NAVIGATION_MENU_ITEM_ID,
			_modifiableSettings.getValue("siteNavigationMenuId", null));
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
			_DISPLAY_STYLE_GROUP_KEY
		);

		return group;
	}

	private GroupLocalService _getGroupLocalService(Group group)
		throws Exception {

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		if (group == null) {
			Mockito.when(
				groupLocalService.fetchGroup(
					Mockito.anyLong(), Mockito.anyString())
			).thenReturn(
				null
			);
			Mockito.when(
				groupLocalService.getGroup(Mockito.anyLong())
			).thenReturn(
				null
			);
		}
		else {
			Mockito.when(
				groupLocalService.fetchGroup(_COMPANY_ID, group.getGroupKey())
			).thenReturn(
				group
			);
			Mockito.when(
				groupLocalService.getGroup(group.getGroupId())
			).thenReturn(
				group
			);
		}

		return groupLocalService;
	}

	private PortletRequest _getPortletRequest(long groupId) throws Exception {
		PortletRequest portletRequest = Mockito.mock(PortletRequest.class);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		themeDisplay.setCompany(company);

		themeDisplay.setScopeGroupId(groupId);

		Mockito.when(
			portletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		return portletRequest;
	}

	private SiteNavigationMenuItemLocalService
		_getSiteNavigationMenuItemLocalService(
			String siteNavigationMenuItemExternalReferenceCode) {

		SiteNavigationMenuItemLocalService siteNavigationMenuItemLocalService =
			Mockito.mock(SiteNavigationMenuItemLocalService.class);

		SiteNavigationMenuItem siteNavigationMenuItem = Mockito.mock(
			SiteNavigationMenuItem.class);

		Mockito.when(
			siteNavigationMenuItem.getExternalReferenceCode()
		).thenReturn(
			siteNavigationMenuItemExternalReferenceCode
		);

		Mockito.when(
			siteNavigationMenuItemLocalService.fetchSiteNavigationMenuItem(
				Mockito.anyLong())
		).thenReturn(
			siteNavigationMenuItem
		);

		return siteNavigationMenuItemLocalService;
	}

	private SiteNavigationMenuService _getSiteNavigationMenuService(
			String siteNavigationMenuExternalReferenceCode)
		throws Exception {

		SiteNavigationMenuService siteNavigationMenuService = Mockito.mock(
			SiteNavigationMenuService.class);

		SiteNavigationMenu siteNavigationMenu = Mockito.mock(
			SiteNavigationMenu.class);

		Mockito.when(
			siteNavigationMenu.getExternalReferenceCode()
		).thenReturn(
			siteNavigationMenuExternalReferenceCode
		);

		Mockito.when(
			siteNavigationMenuService.fetchSiteNavigationMenu(Mockito.anyLong())
		).thenReturn(
			siteNavigationMenu
		);

		return siteNavigationMenuService;
	}

	private void _setUpSettings() {
		_settings = Mockito.mock(Settings.class);

		_modifiableSettings = Mockito.mock(ModifiableSettings.class);

		_portletPropertiesMap = new HashMap<>();

		Mockito.when(
			_modifiableSettings.getValue(
				Mockito.anyString(), ArgumentMatchers.nullable(String.class))
		).then(
			invocation -> {
				String value = _portletPropertiesMap.get(
					invocation.getArgument(0, String.class));

				if (Validator.isNull(value)) {
					return invocation.getArgument(1, String.class);
				}

				return value;
			}
		);

		Mockito.when(
			_modifiableSettings.setValue(
				Mockito.anyString(), Mockito.anyString())
		).then(
			invocation -> _portletPropertiesMap.put(
				invocation.getArgument(0, String.class),
				invocation.getArgument(1, String.class))
		);

		Mockito.doAnswer(
			invocation -> _portletPropertiesMap.remove(
				invocation.getArgument(0, String.class))
		).when(
			_modifiableSettings
		).reset(
			Mockito.anyString()
		);

		_modifiableSettings.setValue(
			"displayStyleGroupId", _DISPLAY_STYLE_GROUP_ID);
		_modifiableSettings.setValue(
			"displayStyleGroupKey", _DISPLAY_STYLE_GROUP_KEY);
		_modifiableSettings.setValue("rootMenuItemId", _ROOT_MENU_ITEM_ID);
		_modifiableSettings.setValue("rootMenuItemType", "select");
		_modifiableSettings.setValue(
			"siteNavigationMenuId", _SITE_NAVIGATION_MENU_ITEM_ID);

		Mockito.when(
			_settings.getModifiableSettings()
		).thenReturn(
			_modifiableSettings
		);
	}

	private void _setUpSiteNavigationMenuConfigurationAction(
			Group group, String siteNavigationMenuExternalReferenceCode,
			String siteNavigationMenuItemExternalReferenceCode)
		throws Exception {

		_siteNavigationMenuConfigurationAction =
			new SiteNavigationMenuConfigurationAction();

		_siteNavigationMenuConfigurationAction.groupLocalService =
			_getGroupLocalService(group);
		_siteNavigationMenuConfigurationAction.
			siteNavigationMenuItemLocalService =
				_getSiteNavigationMenuItemLocalService(
					siteNavigationMenuItemExternalReferenceCode);
		_siteNavigationMenuConfigurationAction.siteNavigationMenuService =
			_getSiteNavigationMenuService(
				siteNavigationMenuExternalReferenceCode);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _DISPLAY_STYLE_GROUP_ID = String.valueOf(
		RandomTestUtil.randomLong());

	private static final String _DISPLAY_STYLE_GROUP_KEY =
		RandomTestUtil.randomString();

	private static final String _ROOT_MENU_ITEM_ID = String.valueOf(
		RandomTestUtil.randomLong());

	private static final String _SITE_NAVIGATION_MENU_ITEM_ID = String.valueOf(
		RandomTestUtil.randomLong());

	private ModifiableSettings _modifiableSettings;
	private Map<String, String> _portletPropertiesMap;
	private Settings _settings;
	private SiteNavigationMenuConfigurationAction
		_siteNavigationMenuConfigurationAction;

}