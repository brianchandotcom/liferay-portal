/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class PortalCacheManagerConfiguration {

	public PortalCacheManagerConfiguration(
		Set<Properties> portalCacheManagerListenerPropertiesSet,
		PortalCacheConfiguration defaultPortalCacheConfiguration,
		Set<PortalCacheConfiguration> portalCacheConfigurations) {

		if (portalCacheManagerListenerPropertiesSet == null) {
			_portalCacheManagerListenerPropertiesSet = Collections.emptySet();
		}
		else {
			_portalCacheManagerListenerPropertiesSet = new HashSet<>(
				portalCacheManagerListenerPropertiesSet);
		}

		_defaultPortalCacheConfiguration = defaultPortalCacheConfiguration;

		if (portalCacheConfigurations != null) {
			for (PortalCacheConfiguration portalCacheConfiguration :
					portalCacheConfigurations) {

				_portalCacheConfigurations.put(
					portalCacheConfiguration.getPortalCacheName(),
					portalCacheConfiguration);
			}
		}
	}

	public PortalCacheConfiguration getPortalCacheConfiguration(
		String portalCacheName) {

		PortalCacheConfiguration portalCacheConfiguration =
			_portalCacheConfigurations.get(portalCacheName);

		if (portalCacheConfiguration == null) {
			portalCacheConfiguration =
				_defaultPortalCacheConfiguration.newPortalCacheConfiguration(
					portalCacheName);

			_portalCacheConfigurations.put(
				portalCacheName, portalCacheConfiguration);
		}

		return portalCacheConfiguration;
	}

	public Set<Properties> getPortalCacheManagerListenerPropertiesSet() {
		return Collections.unmodifiableSet(
			_portalCacheManagerListenerPropertiesSet);
	}

	public Set<String> getPortalCacheNames() {
		return Collections.unmodifiableSet(_portalCacheConfigurations.keySet());
	}

	public PortalCacheConfiguration putPortalCacheConfiguration(
		String portalCacheName,
		PortalCacheConfiguration portalCacheConfiguration) {

		return _portalCacheConfigurations.put(
			portalCacheName, portalCacheConfiguration);
	}

	private final PortalCacheConfiguration _defaultPortalCacheConfiguration;
	private final Map<String, PortalCacheConfiguration>
		_portalCacheConfigurations = new ConcurrentHashMap<>();
	private final Set<Properties> _portalCacheManagerListenerPropertiesSet;

}