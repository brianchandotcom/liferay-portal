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

package com.liferay.structured.content.apio.internal.model;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Locale;

/**
 * Wraps a {@code JournalArticle} and includes a {@code ThemeDisplay} object.
 *
 * @author Eduardo Perez
 */
public class JournalArticleWrapper
	extends com.liferay.journal.model.JournalArticleWrapper {

	/**
	 * Creates a new journal article wrapper.
	 *
	 * @param journalArticle the journal article to wrap
	 * @param themeDisplay the current request's theme display
	 */
	public JournalArticleWrapper(
		JournalArticle journalArticle, Locale locale,
		ThemeDisplay themeDisplay) {

		super(journalArticle);

		_locale = locale;
		_themeDisplay = themeDisplay;
	}

	/**
	 * @return locale the {@link Locale}
	 * @review
	 */
	public Locale getLocale() {
		return _locale;
	}

	/**
	 * @return themeDisplay the {@link ThemeDisplay}
	 * @review
	 */
	public ThemeDisplay getThemeDisplay() {
		return _themeDisplay;
	}

	private final Locale _locale;
	private final ThemeDisplay _themeDisplay;

}