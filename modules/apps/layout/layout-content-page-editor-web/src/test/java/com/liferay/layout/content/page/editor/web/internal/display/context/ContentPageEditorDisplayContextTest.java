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

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.search.InfoSearchClassMapperTracker;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jürgen Kappler
 */
public class ContentPageEditorDisplayContextTest {

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

		InfoItemServiceTracker infoItemServiceTracker = Mockito.mock(
			InfoItemServiceTracker.class);

		Mockito.when(
			infoItemServiceTracker.getInfoItemClassNames(
				InfoItemFormProvider.class)
		).thenReturn(
			Arrays.asList("className1", "className2")
		);

		InfoSearchClassMapperTracker infoSearchClassMapperTracker =
			Mockito.mock(InfoSearchClassMapperTracker.class);

		Mockito.when(
			infoSearchClassMapperTracker.getSearchClassName("className1")
		).thenReturn(
			"searchClassName1"
		);

		Mockito.when(
			infoSearchClassMapperTracker.getSearchClassName("className2")
		).thenReturn(
			"className2"
		);

		ContentPageEditorDisplayContext contentPageEditorDisplayContext =
			new ContentPageEditorDisplayContext(
				null, null, null, null, httpServletRequest,
				infoItemServiceTracker, infoSearchClassMapperTracker, null,
				null, null, null, null, null, null);

		List<String> infoItemClassNames = ReflectionTestUtil.invoke(
			contentPageEditorDisplayContext, "_getInfoItemClassNames",
			new Class<?>[0]);

		Assert.assertEquals(
			infoItemClassNames.toString(), 3, infoItemClassNames.size());

		Assert.assertTrue(infoItemClassNames.contains("className1"));
		Assert.assertTrue(infoItemClassNames.contains("className2"));
		Assert.assertTrue(infoItemClassNames.contains("searchClassName1"));
	}

}