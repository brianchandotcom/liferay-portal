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

package com.liferay.bookmarks.configuration.definition;

import com.liferay.bookmarks.configuration.BookmarksGroupServiceConfigurationOverride;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;

/**
 * @author Iván Zaera
 */
public class BookmarksGroupServiceConfigurationOverrideImpl
	implements BookmarksGroupServiceConfigurationOverride {

	public BookmarksGroupServiceConfigurationOverrideImpl(
		TypedSettings typedSettings) {

		_typedSettings = typedSettings;
	}

	@Override
	public String emailEntryAddedBodyXml() {
		return LocalizationUtil.getXml(
			_typedSettings.getLocalizedValuesMap("emailEntryAddedBody"),
			"emailEntryAddedBody");
	}

	@Override
	public String emailEntryAddedSubjectXml() {
		return LocalizationUtil.getXml(
			_typedSettings.getLocalizedValuesMap("emailEntryAddedSubject"),
			"emailEntryAddedSubject");
	}

	@Override
	public String emailEntryUpdatedBodyXml() {
		return LocalizationUtil.getXml(
			_typedSettings.getLocalizedValuesMap("emailEntryUpdatedBody"),
			"emailEntryUpdatedBody");
	}

	@Override
	public String emailEntryUpdatedSubjectXml() {
		return LocalizationUtil.getXml(
			_typedSettings.getLocalizedValuesMap("emailEntryUpdatedSubject"),
			"emailEntryUpdatedSubject");
	}

	@Override
	public long rootFolderId() {
		return _typedSettings.getLongValue(
			"rootFolderId", BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	private final TypedSettings _typedSettings;

}