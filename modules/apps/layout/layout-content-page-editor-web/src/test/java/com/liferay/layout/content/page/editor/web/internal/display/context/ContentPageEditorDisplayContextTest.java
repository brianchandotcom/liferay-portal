/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.design.library.util.DesignLibraryUtil;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.StyleBookEntryProviderUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Gabriel Lima
 */
public class ContentPageEditorDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_designLibraryUtilMockedStatic.close();
		_styleBookEntryProviderUtilMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-104844")
	public void testGetBackURL() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_contentPageEditorDisplayContext, "httpServletRequest",
			_httpServletRequest);

		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);

		ReflectionTestUtil.setFieldValue(
			_contentPageEditorDisplayContext, "themeDisplay", _themeDisplay);

		_testGetBackURLWithDesignLibraryScope();
		_testGetBackURLWithoutDesignLibraryScope();
	}

	@Test
	public void testGetStyleBookEntryERC() throws Exception {
		ContentPageEditorDisplayContext contentPageEditorDisplayContext =
			Mockito.mock(ContentPageEditorDisplayContext.class);

		ReflectionTestUtil.setFieldValue(
			contentPageEditorDisplayContext, "_frontendTokenDefinitionRegistry",
			Mockito.mock(FrontendTokenDefinitionRegistry.class));

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			themeDisplay.getLayout()
		).thenReturn(
			layout
		);

		ReflectionTestUtil.setFieldValue(
			contentPageEditorDisplayContext, "themeDisplay", themeDisplay);

		_styleBookEntryProviderUtilMockedStatic.when(
			() -> StyleBookEntryProviderUtil.getStyleBookEntry(layout)
		).thenReturn(
			Mockito.mock(StyleBookEntry.class)
		);

		Assert.assertEquals(
			StringPool.BLANK,
			ReflectionTestUtil.invoke(
				contentPageEditorDisplayContext, "_getStyleBookEntryERC",
				new Class<?>[0]));
	}

	private void _testGetBackURLWithDesignLibraryScope() {
		_designLibraryUtilMockedStatic.when(
			() -> DesignLibraryUtil.isDesignLibraryScope(_group)
		).thenReturn(
			true
		);

		String designLibraryResourcesURL = RandomTestUtil.randomString();

		_designLibraryUtilMockedStatic.when(
			() -> DesignLibraryUtil.getDesignLibraryResourcesURL(
				_group, _httpServletRequest)
		).thenReturn(
			designLibraryResourcesURL
		);

		Assert.assertEquals(
			designLibraryResourcesURL,
			ReflectionTestUtil.invoke(
				_contentPageEditorDisplayContext, "_getBackURL",
				new Class<?>[0]));
	}

	private void _testGetBackURLWithoutDesignLibraryScope() {
		_designLibraryUtilMockedStatic.when(
			() -> DesignLibraryUtil.isDesignLibraryScope(_group)
		).thenReturn(
			false
		);

		String urlCurrent = RandomTestUtil.randomString();

		Mockito.when(
			_themeDisplay.getURLCurrent()
		).thenReturn(
			urlCurrent
		);

		Assert.assertEquals(
			urlCurrent,
			ReflectionTestUtil.invoke(
				_contentPageEditorDisplayContext, "_getBackURL",
				new Class<?>[0]));
	}

	private static final MockedStatic<DesignLibraryUtil>
		_designLibraryUtilMockedStatic = Mockito.mockStatic(
			DesignLibraryUtil.class);
	private static final MockedStatic<StyleBookEntryProviderUtil>
		_styleBookEntryProviderUtilMockedStatic = Mockito.mockStatic(
			StyleBookEntryProviderUtil.class);

	private final ContentPageEditorDisplayContext
		_contentPageEditorDisplayContext = Mockito.mock(
			ContentPageEditorDisplayContext.class);
	private final Group _group = Mockito.mock(Group.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}