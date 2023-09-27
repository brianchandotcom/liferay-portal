/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model;

import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagType;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class FeatureFlagImpl implements FeatureFlag {

	public FeatureFlagImpl(String key) {
		this(
			GetterUtil.getStringValues(
				PropsUtil.getArray(
					FeatureFlagConstants.getKey(key, "dependencies"))),
			GetterUtil.getString(
				PropsUtil.get(FeatureFlagConstants.getKey(key, "description"))),
			GetterUtil.getBoolean(
				PropsUtil.get(FeatureFlagConstants.getKey(key))),
			FeatureFlagType.toFeatureFlagType(
				PropsUtil.get(FeatureFlagConstants.getKey(key, "type"))),
			key,
			GetterUtil.getString(
				PropsUtil.get(FeatureFlagConstants.getKey(key, "title")), key));
	}

	public FeatureFlagImpl(
		String[] dependencyKeys, String description, boolean enabled,
		FeatureFlagType featureFlagType, String key, String title) {

		_dependencyKeys = dependencyKeys;
		_description = description;
		_enabled = enabled;
		_featureFlagType = featureFlagType;
		_key = key;
		_title = title;
	}

	@Override
	public String[] getDependencyKeys() {
		return _dependencyKeys;
	}

	@Override
	public String getDescription(Locale locale) {
		return _description;
	}

	@Override
	public FeatureFlagType getFeatureFlagType() {
		return _featureFlagType;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public String getTitle(Locale locale) {
		return _title;
	}

	@Override
	public boolean isEnabled() {
		return _enabled;
	}

	private final String[] _dependencyKeys;
	private final String _description;
	private final boolean _enabled;
	private final FeatureFlagType _featureFlagType;
	private final String _key;
	private final String _title;

}