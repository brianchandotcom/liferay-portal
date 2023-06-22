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
import com.liferay.feature.flag.web.internal.model.DependencyAwareFeatureFlag;
import com.liferay.feature.flag.web.internal.model.FeatureFlag;
import com.liferay.feature.flag.web.internal.model.FeatureFlagImpl;
import com.liferay.feature.flag.web.internal.model.LanguageAwareFeatureFlag;
import com.liferay.feature.flag.web.internal.model.PreferenceAwareFeatureFlag;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = CompanyFeatureFlagsProvider.class)
public class CompanyFeatureFlagsProvider {

	public CompanyFeatureFlags getOrCreateCompanyFeatureFlags(long companyId) {
		return _companyFeatureFlagsMap.computeIfAbsent(
			companyId, key -> _createCompanyFeatureFlags(key));
	}

	public void setEnabled(long companyId, String key, boolean enabled) {
		_featureFlagPreferencesManager.setEnabled(companyId, key, enabled);

		CompanyFeatureFlags companyFeatureFlags = _companyFeatureFlagsMap.get(
			companyId);

		if (companyFeatureFlags == null) {
			return;
		}

		companyFeatureFlags.setEnabled(key, enabled);
	}

	public <T> T withCompanyFeatureFlags(
		long companyId, Function<CompanyFeatureFlags, T> function) {

		return function.apply(getOrCreateCompanyFeatureFlags(companyId));
	}

	private CompanyFeatureFlags _createCompanyFeatureFlags(long companyId) {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			Map<String, FeatureFlag> featureFlagsMap = new HashMap<>();

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

				featureFlagsMap.put(featureFlag.getKey(), featureFlag);
			}

			for (Map.Entry<String, FeatureFlag> entry :
					featureFlagsMap.entrySet()) {

				List<FeatureFlag> dependencyFeatureFlags = new ArrayList<>();

				FeatureFlag featureFlag = entry.getValue();

				for (String dependencyKey : featureFlag.getDependencyKeys()) {
					if (Objects.equals(featureFlag.getKey(), dependencyKey)) {
						_log.error(
							"A feature flag cannot depend on itself: " +
								dependencyKey);

						continue;
					}

					FeatureFlag dependencyFeatureFlag = featureFlagsMap.get(
						dependencyKey);

					if (dependencyFeatureFlag != null) {
						if (!ArrayUtil.contains(
								dependencyFeatureFlag.getDependencyKeys(),
								featureFlag.getKey())) {

							dependencyFeatureFlags.add(dependencyFeatureFlag);
						}
						else {
							_log.error(
								StringBundler.concat(
									"Skipping circular dependency ",
									dependencyKey, " for feature flag ",
									featureFlag.getKey()));
						}
					}
				}

				if (ListUtil.isNotEmpty(dependencyFeatureFlags)) {
					entry.setValue(
						new DependencyAwareFeatureFlag(
							featureFlag,
							dependencyFeatureFlags.toArray(
								new FeatureFlag[0])));
				}
			}

			return new CompanyFeatureFlags(
				Collections.unmodifiableMap(featureFlagsMap));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyFeatureFlagsProvider.class);

	private static final Pattern _pattern = Pattern.compile("^([A-Z\\-0-9]+)$");

	private final Map<Long, CompanyFeatureFlags> _companyFeatureFlagsMap =
		new ConcurrentHashMap<>();

	@Reference
	private FeatureFlagPreferencesManager _featureFlagPreferencesManager;

	@Reference
	private Language _language;

}