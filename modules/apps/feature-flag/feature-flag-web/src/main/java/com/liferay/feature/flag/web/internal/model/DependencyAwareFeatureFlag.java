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

import com.liferay.petra.function.transform.TransformUtil;

/**
 * @author Drew Brokke
 */
public class DependencyAwareFeatureFlag extends FeatureFlagWrapper {

	public DependencyAwareFeatureFlag(
		FeatureFlag featureFlag, FeatureFlag... dependencyFeatureFlags) {

		super(featureFlag);

		_dependencyFeatureFlags = dependencyFeatureFlags;
	}

	@Override
	public String[] getDependencies() {
		return TransformUtil.transform(
			_dependencyFeatureFlags, FeatureFlag::getKey, String.class);
	}

	@Override
	public boolean isEnabled() {
		for (FeatureFlag dependencyFeatureFlag : _dependencyFeatureFlags) {
			if (!dependencyFeatureFlag.isEnabled()) {
				return false;
			}
		}

		return super.isEnabled();
	}

	private final FeatureFlag[] _dependencyFeatureFlags;

}