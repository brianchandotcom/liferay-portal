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

package com.liferay.portlet.blogs;

import com.liferay.portal.kernel.settings.BaseServiceSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.PropsKeys;

/**
 * @author Iván Zaera
 */
public class BlogsSettings extends BaseServiceSettings {

	public BlogsSettings(Settings settings) {
		super(settings, _fallbackKeys);
	}

	public LocalizedValuesMap getEmailEntryAddedBody() {
		return typedSettings.getLocalizedValuesMap("emailEntryAddedBody");
	}

	public String getEmailEntryAddedBodyXml() {
		LocalizedValuesMap emailEntryAddedBodyMap = getEmailEntryAddedBody();

		return emailEntryAddedBodyMap.getLocalizationXml();
	}

	public boolean getEmailEntryAddedEnabled() {
		return typedSettings.getBooleanValue("emailEntryAddedEnabled");
	}

	public LocalizedValuesMap getEmailEntryAddedSubject() {
		return typedSettings.getLocalizedValuesMap("emailEntryAddedSubject");
	}

	public String getEmailEntryAddedSubjectXml() {
		LocalizedValuesMap emailEntryAddedSubjectMap =
			getEmailEntryAddedSubject();

		return emailEntryAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryUpdatedBody() {
		return typedSettings.getLocalizedValuesMap("emailEntryUpdatedBody");
	}

	public String getEmailEntryUpdatedBodyXml() {
		LocalizedValuesMap emailEntryUpdatedBodyMap =
			getEmailEntryUpdatedBody();

		return emailEntryUpdatedBodyMap.getLocalizationXml();
	}

	public boolean getEmailEntryUpdatedEnabled() {
		return typedSettings.getBooleanValue("emailEntryUpdatedEnabled");
	}

	public LocalizedValuesMap getEmailEntryUpdatedSubject() {
		return typedSettings.getLocalizedValuesMap("emailEntryUpdatedSubject");
	}

	public String getEmailEntryUpdatedSubjectXml() {
		LocalizedValuesMap emailEntryUpdatedSubjectMap =
			getEmailEntryUpdatedSubject();

		return emailEntryUpdatedSubjectMap.getLocalizationXml();
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
			"emailEntryAddedBody", PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_BODY);
		_fallbackKeys.add(
			"emailEntryAddedEnabled",
			PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_ENABLED);
		_fallbackKeys.add(
			"emailEntryAddedSubject",
			PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_SUBJECT);
		_fallbackKeys.add(
			"emailEntryUpdatedBody", PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_BODY);
		_fallbackKeys.add(
			"emailEntryUpdatedEnabled",
			PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_ENABLED);
		_fallbackKeys.add(
			"emailEntryUpdatedSubject",
			PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_SUBJECT);
		_fallbackKeys.add(
			"emailFromAddress", PropsKeys.BLOGS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		_fallbackKeys.add(
			"emailFromName", PropsKeys.BLOGS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
	}

}