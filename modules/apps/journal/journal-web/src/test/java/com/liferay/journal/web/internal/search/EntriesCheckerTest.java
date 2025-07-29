/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.search;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jan Brychta
 */
public class EntriesCheckerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetRowCheckBoxForNoJournalArticlePermissions() {
		EntriesChecker entriesChecker = new EntriesChecker(
			_getLiferayPortletRequest(), _getLiferayPortletResponse());

		try (MockedStatic<JournalArticleLocalServiceUtil>
				articleLocalServiceUtilMockedStatic = Mockito.mockStatic(
					JournalArticleLocalServiceUtil.class);
			MockedStatic<LanguageUtil> languageUtilMockedStatic =
				Mockito.mockStatic(LanguageUtil.class);
			MockedStatic<JournalArticlePermission>
				journalArticlePermissionMockedStatic = Mockito.mockStatic(
					JournalArticlePermission.class)) {

			JournalArticle mockArticle = Mockito.mock(JournalArticle.class);

			Mockito.when(
				mockArticle.getArticleId()
			).thenReturn(
				_ARTICLE_ID
			);

			articleLocalServiceUtilMockedStatic.when(
				() -> JournalArticleLocalServiceUtil.fetchArticle(
					_SCOPE_GROUP_ID, _ARTICLE_ID)
			).thenReturn(
				mockArticle
			);

			languageUtilMockedStatic.when(
				() -> LanguageUtil.get(
					Mockito.any(Locale.class), Mockito.eq("select"))
			).thenReturn(
				"Select (mocked)"
			);

			journalArticlePermissionMockedStatic.when(
				() -> JournalArticlePermission.contains(
					ArgumentMatchers.any(PermissionChecker.class),
					ArgumentMatchers.any(JournalArticle.class),
					ArgumentMatchers.anyString())
			).thenReturn(
				false
			);

			String rowCheckBox = entriesChecker.getRowCheckBox(
				_getHttpServletRequest(), false, false, _ARTICLE_ID);

			Assert.assertTrue((rowCheckBox != null) && !rowCheckBox.isEmpty());
		}
	}

	private HttpServletRequest _getHttpServletRequest() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_getThemeDisplay()
		);

		return httpServletRequest;
	}

	private LiferayPortletRequest _getLiferayPortletRequest() {
		LiferayPortletRequest liferayPortletRequest = Mockito.mock(
			LiferayPortletRequest.class);

		ThemeDisplay themeDisplay = _getThemeDisplay();

		Mockito.when(
			liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		return liferayPortletRequest;
	}

	private LiferayPortletResponse _getLiferayPortletResponse() {
		return Mockito.mock(LiferayPortletResponse.class);
	}

	private PermissionChecker _getPermissionChecker() {
		return Mockito.mock(PermissionChecker.class);
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPermissionChecker(_getPermissionChecker());
		themeDisplay.setScopeGroupId(_SCOPE_GROUP_ID);

		return themeDisplay;
	}

	private static final String _ARTICLE_ID = "1234";

	private static final long _SCOPE_GROUP_ID = 0;

}