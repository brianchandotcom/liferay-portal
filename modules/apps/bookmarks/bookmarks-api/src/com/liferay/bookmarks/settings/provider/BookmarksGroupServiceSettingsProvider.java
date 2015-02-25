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

import com.liferay.bookmarks.configuration.BookmarksGroupServiceConfiguration;
import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.settings.BookmarksGroupServiceSettings;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.GroupServiceSettingsProvider;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;

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
		"class.name=com.liferay.bookmarks.settings.BookmarksGroupServiceSettings"
	},
	service = GroupServiceSettingsProvider.class
)
public class BookmarksGroupServiceSettingsProvider
	implements GroupServiceSettingsProvider<BookmarksGroupServiceSettings> {

	@Override
	public BookmarksGroupServiceSettings getGroupServiceSettings(long groupId)
		throws PortalException {

		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, BookmarksConstants.SERVICE_NAME);

		return new BookmarksGroupServiceSettings(settings);
	}

	@Override
	public BookmarksGroupServiceSettings getGroupServiceSettings(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, BookmarksConstants.SERVICE_NAME);

		return new BookmarksGroupServiceSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	@Activate
	protected void activate() {
		_settingsFactory.registerSettingsMetadata(
			BookmarksGroupServiceSettings.class,
			_bookmarksGroupServiceConfiguration, null);
	}

	@Reference(unbind = "-")
	protected void setBookmarksGroupServiceConfiguration(
		BookmarksGroupServiceConfiguration bookmarksGroupServiceConfiguration) {

		_bookmarksGroupServiceConfiguration =
			bookmarksGroupServiceConfiguration;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private BookmarksGroupServiceConfiguration _bookmarksGroupServiceConfiguration;
	private SettingsFactory _settingsFactory;

}