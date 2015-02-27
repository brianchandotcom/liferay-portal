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

import com.liferay.bookmarks.configuration.BookmarksGroupServiceConfiguration;
import com.liferay.bookmarks.settings.BookmarksGroupServiceSettings;
import com.liferay.portal.kernel.settings.GroupServiceSettingsProvider;

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

	public BookmarksGroupServiceConfiguration
		getBookmarksGroupServiceConfiguration() {

		return _bookmarksGroupServiceConfiguration;
	}

	public GroupServiceSettingsProvider<BookmarksGroupServiceSettings>
		getGroupServiceSettingsProvider() {

		return _groupServiceSettingsProvider;
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
	protected void setBookmarksGroupServiceConfiguration(
		BookmarksGroupServiceConfiguration bookmarksGroupServiceConfiguration) {

		_bookmarksGroupServiceConfiguration =
			bookmarksGroupServiceConfiguration;
	}

	@Reference(
		target = "(class.name=com.liferay.bookmarks.settings.BookmarksGroupServiceSettings)"
	)
	protected void setGroupServiceSettingsProvider(
		GroupServiceSettingsProvider<BookmarksGroupServiceSettings>
			groupServiceSettingsProvider) {

		_groupServiceSettingsProvider = groupServiceSettingsProvider;
	}

	protected void unsetBookmarksGroupServiceConfiguration(
		BookmarksGroupServiceConfiguration bookmarksGroupServiceConfiguration) {

		_bookmarksGroupServiceConfiguration = null;
	}

	protected void unsetGroupServiceSettingsProvider(
		GroupServiceSettingsProvider<BookmarksGroupServiceSettings>
			groupServiceSettingsProvider) {

		_groupServiceSettingsProvider = null;
	}

	private static BookmarksWebSettingsProvider _bookmarksWebSettingsProvider;

	private BookmarksGroupServiceConfiguration
		_bookmarksGroupServiceConfiguration;
	private GroupServiceSettingsProvider<BookmarksGroupServiceSettings>
		_groupServiceSettingsProvider;

}