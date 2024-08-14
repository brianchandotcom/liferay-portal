/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.portlet.action;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetPublisherConfigurationActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testUpdateAssetListEntryPreferences() throws Exception {
		AssetListEntry assetListEntry = _getAssetListEntry();

		Group group = _getGroup(assetListEntry.getGroupId());

		AssetPublisherConfigurationAction assetPublisherConfigurationAction =
			_getAssetPublisherConfigurationAction(assetListEntry, group);

		Map<String, String[]> portletPreferencesMap = new HashMap<>();
		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		assetPublisherConfigurationAction.updateAssetListEntryPreferences(
			_getMockActionRequest(
				assetListEntry.getExternalReferenceCode(),
				assetListEntry.getAssetListEntryId(),
				group.getExternalReferenceCode(), portletPreferencesMap, null),
			portletPreferences);

		Mockito.verifyNoInteractions(
			assetPublisherConfigurationAction.assetListEntryLocalService,
			assetPublisherConfigurationAction.groupLocalService,
			portletPreferences);

		Assert.assertTrue(MapUtil.isEmpty(portletPreferencesMap));
	}

	@FeatureFlags("LPD-22837")
	@Test
	public void testUpdateAssetListEntryPreferencesWithFeatureWithDifferentGroup()
		throws Exception {

		AssetListEntry assetListEntry = _getAssetListEntry();

		Group group = _getGroup(assetListEntry.getGroupId());

		AssetPublisherConfigurationAction assetPublisherConfigurationAction =
			_getAssetPublisherConfigurationAction(assetListEntry, group);

		Map<String, String[]> portletPreferencesMap = new HashMap<>();
		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		assetPublisherConfigurationAction.updateAssetListEntryPreferences(
			_getMockActionRequest(
				assetListEntry.getExternalReferenceCode(),
				assetListEntry.getAssetListEntryId(),
				group.getExternalReferenceCode(), portletPreferencesMap,
				_getThemeDisplay(RandomTestUtil.randomLong())),
			portletPreferences);

		Mockito.verify(
			assetPublisherConfigurationAction.assetListEntryLocalService
		).fetchAssetListEntry(
			assetListEntry.getAssetListEntryId()
		);
		Mockito.verify(
			assetPublisherConfigurationAction.groupLocalService
		).getGroup(
			assetListEntry.getGroupId()
		);

		Assert.assertArrayEquals(
			MapUtil.toString(portletPreferencesMap),
			new String[] {assetListEntry.getExternalReferenceCode()},
			portletPreferencesMap.get("assetListEntryExternalReferenceCode"));
		Assert.assertArrayEquals(
			MapUtil.toString(portletPreferencesMap),
			new String[] {group.getExternalReferenceCode()},
			portletPreferencesMap.get(
				"assetListEntryGroupExternalReferenceCode"));
	}

	@FeatureFlags("LPD-22837")
	@Test
	public void testUpdateAssetListEntryPreferencesWithFeatureWithSameGroup()
		throws Exception {

		AssetListEntry assetListEntry = _getAssetListEntry();

		Group group = _getGroup(assetListEntry.getGroupId());

		AssetPublisherConfigurationAction assetPublisherConfigurationAction =
			_getAssetPublisherConfigurationAction(assetListEntry, group);

		Map<String, String[]> portletPreferencesMap = new HashMap<>();
		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		assetPublisherConfigurationAction.updateAssetListEntryPreferences(
			_getMockActionRequest(
				assetListEntry.getExternalReferenceCode(),
				assetListEntry.getAssetListEntryId(),
				group.getExternalReferenceCode(), portletPreferencesMap,
				_getThemeDisplay(assetListEntry.getGroupId())),
			portletPreferences);

		Mockito.verifyNoInteractions(
			assetPublisherConfigurationAction.groupLocalService);
		Mockito.verify(
			portletPreferences
		).reset(
			"assetListEntryGroupExternalReferenceCode"
		);

		Assert.assertArrayEquals(
			MapUtil.toString(portletPreferencesMap),
			new String[] {assetListEntry.getExternalReferenceCode()},
			portletPreferencesMap.get("assetListEntryExternalReferenceCode"));
		Assert.assertFalse(
			MapUtil.toString(portletPreferencesMap),
			portletPreferencesMap.containsKey(
				"assetListEntryGroupExternalReferenceCode"));
	}

	@FeatureFlags("LPD-22837")
	@Test
	public void testUpdateAssetListEntryPreferencesWithNoSelection()
		throws Exception {

		AssetPublisherConfigurationAction assetPublisherConfigurationAction =
			_getAssetPublisherConfigurationAction(null, null);

		Map<String, String[]> portletPreferencesMap = new HashMap<>();
		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		assetPublisherConfigurationAction.updateAssetListEntryPreferences(
			_getMockActionRequest(
				StringPool.BLANK, 0, StringPool.BLANK, portletPreferencesMap,
				_getThemeDisplay(RandomTestUtil.randomLong())),
			portletPreferences);

		Mockito.verify(
			assetPublisherConfigurationAction.assetListEntryLocalService
		).fetchAssetListEntry(
			0
		);
		Mockito.verifyNoInteractions(
			assetPublisherConfigurationAction.groupLocalService);
		Mockito.verify(
			portletPreferences
		).reset(
			"assetListEntryExternalReferenceCode"
		);
		Mockito.verify(
			portletPreferences
		).reset(
			"assetListEntryGroupExternalReferenceCode"
		);

		Assert.assertTrue(
			MapUtil.toString(portletPreferencesMap),
			MapUtil.isEmpty(portletPreferencesMap));
	}

	@Test
	public void testUpdateDisplayStyleGroupPreferences() throws Exception {
		AssetPublisherConfigurationAction assetPublisherConfigurationAction =
			_getAssetPublisherConfigurationAction(null, null);

		Map<String, String[]> portletPreferencesMap = new HashMap<>();
		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		assetPublisherConfigurationAction.updateDisplayStyleGroupPreferences(
			_getMockActionRequest(
				StringPool.BLANK, 0, StringPool.BLANK, portletPreferencesMap,
				_getThemeDisplay(RandomTestUtil.randomLong())),
			portletPreferences);

		Mockito.verifyNoInteractions(
			assetPublisherConfigurationAction.groupLocalService,
			portletPreferences);

		Assert.assertTrue(MapUtil.isEmpty(portletPreferencesMap));
	}

	private AssetListEntry _getAssetListEntry() {
		AssetListEntry assetListEntry = Mockito.mock(AssetListEntry.class);

		Mockito.when(
			assetListEntry.getExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);
		Mockito.when(
			assetListEntry.getAssetListEntryId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);
		Mockito.when(
			assetListEntry.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return assetListEntry;
	}

	private AssetListEntryLocalService _getAssetListEntryLocalService(
		AssetListEntry assetListEntry) {

		AssetListEntryLocalService assetListEntryLocalService = Mockito.mock(
			AssetListEntryLocalService.class);

		if (assetListEntry == null) {
			Mockito.when(
				assetListEntryLocalService.fetchAssetListEntry(
					Mockito.anyLong())
			).thenReturn(
				null
			);
		}
		else {
			Mockito.when(
				assetListEntryLocalService.fetchAssetListEntry(
					assetListEntry.getAssetListEntryId())
			).thenReturn(
				assetListEntry
			);
		}

		return assetListEntryLocalService;
	}

	private AssetPublisherConfigurationAction
			_getAssetPublisherConfigurationAction(
				AssetListEntry assetListEntry, Group group)
		throws Exception {

		AssetPublisherConfigurationAction assetPublisherConfigurationAction =
			new AssetPublisherConfigurationAction();

		assetPublisherConfigurationAction.assetListEntryLocalService =
			_getAssetListEntryLocalService(assetListEntry);
		assetPublisherConfigurationAction.groupLocalService =
			_getGroupLocalService(group);

		return assetPublisherConfigurationAction;
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

		return group;
	}

	private GroupLocalService _getGroupLocalService(Group group)
		throws Exception {

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		if (group == null) {
			Mockito.when(
				groupLocalService.getGroup(Mockito.anyLong())
			).thenReturn(
				null
			);
		}
		else {
			Mockito.when(
				groupLocalService.getGroup(group.getGroupId())
			).thenReturn(
				group
			);
		}

		return groupLocalService;
	}

	private MockActionRequest _getMockActionRequest(
		String assetListEntryExternalReferenceCode, long assetListEntryId,
		String assetListEntryGroupExternalReferenceCode,
		Map<String, String[]> portletPreferencesMap,
		ThemeDisplay themeDisplay) {

		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.setAttribute(
			WebKeys.PORTLET_PREFERENCES_MAP, portletPreferencesMap);
		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		mockActionRequest.setParameter(
			"preferences--assetListEntryExternalReferenceCode--",
			assetListEntryExternalReferenceCode);
		mockActionRequest.setParameter(
			"preferences--assetListEntryId--",
			String.valueOf(assetListEntryId));
		mockActionRequest.setParameter(
			"preferences--assetListEntryGroupExternalReferenceCode--",
			assetListEntryGroupExternalReferenceCode);

		return mockActionRequest;
	}

	private ThemeDisplay _getThemeDisplay(long groupId) {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setScopeGroupId(groupId);

		return themeDisplay;
	}

}