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

import java.util.List;
import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class FeatureFlagDisplay extends FeatureFlagWrapper {

	public FeatureFlagDisplay(
		List<FeatureFlag> dependencyFeatureFlags, FeatureFlag featureFlag,
		Locale locale) {

		super(featureFlag);

		_dependencyFeatureFlags = dependencyFeatureFlags;
		_locale = locale;
	}

	public String getDescription() {
		return super.getDescription(_locale);
	}

	public String getTitle() {
		return super.getTitle(_locale);
	}

	public boolean isDependenciesFulfilled() {
		for (FeatureFlag dependencyFeatureFlag : _dependencyFeatureFlags) {
			if (!dependencyFeatureFlag.isEnabled()) {
				return false;
			}
		}

		return true;
	}

	private final List<FeatureFlag> _dependencyFeatureFlags;
	private final Locale _locale;

}