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
public enum FeatureFlagStatus {

	BETA("beta"), DEV("dev"), RELEASE("release");

	public static FeatureFlagStatus toFeatureFlagStatus(String string) {
		for (FeatureFlagStatus featureFlagStatus : values()) {
			if (StringUtil.equalsIgnoreCase(featureFlagStatus._value, string)) {
				return featureFlagStatus;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("String did not match a known feature flag status");
		}

		return DEV;
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, _descriptionLanguageKey);
	}

	public Predicate<FeatureFlag> getPredicate() {
		return featureFlag -> equals(featureFlag.getFeatureFlagStatus());
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

	private FeatureFlagStatus(String value) {
		_value = value;

		_descriptionLanguageKey = FeatureFlagConstants.getKey(
			"status.description", _value);
		_titleLanguageKey = FeatureFlagConstants.getKey("status.title", _value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FeatureFlagStatus.class);

	private final String _descriptionLanguageKey;
	private final String _titleLanguageKey;
	private final String _value;

}