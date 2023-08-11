/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.settings;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.util.Map;

/**
 * @author Iván Zaera
 */
@Settings.Config
public class BlogsGroupServiceSettings {

	public static BlogsGroupServiceSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				groupId, BlogsConstants.SERVICE_NAME));

		return new BlogsGroupServiceSettings(settings);
	}

	public static BlogsGroupServiceSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				groupId, BlogsConstants.SERVICE_NAME));

		return new BlogsGroupServiceSettings(
			new ParameterMapSettings(parameterMap, settings));
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

	private final TypedSettings _typedSettings;

}