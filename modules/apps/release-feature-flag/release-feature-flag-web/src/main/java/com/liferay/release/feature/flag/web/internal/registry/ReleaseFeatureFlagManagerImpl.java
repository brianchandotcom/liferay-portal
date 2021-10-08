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

package com.liferay.release.feature.flag.web.internal.registry;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.release.feature.flag.ReleaseFeatureFlag;
import com.liferay.release.feature.flag.ReleaseFeatureFlagManager;
import com.liferay.release.feature.flag.web.internal.configuration.ReleaseFeatureFlagConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.release.feature.flag.web.internal.configuration.ReleaseFeatureFlagConfiguration",
	service = ReleaseFeatureFlagManager.class
)
public class ReleaseFeatureFlagManagerImpl
	implements ReleaseFeatureFlagManager {

	@Override
	public boolean isEnabled(ReleaseFeatureFlag releaseFeatureFlag) {
		return !ArrayUtil.contains(
			_disabledReleaseFeatureFlags, releaseFeatureFlag.toString());
	}

	@Override
	public void setEnabled(
		ReleaseFeatureFlag releaseFeatureFlag, boolean enabled) {

		String[] disabledReleaseFeatureFlags = null;

		if (enabled && !isEnabled(releaseFeatureFlag)) {
			disabledReleaseFeatureFlags = ArrayUtil.remove(
				_disabledReleaseFeatureFlags, releaseFeatureFlag.toString());
		}
		else if (!enabled) {
			disabledReleaseFeatureFlags = ArrayUtil.append(
				_disabledReleaseFeatureFlags, releaseFeatureFlag.toString());
		}

		try {
			_configurationProvider.saveSystemConfiguration(
				ReleaseFeatureFlagConfiguration.class,
				HashMapDictionaryBuilder.<String, Object>put(
					"disabledReleaseFeatureFlags", disabledReleaseFeatureFlags
				).build());
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException, configurationException);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		ReleaseFeatureFlagConfiguration releaseFeatureFlagConfiguration =
			ConfigurableUtil.createConfigurable(
				ReleaseFeatureFlagConfiguration.class, properties);

		_disabledReleaseFeatureFlags =
			releaseFeatureFlagConfiguration.disabledReleaseFeatureFlags();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseFeatureFlagManagerImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile String[] _disabledReleaseFeatureFlags = new String[0];

}