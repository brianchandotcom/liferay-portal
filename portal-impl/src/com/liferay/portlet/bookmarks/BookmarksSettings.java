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

package com.liferay.portlet.bookmarks;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.settings.BaseServiceSettings;
import com.liferay.portal.settings.FallbackKeys;
import com.liferay.portal.settings.LocalizedValuesMap;
import com.liferay.portal.settings.Settings;

/**
 * @author Iván Zaera
 */
public class BookmarksSettings extends BaseServiceSettings {

	public BookmarksSettings(Settings settings) {
		super(settings, _fallbackKeys);
	}

	public LocalizedValuesMap getEmailEntryAddedBody() {
		return typedSettings.getLocalizedValuesMap("emailEntryAddedBody");
	}

	public boolean getEmailEntryAddedEnabled() {
		return typedSettings.getBooleanValue("emailEntryAddedEnabled");
	}

	public LocalizedValuesMap getEmailEntryAddedSubject() {
		return typedSettings.getLocalizedValuesMap("emailEntryAddedSubject");
	}

	public LocalizedValuesMap getEmailEntryUpdatedBody() {
		return typedSettings.getLocalizedValuesMap("emailEntryUpdatedBody");
	}

	public boolean getEmailEntryUpdatedEnabled() {
		return typedSettings.getBooleanValue("emailEntryUpdatedEnabled");
	}

	public LocalizedValuesMap getEmailEntryUpdatedSubject() {
		return typedSettings.getLocalizedValuesMap("emailEntryUpdatedSubject");
	}

	public String getEmailFromAddress() {
		return typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return typedSettings.getValue("emailFromName");
	}

	private static FallbackKeys _fallbackKeys = new FallbackKeys();

	static {
		_fallbackKeys.add(
			"emailEntryAddedBody", PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_BODY);
		_fallbackKeys.add(
			"emailEntryAddedEnabled",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_ENABLED);
		_fallbackKeys.add(
			"emailEntryAddedSubject",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_ADDED_SUBJECT);
		_fallbackKeys.add(
			"emailEntryUpdatedBody",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_BODY);
		_fallbackKeys.add(
			"emailEntryUpdatedEnabled",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_ENABLED);
		_fallbackKeys.add(
			"emailEntryUpdatedSubject",
			PropsKeys.BOOKMARKS_EMAIL_ENTRY_UPDATED_SUBJECT);
		_fallbackKeys.add(
			"emailFromAddress", PropsKeys.BOOKMARKS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS );
		_fallbackKeys.add(
			"emailFromName", PropsKeys.BOOKMARKS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
	}

}