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

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
		MockitoAnnotations.initMocks(this);

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

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);

		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(_beanProperties);
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
			_UNEXPECTED_FOLDER_NAME, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());
		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_language
		).get(
			_httpServletRequest, "home"
		);

		Mockito.verifyZeroInteractions(_journalFolderLocalService);
	}

	@Test
	public void testGetFolderNameSpecificFolderId() throws Exception {
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
			_UNEXPECTED_FOLDER_NAME, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());
		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());

		Mockito.verify(
			_journalFolderLocalService
		).fetchJournalFolder(
			folderId
		);

		Mockito.verifyZeroInteractions(_language);
	}

	@Test
	public void testGetFolderNameSpecificFolderIdFolderNotFound()
		throws Exception {

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
			_UNEXPECTED_FOLDER_NAME, expectedResult,
			_journalEditArticleDisplayContext.getFolderName());
		Assert.assertEquals(
			_UNEXPECTED_FOLDER_NAME, expectedResult,
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
		Assert.assertFalse(
			_journalEditArticleDisplayContext.isShowSelectFolder());

		Mockito.verify(
			_httpServletRequest, Mockito.never()
		).getParameter(
			"showSelectFolder"
		);
	}

	private static final String _UNEXPECTED_FOLDER_NAME =
		"Unexpected folder name";

	@Mock
	private BeanProperties _beanProperties;

	@Mock
	private HttpServletRequest _httpServletRequest;

	@Mock
	private JournalArticle _journalArticle;

	private JournalEditArticleDisplayContext _journalEditArticleDisplayContext;

	@Mock
	private JournalFolderLocalService _journalFolderLocalService;

	@Mock
	private Language _language;

	@Mock
	private LiferayPortletResponse _liferayPortletResponse;

	@Mock
	private ThemeDisplay _themeDisplay;

}