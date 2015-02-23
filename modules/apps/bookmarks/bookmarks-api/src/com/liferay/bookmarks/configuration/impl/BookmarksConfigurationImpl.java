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

import com.liferay.bookmarks.configuration.BookmarksConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.bookmarks.configuration.BookmarksConfiguration",
	immediate = true, service = BookmarksConfiguration.class
)
public class BookmarksConfigurationImpl implements BookmarksConfiguration {

	@Override
	public String emailEntryAddedBody() {
		return _bookmarksConfiguration.emailEntryAddedBody();
	}

	@Override
	public boolean emailEntryAddedEnabled() {
		return _bookmarksConfiguration.emailEntryAddedEnabled();
	}

	@Override
	public String emailEntryAddedSubject() {
		return _bookmarksConfiguration.emailEntryAddedSubject();
	}

	@Override
	public String emailEntryUpdatedBody() {
		return _bookmarksConfiguration.emailEntryUpdatedBody();
	}

	@Override
	public boolean emailEntryUpdatedEnabled() {
		return _bookmarksConfiguration.emailEntryUpdatedEnabled();
	}

	@Override
	public String emailEntryUpdatedSubject() {
		return _bookmarksConfiguration.emailEntryUpdatedSubject();
	}

	@Override
	public String emailFromAddress() {
		return _bookmarksConfiguration.emailFromAddress();
	}

	@Override
	public String emailFromName() {
		return _bookmarksConfiguration.emailFromName();
	}

	@Override
	public boolean enableRelatedAssets() {
		return _bookmarksConfiguration.enableRelatedAssets();
	}

	@Override
	public int entriesPerPage() {
		return _bookmarksConfiguration.entriesPerPage();
	}

	@Override
	public String[] entryColumns() {
		return _bookmarksConfiguration.entryColumns();
	}

	@Override
	public String[] folderColumns() {
		return _bookmarksConfiguration.folderColumns();
	}

	@Override
	public int foldersPerPage() {
		return _bookmarksConfiguration.foldersPerPage();
	}

	@Override
	public boolean showFoldersSearch() {
		return _bookmarksConfiguration.showFoldersSearch();
	}

	@Override
	public boolean showSubfolders() {
		return _bookmarksConfiguration.showSubfolders();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_bookmarksConfiguration = Configurable.createConfigurable(
			BookmarksConfiguration.class, properties);
	}

	private volatile BookmarksConfiguration _bookmarksConfiguration;

}