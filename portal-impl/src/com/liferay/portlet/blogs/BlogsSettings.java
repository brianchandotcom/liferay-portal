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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.blogs.util.BlogsConstants;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class BlogsSettings {

	public static final String[] ALL_KEYS = {
		"emailEntryAddedBody", "emailEntryAddedSubject",
		"emailEntryUpdatedBody", "emailEntryUpdatedSubject", "emailFromAddress",
		"emailFromName", "emailEntryAddedEnabled", "emailEntryUpdatedEnabled"
	};

	public static BlogsSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, BlogsConstants.SERVICE_NAME);

		return new BlogsSettings(settings);
	}

	public static BlogsSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, BlogsConstants.SERVICE_NAME);

		return new BlogsSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	public BlogsSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public LocalizedValuesMap getEmailEntryAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedBody");
	}

	public String getEmailEntryAddedBodyXml() {
		LocalizedValuesMap emailEntryAddedBodyMap = getEmailEntryAddedBody();

		return emailEntryAddedBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryAddedSubject");
	}

	public String getEmailEntryAddedSubjectXml() {
		LocalizedValuesMap emailEntryAddedSubjectMap =
			getEmailEntryAddedSubject();

		return emailEntryAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedBody");
	}

	public String getEmailEntryUpdatedBodyXml() {
		LocalizedValuesMap emailEntryUpdatedBodyMap =
			getEmailEntryUpdatedBody();

		return emailEntryUpdatedBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailEntryUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailEntryUpdatedSubject");
	}

	public String getEmailEntryUpdatedSubjectXml() {
		LocalizedValuesMap emailEntryUpdatedSubjectMap =
			getEmailEntryUpdatedSubject();

		return emailEntryUpdatedSubjectMap.getLocalizationXml();
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
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

	private static final String[] _MULTI_VALUED_KEYS = {};

	static {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerSettingsMetadata(
			BlogsConstants.SERVICE_NAME, _getFallbackKeys(),
			_MULTI_VALUED_KEYS);
	}

	private final TypedSettings _typedSettings;

}