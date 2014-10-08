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

package com.liferay.portlet.wiki;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.resource.manager.ResourceManager;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portlet.wiki.util.WikiConstants;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class WikiSettings {

	public static String[] ALL_KEYS = {
		"emailFromAddress", "emailFromName", "emailPageAddedBody",
		"emailPageAddedSubject", "emailPageUpdatedBody",
		"emailPageUpdatedSubject", "emailPageAddedEnabled",
		"emailPageUpdatedEnabled"
	};

	public static WikiSettings getInstance(long groupId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, WikiConstants.SERVICE_NAME);

		return new WikiSettings(settings);
	}

	public static WikiSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			groupId, WikiConstants.SERVICE_NAME);

		return new WikiSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	public WikiSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public LocalizedValuesMap getEmailPageAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailPageAddedBody");
	}

	public String getEmailPageAddedBodyXml() {
		LocalizedValuesMap emailPageAddedBodyMap = getEmailPageAddedBody();

		return emailPageAddedBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailPageAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailPageAddedSubject");
	}

	public String getEmailPageAddedSubjectXml() {
		LocalizedValuesMap emailPageAddedSubjectMap =
			getEmailPageAddedSubject();

		return emailPageAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailPageUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailPageUpdatedBody");
	}

	public String getEmailPageUpdatedBodyXml() {
		LocalizedValuesMap emailPageUpdatedBodyMap = getEmailPageUpdatedBody();

		return emailPageUpdatedBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailPageUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailPageUpdatedSubject");
	}

	public String getEmailPageUpdatedSubjectXml() {
		LocalizedValuesMap emailPageUpdatedSubjectMap =
			getEmailPageUpdatedSubject();

		return emailPageUpdatedSubjectMap.getLocalizationXml();
	}

	public boolean isEmailPageAddedEnabled() {
		return _typedSettings.getBooleanValue("emailPageAddedEnabled");
	}

	public boolean isEmailPageUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailPageUpdatedEnabled");
	}

	private static FallbackKeys _getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"emailFromAddress", PropsKeys.WIKI_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		fallbackKeys.add(
			"emailFromName", PropsKeys.WIKI_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		fallbackKeys.add(
			"emailPageAddedBody", PropsKeys.WIKI_EMAIL_PAGE_ADDED_BODY);
		fallbackKeys.add(
			"emailPageAddedEnabled", PropsKeys.WIKI_EMAIL_PAGE_ADDED_ENABLED);
		fallbackKeys.add(
			"emailPageAddedSubject", PropsKeys.WIKI_EMAIL_PAGE_ADDED_SUBJECT);
		fallbackKeys.add(
			"emailPageUpdatedBody", PropsKeys.WIKI_EMAIL_PAGE_UPDATED_BODY);
		fallbackKeys.add(
			"emailPageUpdatedEnabled",
			PropsKeys.WIKI_EMAIL_PAGE_UPDATED_ENABLED);
		fallbackKeys.add(
			"emailPageUpdatedSubject",
			PropsKeys.WIKI_EMAIL_PAGE_UPDATED_SUBJECT);

		return fallbackKeys;
	}

	private static final String[] _MULTI_VALUED_KEYS = {};

	private static final ResourceManager _resourceManager =
		new ClassLoaderResourceManager(WikiSettings.class.getClassLoader());

	static {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerSettingsMetadata(
			WikiConstants.SERVICE_NAME, _getFallbackKeys(), _MULTI_VALUED_KEYS,
			_resourceManager);
	}

	private final TypedSettings _typedSettings;

}