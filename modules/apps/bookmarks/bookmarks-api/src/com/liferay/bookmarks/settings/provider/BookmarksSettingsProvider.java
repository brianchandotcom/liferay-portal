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

package com.liferay.bookmarks.settings.provider;

import com.liferay.bookmarks.configuration.BookmarksConfiguration;
import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.settings.BookmarksSettings;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsProvider;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"class.name=com.liferay.bookmarks.settings.BookmarksSettings"
	},
	service = SettingsProvider.class
)
public class BookmarksSettingsProvider
	implements SettingsProvider<BookmarksSettings> {

	@Override
	public BookmarksSettings getGroupServiceSettings(long groupId)
		throws PortalException {

		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, BookmarksConstants.SERVICE_NAME);

		return new BookmarksSettings(settings);
	}

	@Override
	public BookmarksSettings getGroupServiceSettings(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, BookmarksConstants.SERVICE_NAME);

		return new BookmarksSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	@Activate
	protected void activate() {
		_settingsFactory.registerSettingsMetadata(
			BookmarksSettings.class, _bookmarksConfiguration, null);
	}

	@Reference(unbind = "-")
	protected void setBookmarksConfiguration(
		BookmarksConfiguration bookmarksConfiguration) {

		_bookmarksConfiguration = bookmarksConfiguration;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private BookmarksConfiguration _bookmarksConfiguration;
	private SettingsFactory _settingsFactory;

}