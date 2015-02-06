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

package com.liferay.wiki.settings;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class WikiSettings {

	public static String[] ALL_KEYS = {
		"defaultFormat", "emailFromAddress", "emailFromName",
		"emailPageAddedBody", "emailPageAddedSubject", "emailPageUpdatedBody",
		"emailPageUpdatedSubject", "emailPageAddedEnabled",
		"emailPageUpdatedEnabled", "pageCommentsEnabled",
		"pageMinorEditAddSocialActivity", "pageMinorEditSendEmail",
		"rssAbstractLength",
	};

	/**
	 * @deprecated As of 7.0.0, replaced by {WikiSettingsProvider#getGroupServiceSettings(long)}
	 */
	@Deprecated
	public static WikiSettings getInstance(long groupId)
		throws PortalException {

		WikiSettingsProvider wikiSettingsProvider =
			WikiSettingsProvider.getWikiSettingsProvider();

		return wikiSettingsProvider.getGroupServiceSettings(groupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {WikiSettingsProvider#getGroupServiceSettings(long,String[])}
	 */
	@Deprecated
	public static WikiSettings getInstance(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		WikiSettingsProvider wikiSettingsProvider =
			WikiSettingsProvider.getWikiSettingsProvider();

		return wikiSettingsProvider.getGroupServiceSettings(
			groupId, parameterMap);
	}

	public String getDefaultFormat() {
		return _typedSettings.getValue("defaultFormat");
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

	public int getRSSAbstractLength() {
		return _typedSettings.getIntegerValue("rssAbstractLength");
	}

	public boolean isEmailPageAddedEnabled() {
		return _typedSettings.getBooleanValue("emailPageAddedEnabled");
	}

	public boolean isEmailPageUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailPageUpdatedEnabled");
	}

	public boolean isPageCommentsEnabled() {
		return _typedSettings.getBooleanValue("pageCommentsEnabled");
	}

	public boolean isPageMinorEditAddSocialActivity() {
		return _typedSettings.getBooleanValue("pageMinorEditAddSocialActivity");
	}

	public boolean isPageMinorEditSendMail() {
		return _typedSettings.getBooleanValue("pageMinorEditSendEmail");
	}

	protected static FallbackKeys getFallbackKeys() {
		FallbackKeys fallbackKeys = new FallbackKeys();

		fallbackKeys.add(
			"emailFromAddress", PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		fallbackKeys.add("emailFromName", PropsKeys.ADMIN_EMAIL_FROM_NAME);

		return fallbackKeys;
	}

	protected WikiSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	protected static final String[] MULTI_VALUED_KEYS = {};

	private final TypedSettings _typedSettings;

}