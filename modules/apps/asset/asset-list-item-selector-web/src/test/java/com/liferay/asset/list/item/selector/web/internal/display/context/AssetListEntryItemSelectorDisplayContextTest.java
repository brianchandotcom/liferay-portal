/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.list.item.selector.web.internal.display.context;

import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jürgen Kappler
 */
public class AssetListEntryItemSelectorDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetInfoItemClassNames() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Mockito.when(
			(ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		InfoItemServiceRegistry infoItemServiceRegistry = Mockito.mock(
			InfoItemServiceRegistry.class);

		Mockito.when(
			infoItemServiceRegistry.getInfoItemClassNames(
				InfoItemFormProvider.class)
		).thenReturn(
			Arrays.asList("className1", "className2")
		);

		InfoSearchClassMapperRegistry infoSearchClassMapperRegistry =
			Mockito.mock(InfoSearchClassMapperRegistry.class);

		Mockito.when(
			infoSearchClassMapperRegistry.getSearchClassName("className1")
		).thenReturn(
			"searchClassName1"
		);

		Mockito.when(
			infoSearchClassMapperRegistry.getSearchClassName("className2")
		).thenReturn(
			"className2"
		);

		AssetListEntryItemSelectorDisplayContext
			assetListEntryItemSelectorDisplayContext =
				new AssetListEntryItemSelectorDisplayContext(
					httpServletRequest, infoItemServiceRegistry,
					infoSearchClassMapperRegistry, null, null, null);

		String[] infoItemClassNames = ReflectionTestUtil.invoke(
			assetListEntryItemSelectorDisplayContext, "_getInfoItemClassNames",
			new Class<?>[0]);

		Assert.assertEquals(
			Arrays.toString(infoItemClassNames), 3, infoItemClassNames.length);

		Assert.assertTrue(ArrayUtil.contains(infoItemClassNames, "className1"));
		Assert.assertTrue(ArrayUtil.contains(infoItemClassNames, "className2"));
		Assert.assertTrue(
			ArrayUtil.contains(infoItemClassNames, "searchClassName1"));
	}

}