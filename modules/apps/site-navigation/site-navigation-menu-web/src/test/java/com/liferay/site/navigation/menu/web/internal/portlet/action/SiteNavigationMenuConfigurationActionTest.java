/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
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
		_siteNavigationMenuConfigurationAction =
			new SiteNavigationMenuConfigurationAction();

		_modifiableSettings = _getModifiableSettings();

		_modifiableSettings.setValue(
			"displayStyleGroupId", _DISPLAY_STYLE_GROUP_ID);
		_modifiableSettings.setValue(
			"displayStyleGroupKey", _DISPLAY_STYLE_GROUP_KEY);
		_modifiableSettings.setValue("rootMenuItemId", _ROOT_MENU_ITEM_ID);
		_modifiableSettings.setValue("rootMenuItemType", "select");
		_modifiableSettings.setValue(
			"siteNavigationMenuId", _SITE_NAVIGATION_MENU_ITEM_ID);
	}

	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithFeatureFlagDisabled()
		throws Exception {

		_siteNavigationMenuConfigurationAction.groupLocalService =
			_getGroupLocalService(null);

		_siteNavigationMenuConfigurationAction.
			updateDisplayStyleGroupPreferences(
				_modifiableSettings,
				_getPortletRequest(RandomTestUtil.randomLong()));

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

	@FeatureFlags("LPD-23048")
	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithFeatureFlagEnabledDifferentScope()
		throws Exception {

		Group group = _getGroup(RandomTestUtil.randomLong());

		_siteNavigationMenuConfigurationAction.groupLocalService =
			_getGroupLocalService(group);

		_siteNavigationMenuConfigurationAction.
			updateDisplayStyleGroupPreferences(
				_modifiableSettings,
				_getPortletRequest(RandomTestUtil.randomLong()));

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

	@FeatureFlags("LPD-23048")
	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithFeatureFlagEnabledSameScope()
		throws Exception {

		Group group = _getGroup(RandomTestUtil.randomLong());

		_siteNavigationMenuConfigurationAction.groupLocalService =
			_getGroupLocalService(group);

		_siteNavigationMenuConfigurationAction.
			updateDisplayStyleGroupPreferences(
				_modifiableSettings, _getPortletRequest(group.getGroupId()));

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
		throws PortalException {

		_siteNavigationMenuConfigurationAction.
			siteNavigationMenuItemLocalService =
				_getSiteNavigationMenuItemLocalService(
					RandomTestUtil.randomString());

		_siteNavigationMenuConfigurationAction.updateRootMenuItemPreferences(
			_modifiableSettings);

		Assert.assertNull(
			_modifiableSettings.getValue(
				"rootMenuItemExternalReferenceCode", null));
		Assert.assertEquals(
			_ROOT_MENU_ITEM_ID,
			_modifiableSettings.getValue("rootMenuItemId", null));
	}

	@FeatureFlags("LPD-23048")
	@Test
	public void testUpdateRootMenuItemPreferencesWithFeatureFlagEnabled()
		throws Exception {

		String rootMenuItemExternalReferenceCode =
			RandomTestUtil.randomString();

		_siteNavigationMenuConfigurationAction.
			siteNavigationMenuItemLocalService =
				_getSiteNavigationMenuItemLocalService(
					rootMenuItemExternalReferenceCode);

		_siteNavigationMenuConfigurationAction.updateRootMenuItemPreferences(
			_modifiableSettings);

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
		throws PortalException {

		_siteNavigationMenuConfigurationAction.siteNavigationMenuService =
			_getSiteNavigationMenuService(RandomTestUtil.randomString());

		_siteNavigationMenuConfigurationAction.
			updateSiteNavigationMenuPreferences(_modifiableSettings);

		Assert.assertNull(
			_modifiableSettings.getValue(
				"siteNavigationMenuExternalReferenceCode", null));
		Assert.assertEquals(
			_SITE_NAVIGATION_MENU_ITEM_ID,
			_modifiableSettings.getValue("siteNavigationMenuId", null));
	}

	@FeatureFlags("LPD-23048")
	@Test
	public void testUpdateSiteNavigationMenuPreferencesWithFeatureFlagEnabled()
		throws PortalException {

		String siteNavigationMenuExternalReferenceCode =
			RandomTestUtil.randomString();

		_siteNavigationMenuConfigurationAction.siteNavigationMenuService =
			_getSiteNavigationMenuService(
				siteNavigationMenuExternalReferenceCode);

		_siteNavigationMenuConfigurationAction.
			updateSiteNavigationMenuPreferences(_modifiableSettings);

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
				groupLocalService.fetchGroup(0, group.getGroupKey())
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

	private ModifiableSettings _getModifiableSettings() {
		_portletPropertiesMap = new HashMap<>();

		ModifiableSettings modifiableSettings = Mockito.mock(
			ModifiableSettings.class);

		Mockito.when(
			modifiableSettings.getValue(
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
			modifiableSettings.setValue(
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
			modifiableSettings
		).reset(
			Mockito.anyString()
		);

		return modifiableSettings;
	}

	private PortletRequest _getPortletRequest(long groupId) throws Exception {
		PortletRequest portletRequest = Mockito.mock(PortletRequest.class);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(Mockito.mock(Company.class));
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
				String siteNavigationMenuItemExternalReferenceCode)
		throws PortalException {

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
		throws PortalException {

		SiteNavigationMenu siteNavigationMenu = Mockito.mock(
			SiteNavigationMenu.class);

		Mockito.when(
			siteNavigationMenu.getExternalReferenceCode()
		).thenReturn(
			siteNavigationMenuExternalReferenceCode
		);

		SiteNavigationMenuService siteNavigationMenuService = Mockito.mock(
			SiteNavigationMenuService.class);

		Mockito.when(
			siteNavigationMenuService.fetchSiteNavigationMenu(Mockito.anyLong())
		).thenReturn(
			siteNavigationMenu
		);

		return siteNavigationMenuService;
	}

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
	private SiteNavigationMenuConfigurationAction
		_siteNavigationMenuConfigurationAction;

}