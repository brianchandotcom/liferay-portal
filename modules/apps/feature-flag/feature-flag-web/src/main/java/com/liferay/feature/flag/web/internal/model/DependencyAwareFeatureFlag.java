/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;

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
	public String[] getDependencyKeys() {
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