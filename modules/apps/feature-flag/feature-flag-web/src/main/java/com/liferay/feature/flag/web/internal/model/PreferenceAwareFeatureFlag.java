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

import com.liferay.feature.flag.web.internal.helper.FeatureFlagPreferencesHelper;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Drew Brokke
 */
public class PreferenceAwareFeatureFlag extends FeatureFlagWrapper {

	public PreferenceAwareFeatureFlag(
		FeatureFlag featureFlag, long companyId,
		FeatureFlagPreferencesHelper featureFlagPreferencesHelper) {

		super(featureFlag);

		_companyId = companyId;
		_featureFlagPreferencesHelper = featureFlagPreferencesHelper;
	}

	@Override
	public boolean isEnabled() {
		return GetterUtil.getBoolean(
			_featureFlagPreferencesHelper.isEnabled(_companyId, getKey()),
			super.isEnabled());
	}

	private final long _companyId;
	private final FeatureFlagPreferencesHelper _featureFlagPreferencesHelper;

}