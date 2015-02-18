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

package com.liferay.bookmarks.configuration;

import aQute.bnd.annotation.metatype.Configurable;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.bookmarks.configuration.BookmarksServiceConfiguration",
	immediate = true, service = BookmarksServiceConfiguration.class
)
public class BookmarksServiceConfigurationImpl
	implements BookmarksServiceConfiguration {

	@Override
	public String emailEntryAddedBody() {
		return _bookmarksServiceConfiguration.emailEntryAddedBody();
	}

	@Override
	public boolean emailEntryAddedEnabled() {
		return _bookmarksServiceConfiguration.emailEntryAddedEnabled();
	}

	@Override
	public String emailEntryAddedSubject() {
		return _bookmarksServiceConfiguration.emailEntryAddedSubject();
	}

	@Override
	public String emailEntryUpdatedBody() {
		return _bookmarksServiceConfiguration.emailEntryUpdatedBody();
	}

	@Override
	public boolean emailEntryUpdatedEnabled() {
		return _bookmarksServiceConfiguration.emailEntryUpdatedEnabled();
	}

	@Override
	public String emailEntryUpdatedSubject() {
		return _bookmarksServiceConfiguration.emailEntryUpdatedSubject();
	}

	@Override
	public String emailFromAddress() {
		return _bookmarksServiceConfiguration.emailFromAddress();
	}

	@Override
	public String emailFromName() {
		return _bookmarksServiceConfiguration.emailFromName();
	}

	@Override
	public int entriesPerPage() {
		return _bookmarksServiceConfiguration.entriesPerPage();
	}

	@Override
	public String[] entryColumns() {
		return _bookmarksServiceConfiguration.entryColumns();
	}

	@Override
	public String[] folderColumns() {
		return _bookmarksServiceConfiguration.folderColumns();
	}

	@Override
	public int foldersPerPage() {
		return _bookmarksServiceConfiguration.foldersPerPage();
	}

	@Override
	public boolean showFoldersSearch() {
		return _bookmarksServiceConfiguration.showFoldersSearch();
	}

	@Override
	public boolean enableRelatedAssets() {
		return _bookmarksServiceConfiguration.enableRelatedAssets();
	}

	@Override
	public boolean showSubfolders() {
		return _bookmarksServiceConfiguration.showSubfolders();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_bookmarksServiceConfiguration = Configurable.createConfigurable(
			BookmarksServiceConfiguration.class, properties);
	}

	private volatile BookmarksServiceConfiguration
		_bookmarksServiceConfiguration;

}