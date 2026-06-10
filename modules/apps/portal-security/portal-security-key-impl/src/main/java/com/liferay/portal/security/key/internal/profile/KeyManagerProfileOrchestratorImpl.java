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
import com.liferay.portal.security.key.spi.profile.KeyManagerProfileOrchestrator;

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
	service = KeyManagerProfileOrchestrator.class
)
public class KeyManagerProfileOrchestratorImpl
	implements KeyManagerProfileOrchestrator {

	@Override
	public KeyManagerProfile getActiveKeyManagerProfile() {
		KeyManagerGlobalConfiguration keyManagerGlobalConfiguration =
			_keyManagerGlobalConfiguration;

		ServiceTrackerMap<String, KeyManagerProfile> serviceTrackerMap =
			_serviceTrackerMap;

		if ((keyManagerGlobalConfiguration == null) ||
			(serviceTrackerMap == null)) {

			return null;
		}

		KeyManagerProfile keyManagerProfile = serviceTrackerMap.getService(
			keyManagerGlobalConfiguration.activeProfileId());

		if (keyManagerProfile == null) {
			keyManagerProfile = serviceTrackerMap.getService(
				CustomKeyManagerProfile.PROFILE_ID);
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

					synchronized (KeyManagerProfileOrchestratorImpl.this) {
						if (Objects.equals(_lastBootstrappedProfileId, key)) {
							_lastBootstrappedProfileId = null;
						}
					}
				}

			});

		_bootstrap(getActiveKeyManagerProfile());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();

			_serviceTrackerMap = null;
		}

		_keyManagerGlobalConfiguration = null;
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_keyManagerGlobalConfiguration = ConfigurableUtil.createConfigurable(
			KeyManagerGlobalConfiguration.class, properties);

		_bootstrap(getActiveKeyManagerProfile());
	}

	private void _bootstrap(KeyManagerProfile keyManagerProfile) {
		KeyManagerGlobalConfiguration keyManagerGlobalConfiguration =
			_keyManagerGlobalConfiguration;

		if ((keyManagerProfile == null) ||
			(keyManagerGlobalConfiguration == null)) {

			return;
		}

		String activeProfileId =
			keyManagerGlobalConfiguration.activeProfileId();

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
		KeyManagerProfileOrchestratorImpl.class);

	private volatile KeyManagerGlobalConfiguration
		_keyManagerGlobalConfiguration;
	private volatile String _lastBootstrappedProfileId;
	private volatile ServiceTrackerMap<String, KeyManagerProfile>
		_serviceTrackerMap;

}