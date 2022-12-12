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

import com.liferay.feature.flag.web.internal.constants.FeatureFlagConstants;
import com.liferay.feature.flag.web.internal.manager.FeatureFlagPreferencesManager;
import com.liferay.feature.flag.web.internal.model.FeatureFlag;
import com.liferay.feature.flag.web.internal.model.FeatureFlagImpl;
import com.liferay.feature.flag.web.internal.model.LanguageAwareFeatureFlag;
import com.liferay.feature.flag.web.internal.model.PreferenceAwareFeatureFlag;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = CompanyFeatureFlagsFactory.class)
public class CompanyFeatureFlagsFactory {

	public CompanyFeatureFlags create(long companyId) {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			if (!GetterUtil.getBoolean(
					PropsUtil.get(FeatureFlagConstants.getKey("LPS-167698")))) {

				return new CompanyFeatureFlags(Collections.emptyMap());
			}

			Map<String, FeatureFlag> featureFlagMap = new HashMap<>();

			Properties properties = PropsUtil.getProperties(
				FeatureFlagConstants.FEATURE_FLAG + StringPool.PERIOD, true);

			for (String stringPropertyName : properties.stringPropertyNames()) {
				Matcher matcher = _pattern.matcher(stringPropertyName);

				if (!matcher.find()) {
					continue;
				}

				FeatureFlag featureFlag = new FeatureFlagImpl(
					stringPropertyName);

				featureFlag = new LanguageAwareFeatureFlag(
					featureFlag, _language);
				featureFlag = new PreferenceAwareFeatureFlag(
					companyId, featureFlag, _featureFlagPreferencesManager);

				featureFlagMap.put(featureFlag.getKey(), featureFlag);
			}

			return new CompanyFeatureFlags(
				Collections.unmodifiableMap(featureFlagMap));
		}
	}

	private static final Pattern _pattern = Pattern.compile("^([A-Z\\-0-9]+)$");

	@Reference
	private FeatureFlagPreferencesManager _featureFlagPreferencesManager;

	@Reference
	private Language _language;

}