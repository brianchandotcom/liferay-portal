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

import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.wiki.constants.WikiConstants;

/**
 * @author Iván Zaera
 */
@Settings.Config(ids = WikiConstants.SERVICE_NAME)
public class WikiSettings {

	public WikiSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
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

	@Settings.Property(ignore = true)
	public String getEmailPageAddedBodyXml() {
		LocalizedValuesMap emailPageAddedBodyMap = getEmailPageAddedBody();

		return emailPageAddedBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailPageAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailPageAddedSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailPageAddedSubjectXml() {
		LocalizedValuesMap emailPageAddedSubjectMap =
			getEmailPageAddedSubject();

		return emailPageAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailPageUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailPageUpdatedBody");
	}

	@Settings.Property(ignore = true)
	public String getEmailPageUpdatedBodyXml() {
		LocalizedValuesMap emailPageUpdatedBodyMap = getEmailPageUpdatedBody();

		return emailPageUpdatedBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailPageUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailPageUpdatedSubject");
	}

	@Settings.Property(ignore = true)
	public String getEmailPageUpdatedSubjectXml() {
		LocalizedValuesMap emailPageUpdatedSubjectMap =
			getEmailPageUpdatedSubject();

		return emailPageUpdatedSubjectMap.getLocalizationXml();
	}

	@Settings.Property(name = "rssAbstractLength")
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

	private final TypedSettings _typedSettings;

}