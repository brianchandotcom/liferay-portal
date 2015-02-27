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

package com.liferay.bookmarks.configuration.impl;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.bookmarks.configuration.BookmarksGroupServiceConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.bookmarks.configuration.BookmarksGroupServiceConfiguration",
	immediate = true, service = BookmarksGroupServiceConfiguration.class
)
public class BookmarksGroupServiceConfigurationImpl
	implements BookmarksGroupServiceConfiguration {

	@Override
	public String emailEntryAddedBody() {
		return _bookmarksGroupServiceConfiguration.emailEntryAddedBody();
	}

	@Override
	public boolean emailEntryAddedEnabled() {
		return _bookmarksGroupServiceConfiguration.emailEntryAddedEnabled();
	}

	@Override
	public String emailEntryAddedSubject() {
		return _bookmarksGroupServiceConfiguration.emailEntryAddedSubject();
	}

	@Override
	public String emailEntryUpdatedBody() {
		return _bookmarksGroupServiceConfiguration.emailEntryUpdatedBody();
	}

	@Override
	public boolean emailEntryUpdatedEnabled() {
		return _bookmarksGroupServiceConfiguration.emailEntryUpdatedEnabled();
	}

	@Override
	public String emailEntryUpdatedSubject() {
		return _bookmarksGroupServiceConfiguration.emailEntryUpdatedSubject();
	}

	@Override
	public String emailFromAddress() {
		return _bookmarksGroupServiceConfiguration.emailFromAddress();
	}

	@Override
	public String emailFromName() {
		return _bookmarksGroupServiceConfiguration.emailFromName();
	}

	@Override
	public boolean enableRelatedAssets() {
		return _bookmarksGroupServiceConfiguration.enableRelatedAssets();
	}

	@Override
	public String entriesPerPage() {
		return _bookmarksGroupServiceConfiguration.entriesPerPage();
	}

	@Override
	public String[] entryColumns() {
		return _bookmarksGroupServiceConfiguration.entryColumns();
	}

	@Override
	public String[] folderColumns() {
		return _bookmarksGroupServiceConfiguration.folderColumns();
	}

	@Override
	public String foldersPerPage() {
		return _bookmarksGroupServiceConfiguration.foldersPerPage();
	}

	@Override
	public boolean showFoldersSearch() {
		return _bookmarksGroupServiceConfiguration.showFoldersSearch();
	}

	@Override
	public boolean showSubfolders() {
		return _bookmarksGroupServiceConfiguration.showSubfolders();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_bookmarksGroupServiceConfiguration = Configurable.createConfigurable(
			BookmarksGroupServiceConfiguration.class, properties);
	}

	private volatile BookmarksGroupServiceConfiguration
		_bookmarksGroupServiceConfiguration;

}