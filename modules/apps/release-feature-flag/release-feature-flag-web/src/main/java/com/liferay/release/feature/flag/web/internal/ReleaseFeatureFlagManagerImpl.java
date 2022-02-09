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

package com.liferay.release.feature.flag.web.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.release.feature.flag.ReleaseFeatureFlag;
import com.liferay.release.feature.flag.ReleaseFeatureFlagManager;
import com.liferay.release.feature.flag.web.internal.configuration.ReleaseFeatureFlagConfiguration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		return !_disabledReleaseFeatureFlags.contains(releaseFeatureFlag);
	}

	@Override
	public void setEnabled(
		ReleaseFeatureFlag releaseFeatureFlag, boolean enabled) {

		if (enabled) {
			_disabledReleaseFeatureFlags.remove(releaseFeatureFlag);
		}
		else {
			_disabledReleaseFeatureFlags.add(releaseFeatureFlag);
		}

		try {
			_configurationProvider.saveSystemConfiguration(
				ReleaseFeatureFlagConfiguration.class,
				HashMapDictionaryBuilder.<String, Object>put(
					"disabledReleaseFeatureFlags",
					ArrayUtil.toStringArray(_disabledReleaseFeatureFlags)
				).build());
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		ReleaseFeatureFlagConfiguration releaseFeatureFlagConfiguration =
			ConfigurableUtil.createConfigurable(
				ReleaseFeatureFlagConfiguration.class, properties);

		_disabledReleaseFeatureFlags.clear();

		for (String disabledReleaseFeatureFlag :
				releaseFeatureFlagConfiguration.disabledReleaseFeatureFlags()) {

			if (Validator.isNull(disabledReleaseFeatureFlag)) {
				continue;
			}

			try {
				_disabledReleaseFeatureFlags.add(
					ReleaseFeatureFlag.valueOf(disabledReleaseFeatureFlag));
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_log.error(illegalArgumentException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseFeatureFlagManagerImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile Set<ReleaseFeatureFlag> _disabledReleaseFeatureFlags =
		new HashSet<>();

}