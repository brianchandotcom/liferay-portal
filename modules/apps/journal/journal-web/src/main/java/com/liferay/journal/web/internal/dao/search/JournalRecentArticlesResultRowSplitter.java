/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.dao.search;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class JournalRecentArticlesResultRowSplitter
	implements ResultRowSplitter {

	public JournalRecentArticlesResultRowSplitter(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		User user = _themeDisplay.getUser();

		Date date = new Date();

		Calendar currentCalendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), _themeDisplay.getTimeZone());

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			currentCalendar.get(Calendar.YEAR),
			currentCalendar.get(Calendar.MONTH),
			currentCalendar.get(Calendar.DATE), 0, 0, 0, 0,
			_themeDisplay.getTimeZone());

		LocalDateTime todayLocalDateTime = _toLocalDateTime(
			calendar.getTime(), ZoneId.of(user.getTimeZoneId()));

		LocalDateTime sevenDaysAgoLocalDateTime = todayLocalDateTime.minusDays(
			7);
		LocalDateTime thirtyDaysAgoLocalDateTime = todayLocalDateTime.minusDays(
			30);

		List<ResultRow> todayJournalArticleResultRows = new ArrayList<>();
		List<ResultRow> lastSevenDaysJournalArticleResultRows =
			new ArrayList<>();
		List<ResultRow> lastThirtyDaysJournalArticleResultRows =
			new ArrayList<>();
		List<ResultRow> olderJournalArticleResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			JournalArticle journalArticle =
				(JournalArticle)resultRow.getObject();

			LocalDateTime localDateTime = _toLocalDateTime(
				journalArticle.getCreateDate(),
				ZoneId.of(user.getTimeZoneId()));

			if (localDateTime.isBefore(thirtyDaysAgoLocalDateTime)) {
				olderJournalArticleResultRows.add(resultRow);
			}
			else if (localDateTime.isBefore(sevenDaysAgoLocalDateTime)) {
				lastThirtyDaysJournalArticleResultRows.add(resultRow);
			}
			else if (localDateTime.isBefore(todayLocalDateTime)) {
				lastSevenDaysJournalArticleResultRows.add(resultRow);
			}
			else {
				todayJournalArticleResultRows.add(resultRow);
			}
		}

		if (!todayJournalArticleResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					LanguageUtil.get(_themeDisplay.getLocale(), "today"),
					todayJournalArticleResultRows));
		}

		if (!lastSevenDaysJournalArticleResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					LanguageUtil.get(_themeDisplay.getLocale(), "last-7-days"),
					lastSevenDaysJournalArticleResultRows));
		}

		if (!lastThirtyDaysJournalArticleResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					LanguageUtil.get(_themeDisplay.getLocale(), "last-30-days"),
					lastThirtyDaysJournalArticleResultRows));
		}

		if (!olderJournalArticleResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					LanguageUtil.get(_themeDisplay.getLocale(), "older"),
					olderJournalArticleResultRows));
		}

		return resultRowSplitterEntries;
	}

	private LocalDateTime _toLocalDateTime(Date date, ZoneId zoneId) {
		if (date == null) {
			date = new Date();
		}

		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(zoneId);

		return zonedDateTime.toLocalDateTime();
	}

	private final ThemeDisplay _themeDisplay;

}