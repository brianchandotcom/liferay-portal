/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutLookAndFeelDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpLanguageUtil();

		Mockito.when(
			_layoutsAdminDisplayContext.getSelLayout()
		).thenReturn(
			_layout
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_defaultStyleBookEntryUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(
			_httpServletRequest, _layoutsAdminDisplayContext, null);
	}

	@Test
	@TestInfo("LPD-45193")
	public void testGetStyleBookEntryNameWithStyleBookEntry() {
		String name = RandomTestUtil.randomString();

		_setUpDefaultStyleBookEntryUtil(name);

		Mockito.when(
			_layout.getStyleBookEntryId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Assert.assertEquals(
			name, _layoutLookAndFeelDisplayContext.getStyleBookEntryName());

		_defaultStyleBookEntryUtilMockedStatic.when(
			() -> DefaultStyleBookEntryUtil.getDefaultStyleBookEntry(_layout)
		).thenReturn(
			null
		);

		Assert.assertEquals(
			"styles-from-theme",
			_layoutLookAndFeelDisplayContext.getStyleBookEntryName());
	}

	private static void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenAnswer(
			(Answer<String>)invocationOnMock -> invocationOnMock.getArgument(
				1, String.class)
		);

		languageUtil.setLanguage(language);
	}

	private void _setUpDefaultStyleBookEntryUtil(String name) {
		StyleBookEntry styleBookEntry = Mockito.mock(StyleBookEntry.class);

		Mockito.when(
			styleBookEntry.getName()
		).thenReturn(
			name
		);

		_defaultStyleBookEntryUtilMockedStatic.when(
			() -> DefaultStyleBookEntryUtil.getDefaultStyleBookEntry(_layout)
		).thenReturn(
			styleBookEntry
		);
	}

	private static final MockedStatic<DefaultStyleBookEntryUtil>
		_defaultStyleBookEntryUtilMockedStatic = Mockito.mockStatic(
			DefaultStyleBookEntryUtil.class);
	private static final HttpServletRequest _httpServletRequest =
		new MockHttpServletRequest();
	private static final Layout _layout = Mockito.mock(Layout.class);
	private static final LayoutsAdminDisplayContext
		_layoutsAdminDisplayContext = Mockito.mock(
			LayoutsAdminDisplayContext.class);

	private LayoutLookAndFeelDisplayContext _layoutLookAndFeelDisplayContext;

}