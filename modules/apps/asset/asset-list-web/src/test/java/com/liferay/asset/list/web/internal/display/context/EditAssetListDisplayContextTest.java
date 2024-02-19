/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class EditAssetListDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_assetRendererFactoryRegistryUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_httpServletRequest = Mockito.mock(HttpServletRequest.class);

		_themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			_themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		_portletRequest = Mockito.mock(PortletRequest.class);

		_setUpLanguageUtil();
		_setUpPortal();
	}

	@Test
	public void testGetActionDropdownItems() throws Exception {
		String className = RandomTestUtil.randomString();
		long classNameId = RandomTestUtil.randomLong();
		String expectedLabel = RandomTestUtil.randomString();

		_setUpAssetRendererFactoryRegistryUtil(
			className, classNameId, expectedLabel);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"classNameIds", classNameId
		).put(
			"selectionStyle", "manual"
		).build();

		_setUpAssetListEntryLocalService(
			StringPool.BLANK, className, unicodeProperties.toString());

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		List<DropdownItem> dropdownItems =
			editAssetListDisplayContext.getActionDropdownItems();

		Assert.assertEquals(dropdownItems.toString(), 1, dropdownItems.size());

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals(expectedLabel, dropdownItem.get("label"));
	}

	@Test
	public void testGetActionDropdownItemsNoAvailableClassNameId()
		throws Exception {

		long classNameId = RandomTestUtil.randomLong();

		_setUpAssetRendererFactoryRegistryUtil(null, classNameId, null);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"classNameIds", classNameId
		).put(
			"selectionStyle", "manual"
		).build();

		_setUpAssetListEntryLocalService(
			StringPool.BLANK, RandomTestUtil.randomString(),
			unicodeProperties.toString());

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		List<DropdownItem> dropdownItems =
			editAssetListDisplayContext.getActionDropdownItems();

		Assert.assertEquals(dropdownItems.toString(), 0, dropdownItems.size());
	}

	@Test
	public void testGetClassNameIdsDynamicSelection() {
		long classNameId = RandomTestUtil.randomLong();

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"anyAssetType", classNameId
		).put(
			"selectionStyle", "dynamic"
		).build();

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		Assert.assertArrayEquals(
			new long[] {classNameId},
			editAssetListDisplayContext.getClassNameIds(
				unicodeProperties,
				new long[] {
					RandomTestUtil.randomLong(), classNameId,
					RandomTestUtil.randomLong()
				}));
	}

	@Test
	public void testGetClassNameIdsDynamicSelectionNoAvailableClassNameId() {
		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"anyAssetType", RandomTestUtil.randomLong()
		).put(
			"selectionStyle", "dynamic"
		).build();

		EditAssetListDisplayContext editAssetListDisplayContext =
			_getEditAssetListDisplayContext(unicodeProperties);

		Assert.assertArrayEquals(
			new long[0],
			editAssetListDisplayContext.getClassNameIds(
				unicodeProperties,
				new long[] {
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomLong()
				}));
	}

	private EditAssetListDisplayContext _getEditAssetListDisplayContext(
		UnicodeProperties unicodeProperties) {

		return new EditAssetListDisplayContext(
			Mockito.mock(AssetRendererFactoryClassProvider.class),
			Mockito.mock(InfoSearchClassMapperRegistry.class),
			Mockito.mock(ItemSelector.class), _portletRequest,
			Mockito.mock(PortletResponse.class),
			Mockito.mock(SegmentsConfigurationProvider.class),
			unicodeProperties);
	}

	private void _setUpAssetListEntryLocalService(
		String assetEntrySubtype, String assetEntryType, String typeSettings) {

		AssetListEntry assetListEntry = Mockito.mock(AssetListEntry.class);

		Mockito.when(
			assetListEntry.getAssetEntrySubtype()
		).thenReturn(
			assetEntrySubtype
		);
		Mockito.when(
			assetListEntry.getAssetEntryType()
		).thenReturn(
			assetEntryType
		);
		Mockito.when(
			assetListEntry.getTypeSettings(Mockito.anyLong())
		).thenReturn(
			typeSettings
		);

		AssetListEntryLocalService assetListEntryLocalService = Mockito.mock(
			AssetListEntryLocalService.class);

		Mockito.when(
			assetListEntryLocalService.fetchAssetListEntry(Mockito.anyLong())
		).thenReturn(
			assetListEntry
		);

		AssetListEntryLocalServiceUtil assetListEntryLocalServiceUtil =
			new AssetListEntryLocalServiceUtil();

		assetListEntryLocalServiceUtil.setService(assetListEntryLocalService);
	}

	private void _setUpAssetRendererFactoryRegistryUtil(
		String className, long classNameId, String typeName) {

		AssetRendererFactory<?> assetRendererFactory = null;

		if (className != null) {
			assetRendererFactory = Mockito.mock(AssetRendererFactory.class);

			Mockito.when(
				assetRendererFactory.isActive(Mockito.anyLong())
			).thenReturn(
				true
			);
			Mockito.when(
				assetRendererFactory.getClassName()
			).thenReturn(
				className
			);
			Mockito.when(
				assetRendererFactory.isSelectable()
			).thenReturn(
				true
			);
			Mockito.when(
				assetRendererFactory.getTypeName(LocaleUtil.US)
			).thenReturn(
				typeName
			);
		}

		_assetRendererFactoryRegistryUtilMockedStatic.when(
			() ->
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(classNameId)
		).thenReturn(
			assetRendererFactory
		);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private void _setUpPortal() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getHttpServletRequest(_portletRequest)
		).thenReturn(
			_httpServletRequest
		);

		portalUtil.setPortal(portal);
	}

	private static final MockedStatic<AssetRendererFactoryRegistryUtil>
		_assetRendererFactoryRegistryUtilMockedStatic = Mockito.mockStatic(
			AssetRendererFactoryRegistryUtil.class);

	private HttpServletRequest _httpServletRequest;
	private PortletRequest _portletRequest;
	private ThemeDisplay _themeDisplay;

}