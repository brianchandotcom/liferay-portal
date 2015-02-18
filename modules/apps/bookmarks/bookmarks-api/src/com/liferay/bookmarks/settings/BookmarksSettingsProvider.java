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

package com.liferay.bookmarks.settings;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.bookmarks.configuration.BookmarksServiceConfiguration;
import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsProvider;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.bookmarks.configuration.BookmarksServiceConfiguration",
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

	protected static BookmarksSettingsProvider getBookmarksSettingsProvider() {
		if (_bookmarksSettingsProvider == null) {
			throw new IllegalStateException(
				"Bookmarks settings provider is not available");
		}

		return _bookmarksSettingsProvider;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		BookmarksServiceConfiguration bookmarksServiceConfiguration =
			Configurable.createConfigurable(
				BookmarksServiceConfiguration.class, properties);

		_settingsFactory.registerSettingsMetadata(
			BookmarksConstants.SERVICE_NAME,
			BookmarksSettings.getFallbackKeys(),
			BookmarksSettings.MULTI_VALUED_KEYS, bookmarksServiceConfiguration,
			new ClassLoaderResourceManager(
				BookmarksSettings.class.getClassLoader()));

		_bookmarksSettingsProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_bookmarksSettingsProvider = null;
	}

	@Reference
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private static BookmarksSettingsProvider _bookmarksSettingsProvider;

	private SettingsFactory _settingsFactory;

}