/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.display.context;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
public class JournalEditArticleDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		Mockito.when(
			_httpServletRequest.getParameter("showHeader")
		).thenReturn(
			"false"
		);

		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(_beanProperties);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	@Test
	public void testFolderNameValueIsCached() {
		String expectedResult = "Home translation";

		Mockito.when(
			_language.get(_httpServletRequest, "home")
		).thenReturn(
			expectedResult
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null);

		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());
		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_language, Mockito.times(1)
		).get(
			_httpServletRequest, "home"
		);
	}

	@Test
	public void testGetFolderNameDefaultFolderId() {
		String expectedResult = "Home translation";

		Mockito.when(
			_language.get(_httpServletRequest, "home")
		).thenReturn(
			expectedResult
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null);

		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_language
		).get(
			_httpServletRequest, "home"
		);

		Mockito.verifyNoInteractions(_journalFolderLocalService);
	}

	@Test
	public void testGetFolderNameSpecificFolderId() {
		long folderId = 42;

		Mockito.when(
			_httpServletRequest.getParameter("folderId")
		).thenReturn(
			String.valueOf(folderId)
		);

		ReflectionTestUtil.setFieldValue(
			JournalFolderLocalServiceUtil.class, "_service",
			_journalFolderLocalService);

		JournalFolder folder = Mockito.mock(JournalFolder.class);

		String expectedResult = "Folder name";

		Mockito.when(
			folder.getName()
		).thenReturn(
			expectedResult
		);

		Mockito.when(
			_journalFolderLocalService.fetchJournalFolder(folderId)
		).thenReturn(
			folder
		);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null);

		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_journalFolderLocalService
		).fetchJournalFolder(
			folderId
		);

		Mockito.verifyNoInteractions(_language);
	}

	@Test
	public void testGetFolderNameSpecificFolderIdFolderNotFound() {
		String expectedResult = "Home translation";

		Mockito.when(
			_language.get(_httpServletRequest, "home")
		).thenReturn(
			expectedResult
		);

		long folderId = 42;

		Mockito.when(
			_httpServletRequest.getParameter("folderId")
		).thenReturn(
			String.valueOf(folderId)
		);

		ReflectionTestUtil.setFieldValue(
			JournalFolderLocalServiceUtil.class, "_service",
			_journalFolderLocalService);

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null);

		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME_MESSAGE, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_journalFolderLocalService
		).fetchJournalFolder(
			folderId
		);

		Mockito.verify(
			_language
		).get(
			_httpServletRequest, "home"
		);
	}

	@Test
	public void testIsShowSelectFolderAddActionFalseParamValue() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null);

		Mockito.when(
			_httpServletRequest.getParameter("showSelectFolder")
		).thenReturn(
			"false"
		);

		Assert.assertFalse(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest
		).getParameter(
			"showSelectFolder"
		);
	}

	@Test
	public void testIsShowSelectFolderAddActionMissingParam() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null);

		Mockito.when(
			_httpServletRequest.getParameter("showSelectFolder")
		).thenReturn(
			null
		);

		Assert.assertTrue(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest
		).getParameter(
			"showSelectFolder"
		);
	}

	@Test
	public void testIsShowSelectFolderAddActionTrueParamValue() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null);

		Mockito.when(
			_httpServletRequest.getParameter("showSelectFolder")
		).thenReturn(
			"true"
		);

		Assert.assertTrue(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest
		).getParameter(
			"showSelectFolder"
		);
	}

	@Test
	public void testIsShowSelectFolderEditActionDoesntMatterParamValue() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, _journalArticle);

		Assert.assertFalse(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest, Mockito.never()
		).getParameter(
			"showSelectFolder"
		);
	}

	@Test
	public void testShowSelectFolderValueIsCached() {
		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, _liferayPortletResponse, null);

		Mockito.when(
			_httpServletRequest.getParameter("showSelectFolder")
		).thenReturn(
			"true"
		);

		Assert.assertTrue(
			_journalEditArticleDisplayContext.isShowSelectFolder());
		Assert.assertTrue(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest, Mockito.times(1)
		).getParameter(
			"showSelectFolder"
		);
	}

	private static final String _UNEXPECTED_FOLDER_NAME_MESSAGE =
		"Unexpected folder name";

	private final BeanProperties _beanProperties = Mockito.mock(
		BeanProperties.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final JournalArticle _journalArticle = Mockito.mock(
		JournalArticle.class);
	private JournalEditArticleDisplayContext _journalEditArticleDisplayContext;
	private final JournalFolderLocalService _journalFolderLocalService =
		Mockito.mock(JournalFolderLocalService.class);
	private final Language _language = Mockito.mock(Language.class);
	private final LiferayPortletResponse _liferayPortletResponse = Mockito.mock(
		LiferayPortletResponse.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}