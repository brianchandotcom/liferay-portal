/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model;

import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagType;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class FeatureFlagWrapper implements FeatureFlag {

	public FeatureFlagWrapper(FeatureFlag featureFlag) {
		_featureFlag = featureFlag;
	}

	@Override
	public String[] getDependencyKeys() {
		return _featureFlag.getDependencyKeys();
	}

	@Override
	public String getDescription(Locale locale) {
		return _featureFlag.getDescription(locale);
	}

	public FeatureFlag getFeatureFlag() {
		return _featureFlag;
	}

	@Override
	public FeatureFlagType getFeatureFlagType() {
		return _featureFlag.getFeatureFlagType();
	}

	@Override
	public String getKey() {
		return _featureFlag.getKey();
	}

	@Override
	public String getTitle(Locale locale) {
		return _featureFlag.getTitle(locale);
	}

	@Override
	public boolean isEnabled() {
		return _featureFlag.isEnabled();
	}

	private final FeatureFlag _featureFlag;

}