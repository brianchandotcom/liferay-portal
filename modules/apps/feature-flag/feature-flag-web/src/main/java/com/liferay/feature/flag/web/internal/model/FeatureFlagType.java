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
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.function.Predicate;

/**
 * @author Drew Brokke
 */
public enum FeatureFlagType {

	BETA("beta"), DEV("dev"), RELEASE("release");

	public static FeatureFlagType toFeatureFlagType(String string) {
		for (FeatureFlagType featureFlagType : values()) {
			if (StringUtil.equalsIgnoreCase(featureFlagType._value, string)) {
				return featureFlagType;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("String did not match a known feature flag type");
		}

		return DEV;
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, _descriptionLanguageKey);
	}

	public Predicate<FeatureFlag> getPredicate() {
		return featureFlag -> equals(featureFlag.getFeatureFlagType());
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, _titleLanguageKey);
	}

	public boolean isUIEnabled() {
		return GetterUtil.getBoolean(
			PropsUtil.get(
				FeatureFlagConstants.getKey("ui.visible"), new Filter(_value)));
	}

	@Override
	public String toString() {
		return _value;
	}

	private FeatureFlagType(String value) {
		_value = value;

		_descriptionLanguageKey = FeatureFlagConstants.getKey(
			"type.description", value);
		_titleLanguageKey = FeatureFlagConstants.getKey("type.title", value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FeatureFlagType.class);

	private final String _descriptionLanguageKey;
	private final String _titleLanguageKey;
	private final String _value;

}