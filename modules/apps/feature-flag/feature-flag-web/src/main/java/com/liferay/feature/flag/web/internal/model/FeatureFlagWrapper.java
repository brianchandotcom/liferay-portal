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