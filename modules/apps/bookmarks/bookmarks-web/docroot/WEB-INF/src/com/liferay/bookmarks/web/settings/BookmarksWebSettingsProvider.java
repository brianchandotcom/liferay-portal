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

package com.liferay.bookmarks.web.settings;

import com.liferay.bookmarks.configuration.BookmarksConfiguration;
import com.liferay.bookmarks.settings.BookmarksSettings;
import com.liferay.portal.kernel.settings.SettingsProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true
)
public class BookmarksWebSettingsProvider {

	public static BookmarksWebSettingsProvider
		getBookmarksWebSettingsProvider() {

		return _bookmarksWebSettingsProvider;
	}

	public BookmarksConfiguration getBookmarksConfiguration() {
		return _bookmarksConfiguration;
	}

	public SettingsProvider<BookmarksSettings> getBookmarksSettingsProvider() {
		return _settingsProvider;
	}

	@Activate
	protected void activate() {
		_bookmarksWebSettingsProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_bookmarksWebSettingsProvider = null;
	}

	@Reference
	protected void setBookmarksConfiguration(
		BookmarksConfiguration bookmarksConfiguration) {

		_bookmarksConfiguration = bookmarksConfiguration;
	}

	@Reference(
		target = "(class.name=com.liferay.bookmarks.settings.BookmarksSettings)"
	)
	protected void setSettingsProvider(
		SettingsProvider<BookmarksSettings> settingsProvider) {

		_settingsProvider = settingsProvider;
	}

	protected void unsetBookmarksConfiguration(
		BookmarksConfiguration bookmarksConfiguration) {

		_bookmarksConfiguration = null;
	}

	protected void unsetSettingsProvider(
		SettingsProvider<BookmarksSettings> settingsProvider) {

		_settingsProvider = null;
	}

	private static BookmarksWebSettingsProvider _bookmarksWebSettingsProvider;

	private BookmarksConfiguration _bookmarksConfiguration;
	private SettingsProvider<BookmarksSettings> _settingsProvider;

}