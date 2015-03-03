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

package com.liferay.wiki.settings.definition;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.wiki.settings.WikiGroupServiceSettingsExtra;

/**
 * @author Iván Zaera
 */
public class WikiGroupServiceSettingsExtraImpl
	implements WikiGroupServiceSettingsExtra {

	public WikiGroupServiceSettingsExtraImpl(TypedSettings typedSettings) {
		_typedSettings = typedSettings;
	}

	@Override
	public String emailPageAddedBodyXml() {
		LocalizedValuesMap emailPageAddedBodyMap =
			_typedSettings.getLocalizedValuesMap("emailPageAddedBody");

		return emailPageAddedBodyMap.getLocalizationXml(
			"emailPageAddedBody", LocaleUtil.getSiteDefault(),
			LanguageUtil.getAvailableLocales());
	}

	@Override
	public String emailPageAddedSubjectXml() {
		LocalizedValuesMap emailPageAddedSubjectMap =
			_typedSettings.getLocalizedValuesMap("emailPageAddedSubject");

		return emailPageAddedSubjectMap.getLocalizationXml(
			"emailPageAddedSubject", LocaleUtil.getSiteDefault(),
			LanguageUtil.getAvailableLocales());
	}

	@Override
	public String emailPageUpdatedBodyXml() {
		LocalizedValuesMap emailPageUpdatedBodyMap =
			_typedSettings.getLocalizedValuesMap("emailPageUpdatedBody");

		return emailPageUpdatedBodyMap.getLocalizationXml(
			"emailPageUpdatedBody", LocaleUtil.getSiteDefault(),
			LanguageUtil.getAvailableLocales());
	}

	@Override
	public String emailPageUpdatedSubjectXml() {
		LocalizedValuesMap emailPageUpdatedSubjectMap =
			_typedSettings.getLocalizedValuesMap("emailPageUpdatedSubject");

		return emailPageUpdatedSubjectMap.getLocalizationXml(
			"emailPageUpdatedSubject", LocaleUtil.getSiteDefault(),
			LanguageUtil.getAvailableLocales());
	}

	private final TypedSettings _typedSettings;

}