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

package com.liferay.feature.flag.web.internal.manager;

import com.liferay.feature.flag.web.internal.constants.FeatureFlagConstants;
import com.liferay.feature.flag.web.internal.model.FeatureFlagStatus;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Drew Brokke
 */
public class FeatureFlagPropsManager {

	public FeatureFlagPropsManager() {
		Set<String> keySet = new HashSet<>();

		for (String stringPropertyName : _properties.stringPropertyNames()) {
			Matcher matcher = _pattern.matcher(stringPropertyName);

			if (matcher.find()) {
				keySet.add(stringPropertyName);
			}
		}

		_keySet = Collections.unmodifiableSet(keySet);
	}

	public String getDescription(String key) {
		return _get(key, "description", StringPool.BLANK);
	}

	public Set<String> getKeySet() {
		return _keySet;
	}

	public FeatureFlagStatus getStatus(String key) {
		return FeatureFlagStatus.toFeatureFlagStatus(
			_get(key, "status", StringPool.BLANK));
	}

	public String getTitle(String key) {
		return _get(key, "title", key);
	}

	public boolean isEnabled(String key) {
		return GetterUtil.getBoolean(_get(key, StringPool.BLANK, null));
	}

	private String _get(String key, String suffix, String defaultValue) {
		if (Validator.isNotNull(suffix)) {
			key = StringBundler.concat(key, StringPool.PERIOD, suffix);
		}

		String[] stringValues = StringUtil.split(_properties.getProperty(key));

		if (ArrayUtil.isEmpty(stringValues)) {
			return defaultValue;
		}

		return stringValues[stringValues.length - 1];
	}

	private static final Pattern _pattern = Pattern.compile("^([A-Z\\-0-9]+)$");

	private final Set<String> _keySet;
	private final Properties _properties = PropsUtil.getProperties(
		FeatureFlagConstants.FEATURE_FLAG + StringPool.PERIOD, true);

}