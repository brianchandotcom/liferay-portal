/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.dao.search;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jürgen Kappler
 */
public class JournalRecentArticlesResultRowSplitterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		LanguageUtil languageUtil = new LanguageUtil();

		Mockito.when(
			_language.get(Mockito.any(Locale.class), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArguments()[1]
		);

		languageUtil.setLanguage(_language);
	}

	@Test
	public void testSplitMultipleSections() {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		LocalDateTime localDateTime = _toLocalDateTime(
			new Date(), ZoneId.of("UTC"));

		JournalArticle todayJournalArticle = _getJournalArticle(
			0, localDateTime);
		JournalArticle oneDayAgoJournalArticle = _getJournalArticle(
			1, localDateTime);
		JournalArticle sevenDaysAgoJournalArticle = _getJournalArticle(
			7, localDateTime);
		JournalArticle eightDaysAgoJournalArticle = _getJournalArticle(
			8, localDateTime);
		JournalArticle thirtyDaysAgoJournalArticle = _getJournalArticle(
			30, localDateTime);
		JournalArticle thirtyOneDaysAgoJournalArticle = _getJournalArticle(
			31, localDateTime);

		List<ResultRow> resultRows = Arrays.asList(
			_getResultRow(todayJournalArticle),
			_getResultRow(oneDayAgoJournalArticle),
			_getResultRow(sevenDaysAgoJournalArticle),
			_getResultRow(eightDaysAgoJournalArticle),
			_getResultRow(thirtyDaysAgoJournalArticle),
			_getResultRow(thirtyOneDaysAgoJournalArticle));

		JournalRecentArticlesResultRowSplitter
			journalRecentArticlesResultRowSplitter =
				new JournalRecentArticlesResultRowSplitter(themeDisplay);

		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			journalRecentArticlesResultRowSplitter.split(resultRows);

		Assert.assertEquals(
			resultRowSplitterEntries.toString(), 4,
			resultRowSplitterEntries.size());

		_assertResultRowSplitterEntry(
			"today", resultRowSplitterEntries.get(0), themeDisplay,
			todayJournalArticle);
		_assertResultRowSplitterEntry(
			"last-7-days", resultRowSplitterEntries.get(1), themeDisplay,
			oneDayAgoJournalArticle, sevenDaysAgoJournalArticle);
		_assertResultRowSplitterEntry(
			"last-30-days", resultRowSplitterEntries.get(2), themeDisplay,
			eightDaysAgoJournalArticle, thirtyDaysAgoJournalArticle);
		_assertResultRowSplitterEntry(
			"older", resultRowSplitterEntries.get(3), themeDisplay,
			thirtyOneDaysAgoJournalArticle);
	}

	@Test
	public void testSplitSingleSection() {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		JournalArticle thirtyOneDaysAgoJournalArticle = _getJournalArticle(
			31, _toLocalDateTime(new Date(), ZoneId.of("UTC")));

		List<ResultRow> resultRows = Arrays.asList(
			_getResultRow(thirtyOneDaysAgoJournalArticle));

		JournalRecentArticlesResultRowSplitter
			journalRecentArticlesResultRowSplitter =
				new JournalRecentArticlesResultRowSplitter(themeDisplay);

		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			journalRecentArticlesResultRowSplitter.split(resultRows);

		Assert.assertEquals(
			resultRowSplitterEntries.toString(), 1,
			resultRowSplitterEntries.size());

		_assertResultRowSplitterEntry(
			"older", resultRowSplitterEntries.get(0), themeDisplay,
			thirtyOneDaysAgoJournalArticle);
	}

	private void _assertResultRowSplitterEntry(
		String key, ResultRowSplitterEntry resultRowSplitterEntry,
		ThemeDisplay themeDisplay, JournalArticle... expectedJournalArticles) {

		Assert.assertEquals(
			_language.get(themeDisplay.getLocale(), key),
			resultRowSplitterEntry.getTitle());

		List<ResultRow> resultRows = resultRowSplitterEntry.getResultRows();

		Assert.assertEquals(
			resultRows.toString(), expectedJournalArticles.length,
			resultRows.size());

		for (int i = 0; i < resultRows.size(); i++) {
			ResultRow resultRow = resultRows.get(i);

			Assert.assertEquals(
				expectedJournalArticles[i], resultRow.getObject());
		}
	}

	private JournalArticle _getJournalArticle(
		int days, LocalDateTime localDateTime) {

		JournalArticle journalArticle = Mockito.mock(JournalArticle.class);

		LocalDateTime createLocalDateTime = localDateTime.minusDays(days);

		Mockito.when(
			journalArticle.getCreateDate()
		).thenReturn(
			Date.from(createLocalDateTime.toInstant(ZoneOffset.UTC))
		);

		return journalArticle;
	}

	private ResultRow _getResultRow(JournalArticle journalArticle) {
		ResultRow resultRow = Mockito.mock(ResultRow.class);

		Mockito.when(
			resultRow.getObject()
		).thenReturn(
			journalArticle
		);

		return resultRow;
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.getDefault()
		);

		Mockito.when(
			themeDisplay.getTimeZone()
		).thenReturn(
			TimeZone.getTimeZone(ZoneId.of("UTC"))
		);

		User user = _getUser();

		Mockito.when(
			themeDisplay.getUser()
		).thenReturn(
			user
		);

		return themeDisplay;
	}

	private User _getUser() {
		User user = Mockito.mock(User.class);

		Mockito.when(
			user.getTimeZoneId()
		).thenReturn(
			"CET"
		);

		return user;
	}

	private LocalDateTime _toLocalDateTime(Date date, ZoneId zoneId) {
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(zoneId);

		return zonedDateTime.toLocalDateTime();
	}

	private final Language _language = Mockito.mock(Language.class);

}