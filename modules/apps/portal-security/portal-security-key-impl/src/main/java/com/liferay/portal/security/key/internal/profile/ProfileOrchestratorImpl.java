/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.profile;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.key.internal.profile.configuration.KeyManagerGlobalConfiguration;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;
import com.liferay.portal.security.key.spi.profile.ProfileOrchestrator;

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
	configurationPid = "com.liferay.portal.security.key.internal.profile.configuration.KeyManagerGlobalConfiguration",
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

					synchronized (ProfileOrchestratorImpl.this) {
						if (Objects.equals(_lastBootstrappedProfileId, key)) {
							_lastBootstrappedProfileId = null;
						}
					}
				}

			});

		_bootstrap(getActiveProfile());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_keyManagerGlobalConfiguration = ConfigurableUtil.createConfigurable(
			KeyManagerGlobalConfiguration.class, properties);

		synchronized (this) {
			_lastBootstrappedProfileId = null;
		}

		_bootstrap(getActiveProfile());
	}

	private void _bootstrap(KeyManagerProfile keyManagerProfile) {
		if ((keyManagerProfile == null) ||
			(_keyManagerGlobalConfiguration == null)) {

			return;
		}

		String activeProfileId =
			_keyManagerGlobalConfiguration.activeProfileId();

		if (!Objects.equals(
				activeProfileId, keyManagerProfile.getProfileId())) {

			return;
		}

		synchronized (this) {
			if (Objects.equals(_lastBootstrappedProfileId, activeProfileId)) {
				return;
			}

			_lastBootstrappedProfileId = activeProfileId;
		}

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Bootstrapping Key Manager profile: " + activeProfileId);
			}

			keyManagerProfile.bootstrap();
		}
		catch (Exception exception) {
			synchronized (this) {
				if (Objects.equals(
						_lastBootstrappedProfileId, activeProfileId)) {

					_lastBootstrappedProfileId = null;
				}
			}

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