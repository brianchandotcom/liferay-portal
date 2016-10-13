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

package com.liferay.blogs.settings;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.blogs.constants.BlogsConstants;

import java.util.Map;

/**
 * @author Iván Zaera
 */
@Settings.Config(settingsIds = BlogsConstants.SERVICE_NAME)
public class BlogsGroupServiceSettings {

	public static BlogsGroupServiceSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(
				groupId, BlogsConstants.SERVICE_NAME));

		return new BlogsGroupServiceSettings(settings);
	}

	public static BlogsGroupServiceSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(
				groupId, BlogsConstants.SERVICE_NAME));

		return new BlogsGroupServiceSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	public static void registerSettingsMetadata() {
		SettingsFactoryUtil.registerSettingsMetadata(
			BlogsGroupServiceSettings.class, null, _getFallbackKeys());
	}

	public BlogsGroupServiceSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public LocalizedValuesMap getEmailEntryAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailEntryAddedBodyXml() {
		return LocalizationUtil.getXml(
			getEmailEntryAddedBody(), "emailEntryAddedBody");
	}

	public LocalizedValuesMap getEmailEntryAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailEntryAddedSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailEntryAddedSubject(), "emailEntryAddedSubject");
	}

	public LocalizedValuesMap getEmailEntryUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailEntryUpdatedBodyXml() {
		return LocalizationUtil.getXml(
			getEmailEntryUpdatedBody(), "emailEntryUpdatedBody");
	}

	public LocalizedValuesMap getEmailEntryUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailEntryUpdatedSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailEntryUpdatedSubject(), "emailEntryUpdatedSubject");
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public int getSmallImageWidth() {
		return _typedSettings.getIntegerValue("smallImageWidth");
	}

	public boolean isEmailEntryAddedEnabled() {
		return _typedSettings.getBooleanValue("emailEntryAddedEnabled");
	}

	public boolean isEmailEntryUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailEntryUpdatedEnabled");
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"emailEntryAddedBody", PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_BODY);
		fallbackKeys.add(
			"emailEntryAddedEnabled",
			PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_ENABLED);
		fallbackKeys.add(
			"emailEntryAddedSubject",
			PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_SUBJECT);
		fallbackKeys.add(
			"emailEntryUpdatedBody", PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_BODY);
		fallbackKeys.add(
			"emailEntryUpdatedEnabled",
			PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_ENABLED);
		fallbackKeys.add(
			"emailEntryUpdatedSubject",
			PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_SUBJECT);
		fallbackKeys.add(
			"emailFromAddress", PropsKeys.BLOGS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		fallbackKeys.add(
			"emailFromName", PropsKeys.BLOGS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);

		return fallbackKeys;
	}

	static {
		SettingsFactoryUtil.registerSettingsMetadata(
			BlogsGroupServiceSettings.class, null, _getFallbackKeys());
	}

	private final TypedSettings _typedSettings;

}