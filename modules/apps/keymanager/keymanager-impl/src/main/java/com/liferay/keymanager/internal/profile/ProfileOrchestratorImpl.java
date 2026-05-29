/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.keymanager.internal.profile;

import com.liferay.keymanager.internal.profile.configuration.KeyManagerGlobalConfiguration;
import com.liferay.keymanager.spi.profile.KeyManagerProfile;
import com.liferay.keymanager.spi.profile.ProfileOrchestrator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
@Component(
	configurationPid = "com.liferay.keymanager.internal.profile.configuration.KeyManagerGlobalConfiguration",
	service = ProfileOrchestrator.class
)
public class ProfileOrchestratorImpl implements ProfileOrchestrator {

	@Override
	public KeyManagerProfile getActiveProfile() {
		String activeProfileId =
			_keyManagerGlobalConfiguration.activeProfileId();

		KeyManagerProfile keyManagerProfile = _serviceTrackerMap.getService(
			activeProfileId);

		if (keyManagerProfile == null) {
			keyManagerProfile = _serviceTrackerMap.getService("custom");
		}

		return keyManagerProfile;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_keyManagerGlobalConfiguration = ConfigurableUtil.createConfigurable(
			KeyManagerGlobalConfiguration.class, properties);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, KeyManagerProfile.class, "keymanager.profile.id",
			new ServiceTrackerMapListener
				<String, KeyManagerProfile, KeyManagerProfile>() {

				@Override
				public void keyEmitted(
					ServiceTrackerMap<String, KeyManagerProfile>
						serviceTrackerMap,
					String key, KeyManagerProfile keyManagerProfile,
					KeyManagerProfile content) {

					_bootstrap(keyManagerProfile);
				}

				@Override
				public void keyRemoved(
					ServiceTrackerMap<String, KeyManagerProfile>
						serviceTrackerMap,
					String key, KeyManagerProfile keyManagerProfile,
					KeyManagerProfile content) {

					if (Objects.equals(_lastBootstrappedProfileId, key)) {
						_lastBootstrappedProfileId = null;
					}
				}

			});

		_bootstrap(getActiveProfile());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_keyManagerGlobalConfiguration = ConfigurableUtil.createConfigurable(
			KeyManagerGlobalConfiguration.class, properties);

		_lastBootstrappedProfileId = null;

		_bootstrap(getActiveProfile());
	}

	private synchronized void _bootstrap(KeyManagerProfile keyManagerProfile) {
		if ((keyManagerProfile == null) ||
			(_keyManagerGlobalConfiguration == null)) {

			return;
		}

		String activeProfileId =
			_keyManagerGlobalConfiguration.activeProfileId();

		if (!Objects.equals(
				activeProfileId, keyManagerProfile.getProfileId()) ||
			Objects.equals(_lastBootstrappedProfileId, activeProfileId)) {

			return;
		}

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Bootstrapping Key Manager profile: " + activeProfileId);
			}

			keyManagerProfile.bootstrap();

			_lastBootstrappedProfileId = activeProfileId;
		}
		catch (Exception exception) {
			_log.error(
				"Unable to bootstrap Key Manager profile: " + activeProfileId,
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProfileOrchestratorImpl.class);

	private volatile KeyManagerGlobalConfiguration
		_keyManagerGlobalConfiguration;
	private volatile String _lastBootstrappedProfileId;
	private volatile ServiceTrackerMap<String, KeyManagerProfile>
		_serviceTrackerMap;

}