/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.feature.flag;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
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

	BETA("beta"), DEPRECATION("deprecation"), DEV("dev"), RELEASE("release");

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