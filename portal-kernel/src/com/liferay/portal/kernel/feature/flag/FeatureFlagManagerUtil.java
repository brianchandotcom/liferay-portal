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

package com.liferay.portal.kernel.feature.flag;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.function.Function;
import java.util.function.Supplier;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Drew Brokke
 */
public class FeatureFlagManagerUtil {

	public static String getJSON(long companyId) {
		return _withFeatureFlagManager(
			featureFlagManager -> featureFlagManager.getJSON(companyId),
			() -> _FEATURE_FLAGS_JSON);
	}

	public static boolean isEnabled(long companyId, String key) {
		return _withFeatureFlagManager(
			featureFlagManager -> featureFlagManager.isEnabled(companyId, key),
			() -> {
				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setWithSafeCloseable(companyId)) {

					return GetterUtil.getBoolean(
						PropsUtil.get("feature.flag." + key));
				}
			});
	}

	public static boolean isEnabled(String key) {
		return _withFeatureFlagManager(
			featureFlagManager -> featureFlagManager.isEnabled(key),
			() -> GetterUtil.getBoolean(PropsUtil.get("feature.flag." + key)));
	}

	private static <T> T _withFeatureFlagManager(
		Function<FeatureFlagManager, T> function, Supplier<T> supplier) {

		FeatureFlagManager featureFlagManager = _serviceTracker.getService();

		if (featureFlagManager != null) {
			return function.apply(featureFlagManager);
		}

		return supplier.get();
	}

	private static final String _FEATURE_FLAGS_JSON = String.valueOf(
		JSONFactoryUtil.createJSONObject(
			PropsUtil.getProperties("feature.flag.", true)));

	private static final ServiceTracker<FeatureFlagManager, FeatureFlagManager>
		_serviceTracker;

	static {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ServiceTracker<FeatureFlagManager, FeatureFlagManager> serviceTracker =
			new ServiceTracker<>(bundleContext, FeatureFlagManager.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}