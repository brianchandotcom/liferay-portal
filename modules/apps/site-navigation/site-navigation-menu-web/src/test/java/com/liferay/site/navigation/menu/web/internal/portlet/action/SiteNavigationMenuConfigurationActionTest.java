/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
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
	}

	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithFeatureFlagDisabled() {
		_modifiableSettings.setValue("displayStyleGroupId", "1234");
		_modifiableSettings.setValue("displayStyleGroupKey", "groupKey");

		_siteNavigationMenuConfigurationAction.
			updateDisplayStyleGroupPreferences(
				_modifiableSettings, _getPortletRequest("groupKey1"));

		Assert.assertEquals(
			"1234", _modifiableSettings.getValue("displayStyleGroupId", null));
		Assert.assertEquals(
			"groupKey",
			_modifiableSettings.getValue("displayStyleGroupKey", null));
		Assert.assertNull(
			_modifiableSettings.getValue(
				"displayStyleGroupExternalReferenceCode", null));
	}

	@FeatureFlags("LPD-23048")
	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithFeatureFlagEnabledDifferentScope() {
		_modifiableSettings.setValue("displayStyleGroupId", "1234");
		_modifiableSettings.setValue("displayStyleGroupKey", "groupKey");

		_siteNavigationMenuConfigurationAction.
			updateDisplayStyleGroupPreferences(
				_modifiableSettings, _getPortletRequest("differentGroupKey"));

		Assert.assertNull(
			_modifiableSettings.getValue("displayStyleGroupId", null));
		Assert.assertNull(
			_modifiableSettings.getValue("displayStyleGroupKey", null));
		Assert.assertEquals(
			"groupKey",
			_modifiableSettings.getValue(
				"displayStyleGroupExternalReferenceCode", null));
	}

	@FeatureFlags("LPD-23048")
	@Test
	public void testUpdateDisplayStyleGroupPreferencesWithFeatureFlagEnabledSameScope() {
		_modifiableSettings.setValue("displayStyleGroupId", "1234");
		_modifiableSettings.setValue("displayStyleGroupKey", "groupKey");

		_siteNavigationMenuConfigurationAction.
			updateDisplayStyleGroupPreferences(
				_modifiableSettings, _getPortletRequest("groupKey"));

		Assert.assertNull(
			_modifiableSettings.getValue("displayStyleGroupId", null));
		Assert.assertNull(
			_modifiableSettings.getValue("displayStyleGroupKey", null));
		Assert.assertNull(
			_modifiableSettings.getValue(
				"displayStyleGroupExternalReferenceCode", null));
	}

	@Test
	public void testUpdateRootMenuItemPreferencesWithFeatureFlagDisabled()
		throws PortalException {

		_modifiableSettings.setValue("rootMenuItemId", "1234");

		_siteNavigationMenuConfigurationAction.siteNavigationMenuItemService =
			_getSiteNavigationMenuItemService("itemERC");

		_siteNavigationMenuConfigurationAction.updateRootMenuItemPreferences(
			_modifiableSettings);

		Assert.assertEquals(
			"1234", _modifiableSettings.getValue("rootMenuItemId", null));
		Assert.assertNull(
			_modifiableSettings.getValue(
				"rootMenuItemExternalReferenceCode", null));
	}

	@FeatureFlags("LPD-23048")
	@Test
	public void testUpdateRootMenuItemPreferencesWithFeatureFlagEnabled()
		throws PortalException {

		_modifiableSettings.setValue("rootMenuItemId", "1234");

		_siteNavigationMenuConfigurationAction.siteNavigationMenuItemService =
			_getSiteNavigationMenuItemService("itemERC");

		_siteNavigationMenuConfigurationAction.updateRootMenuItemPreferences(
			_modifiableSettings);

		Assert.assertNull(_modifiableSettings.getValue("rootMenuItemId", null));
		Assert.assertEquals(
			"itemERC",
			_modifiableSettings.getValue(
				"rootMenuItemExternalReferenceCode", null));
	}

	@Test
	public void testUpdateSiteNavigationMenuPreferencesWithFeatureFlagDisabled()
		throws PortalException {

		_modifiableSettings.setValue("siteNavigationMenuId", "1234");

		_siteNavigationMenuConfigurationAction.siteNavigationMenuService =
			_getSiteNavigationMenuService("menuERC");

		_siteNavigationMenuConfigurationAction.
			updateSiteNavigationMenuPreferences(_modifiableSettings);

		Assert.assertEquals(
			"1234", _modifiableSettings.getValue("siteNavigationMenuId", null));
		Assert.assertNull(
			_modifiableSettings.getValue(
				"siteNavigationMenuExternalReferenceCode", null));
	}

	@FeatureFlags("LPD-23048")
	@Test
	public void testUpdateSiteNavigationMenuPreferencesWithFeatureFlagEnabled()
		throws PortalException {

		_modifiableSettings.setValue("siteNavigationMenuId", "1234");

		_siteNavigationMenuConfigurationAction.siteNavigationMenuService =
			_getSiteNavigationMenuService("menuERC");

		_siteNavigationMenuConfigurationAction.
			updateSiteNavigationMenuPreferences(_modifiableSettings);

		Assert.assertNull(
			_modifiableSettings.getValue("siteNavigationMenuId", null));
		Assert.assertEquals(
			"menuERC",
			_modifiableSettings.getValue(
				"siteNavigationMenuExternalReferenceCode", null));
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

	private PortletRequest _getPortletRequest(String groupKey) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupKey()
		).thenReturn(
			groupKey
		);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			group
		);

		PortletRequest portletRequest = Mockito.mock(PortletRequest.class);

		Mockito.when(
			portletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		return portletRequest;
	}

	private SiteNavigationMenuItemService _getSiteNavigationMenuItemService(
			String siteNavigationMenuItemExternalReferenceCode)
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem = Mockito.mock(
			SiteNavigationMenuItem.class);

		Mockito.when(
			siteNavigationMenuItem.getExternalReferenceCode()
		).thenReturn(
			siteNavigationMenuItemExternalReferenceCode
		);

		SiteNavigationMenuItemService siteNavigationMenuItemService =
			Mockito.mock(SiteNavigationMenuItemService.class);

		Mockito.when(
			siteNavigationMenuItemService.fetchSiteNavigationMenuItem(
				Mockito.anyLong())
		).thenReturn(
			siteNavigationMenuItem
		);

		return siteNavigationMenuItemService;
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

	private ModifiableSettings _modifiableSettings;
	private Map<String, String> _portletPropertiesMap;
	private SiteNavigationMenuConfigurationAction
		_siteNavigationMenuConfigurationAction;

}