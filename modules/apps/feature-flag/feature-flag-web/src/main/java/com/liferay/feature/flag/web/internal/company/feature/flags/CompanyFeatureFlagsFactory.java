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

package com.liferay.feature.flag.web.internal.company.feature.flags;

import com.liferay.feature.flag.web.internal.manager.FeatureFlagPreferencesManager;
import com.liferay.feature.flag.web.internal.manager.FeatureFlagPropsManager;
import com.liferay.feature.flag.web.internal.model.FeatureFlag;
import com.liferay.feature.flag.web.internal.model.FeatureFlagImpl;
import com.liferay.feature.flag.web.internal.model.LanguageAwareFeatureFlag;
import com.liferay.feature.flag.web.internal.model.PreferenceAwareFeatureFlag;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = CompanyFeatureFlagsFactory.class)
public class CompanyFeatureFlagsFactory {

	public CompanyFeatureFlags create(long companyId) {
		FeatureFlagPropsManager featureFlagPropsManager;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			featureFlagPropsManager = new FeatureFlagPropsManager();
		}

		Map<String, FeatureFlag> featureFlagMap = new HashMap<>();

		boolean featureFlagUIEnabled = featureFlagPropsManager.isEnabled(
			"LPS-167698");

		for (String key : featureFlagPropsManager.getKeySet()) {
			FeatureFlag featureFlag = new FeatureFlagImpl(
				featureFlagPropsManager.getDescription(key),
				featureFlagPropsManager.isEnabled(key),
				featureFlagPropsManager.getStatus(key), key,
				featureFlagPropsManager.getTitle(key));

			if (featureFlagUIEnabled) {
				featureFlag = new LanguageAwareFeatureFlag(
					featureFlag, _language);
				featureFlag = new PreferenceAwareFeatureFlag(
					companyId, featureFlag, _featureFlagPreferencesManager);
			}

			featureFlagMap.put(featureFlag.getKey(), featureFlag);
		}

		return new CompanyFeatureFlags(
			Collections.unmodifiableMap(featureFlagMap),
			featureFlagPropsManager, featureFlagUIEnabled);
	}

	@Reference
	private FeatureFlagPreferencesManager _featureFlagPreferencesManager;

	@Reference
	private Language _language;

}