/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model;

import com.liferay.feature.flag.web.internal.manager.FeatureFlagPreferencesManager;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Drew Brokke
 */
public class PreferenceAwareFeatureFlag extends FeatureFlagWrapper {

	public PreferenceAwareFeatureFlag(
		long companyId, FeatureFlag featureFlag,
		FeatureFlagPreferencesManager featureFlagPreferencesManager) {

		super(featureFlag);

		_companyId = companyId;
		_featureFlagPreferencesManager = featureFlagPreferencesManager;
	}

	@Override
	public boolean isEnabled() {
		Boolean enabled = _enabled;

		if (enabled == null) {
			enabled = GetterUtil.getBoolean(
				_featureFlagPreferencesManager.isEnabled(_companyId, getKey()),
				super.isEnabled());

			_enabled = enabled;
		}

		return enabled;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	private final long _companyId;
	private volatile Boolean _enabled;
	private final FeatureFlagPreferencesManager _featureFlagPreferencesManager;

}