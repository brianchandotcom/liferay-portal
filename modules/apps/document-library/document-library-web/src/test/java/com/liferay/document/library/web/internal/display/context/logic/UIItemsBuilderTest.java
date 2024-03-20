/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context.logic;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import java.net.URI;
import java.net.URISyntaxException;

import org.assertj.core.api.AbstractUriAssert;
import org.assertj.core.api.Assertions;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marco Galluzzi
 */
public class UIItemsBuilderTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		LanguageUtil languageUtil = new LanguageUtil();
		PortalUtil portalUtil = new PortalUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
		portalUtil.setPortal(new PortalImpl());
	}

	@Before
	public void setUp() {
		Mockito.when(
			_dlurlHelper.getDownloadURL(
				Mockito.nullable(FileEntry.class),
				Mockito.nullable(FileVersion.class),
				Mockito.nullable(ThemeDisplay.class), Mockito.anyString(),
				Mockito.anyBoolean(), Mockito.anyBoolean())
		).thenReturn(
			"http://localhost/"
		);
	}

	@Test
	public void testCreateDownloadDropdownItemWithDoAsUserIdParameter()
		throws URISyntaxException {

		String doAsUserId = RandomTestUtil.randomString();
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setDoAsUserId(doAsUserId);

		UIItemsBuilder uiItemsBuilder = _getUIItemsBuilder(themeDisplay);

		DropdownItem dropdownItem = uiItemsBuilder.createDownloadDropdownItem();

		AbstractUriAssert<?> abstractUriAssert = Assertions.assertThat(
			new URI((String)dropdownItem.get("href")));

		abstractUriAssert.hasParameter("doAsUserId", doAsUserId);
	}

	@Test
	public void testCreateDownloadDropdownItemWithoutDoAsUserIdParameter()
		throws URISyntaxException {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setDoAsUserId(null);

		UIItemsBuilder uiItemsBuilder = _getUIItemsBuilder(themeDisplay);

		DropdownItem dropdownItem = uiItemsBuilder.createDownloadDropdownItem();

		AbstractUriAssert<?> abstractUriAssert = Assertions.assertThat(
			new URI((String)dropdownItem.get("href")));

		abstractUriAssert.hasNoParameter("doAsUserId");
	}

	private UIItemsBuilder _getUIItemsBuilder(ThemeDisplay themeDisplay) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return new UIItemsBuilder(
			mockHttpServletRequest, _fileEntry, _fileVersion, null, null,
			_dlurlHelper);
	}

	private final DLURLHelper _dlurlHelper = Mockito.mock(DLURLHelper.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);

}