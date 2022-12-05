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

package com.liferay.feature.flag.web.internal.model;

import com.liferay.feature.flag.web.internal.constants.FeatureFlagConstants;
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