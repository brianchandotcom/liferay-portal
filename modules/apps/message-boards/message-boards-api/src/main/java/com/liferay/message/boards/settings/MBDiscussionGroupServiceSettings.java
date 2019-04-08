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

package com.liferay.message.boards.settings;

import com.liferay.message.boards.constants.MBConstants;
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

import java.util.Map;

/**
 * @author István András Dézsi
 */
@Settings.Config(settingsIds = MBConstants.SERVICE_NAME)
public class MBDiscussionGroupServiceSettings {

	public static MBDiscussionGroupServiceSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(groupId, MBConstants.SERVICE_NAME));

		return new MBDiscussionGroupServiceSettings(settings);
	}

	public static MBDiscussionGroupServiceSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getSettings(
			new GroupServiceSettingsLocator(groupId, MBConstants.SERVICE_NAME));

		ParameterMapSettings parameterMapSettings = new ParameterMapSettings(
			parameterMap, settings);

		return new MBDiscussionGroupServiceSettings(parameterMapSettings);
	}

	public static void registerSettingsMetadata() {
		SettingsFactoryUtil.registerSettingsMetadata(
			MBDiscussionGroupServiceSettings.class, null, _getFallbackKeys());
	}

	public MBDiscussionGroupServiceSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String getAdminEmailFromAddress() {
		return _typedSettings.getValue("adminEmailFromAddress");
	}

	public String getAdminEmailFromName() {
		return _typedSettings.getValue("adminEmailFromName");
	}

	public LocalizedValuesMap getDiscussionEmailBody() {
		return _typedSettings.getLocalizedValuesMap("discussionEmailBody");
	}

	public String getDiscussionEmailBodyXml() {
		return LocalizationUtil.getXml(
			getDiscussionEmailBody(), "discussionEmailBody");
	}

	public LocalizedValuesMap getDiscussionEmailSubject() {
		return _typedSettings.getLocalizedValuesMap("discussionEmailSubject");
	}

	public String getDiscussionEmailSubjectXml() {
		return LocalizationUtil.getXml(
			getDiscussionEmailSubject(), "discussionEmailSubject");
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"adminEmailFromAddress", PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		fallbackKeys.add("adminEmailFromName", PropsKeys.ADMIN_EMAIL_FROM_NAME);
		fallbackKeys.add(
			"discussionEmailBody", PropsKeys.DISCUSSION_EMAIL_BODY);
		fallbackKeys.add(
			"discussionEmailSubject", PropsKeys.DISCUSSION_EMAIL_SUBJECT);

		return fallbackKeys;
	}

	static {
		SettingsFactoryUtil.registerSettingsMetadata(
			MBDiscussionGroupServiceSettings.class, null, _getFallbackKeys());
	}

	private final TypedSettings _typedSettings;

}