/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class EditAssetListDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_httpServletRequest = Mockito.mock(HttpServletRequest.class);

		_themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		_portletRequest = Mockito.mock(PortletRequest.class);

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getHttpServletRequest(_portletRequest)
		).thenReturn(
			_httpServletRequest
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);
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

	private HttpServletRequest _httpServletRequest;
	private PortletRequest _portletRequest;
	private ThemeDisplay _themeDisplay;

}