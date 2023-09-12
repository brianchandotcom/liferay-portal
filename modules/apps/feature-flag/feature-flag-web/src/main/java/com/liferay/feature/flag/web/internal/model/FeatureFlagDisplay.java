/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model;

import com.liferay.portal.kernel.feature.flag.FeatureFlag;

import java.util.List;
import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class FeatureFlagDisplay extends FeatureFlagWrapper {

	public FeatureFlagDisplay(
		Long companyId, List<FeatureFlag> dependencyFeatureFlags,
		FeatureFlag featureFlag, Locale locale) {

		super(featureFlag);

		_companyId = companyId;
		_dependencyFeatureFlags = dependencyFeatureFlags;
		_locale = locale;
	}

	public long getCompanyId() {
		return _companyId;
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

	private final long _companyId;
	private final List<FeatureFlag> _dependencyFeatureFlags;
	private final Locale _locale;

}