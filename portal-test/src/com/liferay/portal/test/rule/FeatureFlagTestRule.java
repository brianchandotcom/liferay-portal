/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.rule.AbstractTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.util.PropsUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.Description;

import org.osgi.framework.ServiceReference;

/**
 * @author Alejandro Tardín
 */
public class FeatureFlagTestRule
	extends AbstractTestRule<Map<String, String>, Map<String, String>> {

	public static final FeatureFlagTestRule INSTANCE =
		new FeatureFlagTestRule();

	@Override
	protected void afterClass(
			Description description, Map<String, String> previousValues)
		throws Throwable {

		_restoreFeatureFlags(previousValues);
	}

	@Override
	protected void afterMethod(
			Description description, Map<String, String> previousValues,
			Object target)
		throws Throwable {

		_restoreFeatureFlags(previousValues);
	}

	@Override
	protected Map<String, String> beforeClass(Description description)
		throws Throwable {

		return _updateFeatureFlags(description);
	}

	@Override
	protected Map<String, String> beforeMethod(
			Description description, Object target)
		throws Throwable {

		return _updateFeatureFlags(description);
	}

	private List<String> _getFeatureFlagKeys(
		ServiceReference<?> serviceReference) {

		Object value = serviceReference.getProperty("featureFlagKey");

		if (value == null) {
			return null;
		}

		if (value instanceof String[]) {
			return Arrays.asList((String[])value);
		}

		return Arrays.asList(String.valueOf(value));
	}

	private void _invokeFeatureFlagListeners(
			String featureFlagKey, boolean enabled)
		throws PortalException {

		try (ServiceTrackerMap<String, List<FeatureFlagListener>>
				serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
					SystemBundleUtil.getBundleContext(),
					FeatureFlagListener.class, null,
					(serviceReference, emitter) -> {
						List<String> keys = _getFeatureFlagKeys(
							serviceReference);

						if (keys == null) {
							return;
						}

						for (String key : keys) {
							emitter.emit(key);
						}
					})) {

			List<FeatureFlagListener> featureFlagListeners =
				serviceTrackerMap.getService(featureFlagKey);

			if (featureFlagListeners == null) {
				return;
			}

			long companyId = CompanyConstants.SYSTEM;

			if (!GetterUtil.getBoolean(
					PropsUtil.get(
						FeatureFlagConstants.getKey(
							featureFlagKey, "system")))) {

				companyId = TestPropsValues.getCompanyId();
			}

			for (FeatureFlagListener featureFlagListener :
					featureFlagListeners) {

				featureFlagListener.onValue(companyId, featureFlagKey, enabled);
			}
		}
	}

	private void _restoreFeatureFlags(Map<String, String> previousValues) {
		Map<String, String> values = new HashMap<>();

		for (Map.Entry<String, String> entry : previousValues.entrySet()) {
			String value = entry.getValue();

			if (value == null) {
				PropsUtil.set(entry.getKey(), value);

				continue;
			}

			values.put(entry.getKey(), entry.getValue());
		}

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.create(
				values, true
			).build());
	}

	private Map<String, String> _updateFeatureFlags(Description description)
		throws PortalException {

		FeatureFlags featureFlags = description.getAnnotation(
			FeatureFlags.class);

		if (featureFlags == null) {
			return Collections.emptyMap();
		}

		Map<String, String> previousValues = new HashMap<>();

		for (String key : featureFlags.value()) {
			String featureFlagKey = "feature.flag." + key;

			previousValues.put(featureFlagKey, PropsUtil.get(featureFlagKey));

			PropsUtil.addProperties(
				UnicodePropertiesBuilder.setProperty(
					featureFlagKey, String.valueOf(featureFlags.enable())
				).build());

			_invokeFeatureFlagListeners(key, featureFlags.enable());
		}

		return previousValues;
	}

}