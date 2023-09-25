/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.feature.flag;

import com.liferay.feature.flag.web.internal.manager.FeatureFlagPreferencesManager;
import com.liferay.feature.flag.web.internal.model.DependencyAwareFeatureFlag;
import com.liferay.feature.flag.web.internal.model.FeatureFlagImpl;
import com.liferay.feature.flag.web.internal.model.LanguageAwareFeatureFlag;
import com.liferay.feature.flag.web.internal.model.PreferenceAwareFeatureFlag;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
@Component(service = AopService.class)
public class FeatureFlagsBagProviderImpl
	implements AopService, FeatureFlagsBagProvider, IdentifiableOSGiService {

	@Override
	public FeatureFlagsBag getOrCreateFeatureFlagsBag(long companyId) {
		return _featureFlagsBagMap.computeIfAbsent(
			companyId, this::_createFeatureFlagsBag);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return FeatureFlagsBagProviderImpl.class.getName();
	}

	@Override
	public void setEnabled(long companyId, String key, boolean enabled) {
		_featureFlagPreferencesManager.setEnabled(companyId, key, enabled);

		_setEnabled(companyId, key, enabled);
	}

	@Override
	public <T> T withFeatureFlagsBag(
		long companyId, Function<FeatureFlagsBag, T> function) {

		return function.apply(getOrCreateFeatureFlagsBag(companyId));
	}

	private FeatureFlagsBag _createFeatureFlagsBag(long companyId) {
		Map<String, FeatureFlag> systemFeatureFlagMap = new HashMap<>();

		if (companyId != CompanyConstants.SYSTEM) {
			FeatureFlagsBag systemFeatureFlagsBag = getOrCreateFeatureFlagsBag(
				CompanyConstants.SYSTEM);

			for (FeatureFlag featureFlag :
					systemFeatureFlagsBag.getFeatureFlags(null)) {

				systemFeatureFlagMap.put(featureFlag.getKey(), featureFlag);
			}
		}

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

				boolean system = GetterUtil.getBoolean(
					properties.get(stringPropertyName + ".system"));

				if ((system && (companyId == CompanyConstants.SYSTEM)) ||
					(!system && (companyId != CompanyConstants.SYSTEM))) {

					FeatureFlag featureFlag = new FeatureFlagImpl(
						stringPropertyName);

					featureFlag = new LanguageAwareFeatureFlag(
						featureFlag, _language);
					featureFlag = new PreferenceAwareFeatureFlag(
						companyId, featureFlag, _featureFlagPreferencesManager);

					featureFlagsMap.put(featureFlag.getKey(), featureFlag);
				}
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

					if ((companyId == CompanyConstants.SYSTEM) &&
						!GetterUtil.getBoolean(
							properties.get(
								FeatureFlagConstants.getKey(
									dependencyKey,
									ExtendedObjectClassDefinition.Scope.SYSTEM.
										getValue())))) {

						_log.error(
							StringBundler.concat(
								"The system feature flag ",
								featureFlag.getKey(),
								"cannot depend on the nonsystem feature flag ",
								dependencyKey));

						continue;
					}

					FeatureFlag dependencyFeatureFlag = featureFlagsMap.get(
						dependencyKey);

					if (dependencyFeatureFlag == null) {
						dependencyFeatureFlag = systemFeatureFlagMap.get(
							dependencyKey);
					}

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

			return new FeatureFlagsBag(
				companyId, Collections.unmodifiableMap(featureFlagsMap));
		}
	}

	@Clusterable
	private void _setEnabled(long companyId, String key, boolean enabled) {
		FeatureFlagsBag featureFlagsBag = _featureFlagsBagMap.get(companyId);

		if (featureFlagsBag == null) {
			return;
		}

		featureFlagsBag.setEnabled(key, enabled);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FeatureFlagsBagProviderImpl.class);

	private static final Map<Long, FeatureFlagsBag> _featureFlagsBagMap =
		new ConcurrentHashMap<>();
	private static final Pattern _pattern = Pattern.compile("^([A-Z\\-0-9]+)$");

	@Reference
	private FeatureFlagPreferencesManager _featureFlagPreferencesManager;

	@Reference
	private Language _language;

}