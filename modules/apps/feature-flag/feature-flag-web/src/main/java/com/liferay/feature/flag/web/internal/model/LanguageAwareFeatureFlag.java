/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model;

import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class LanguageAwareFeatureFlag extends FeatureFlagWrapper {

	public LanguageAwareFeatureFlag(
		FeatureFlag featureFlag, Language language) {

		super(featureFlag);

		_language = language;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(
			locale, FeatureFlagConstants.getKey(getKey(), "description"),
			super.getDescription(locale));
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(
			locale, FeatureFlagConstants.getKey(getKey(), "title"),
			super.getTitle(locale));
	}

	private final Language _language;

}