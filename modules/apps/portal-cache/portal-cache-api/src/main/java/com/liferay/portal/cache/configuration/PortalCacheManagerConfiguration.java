/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.configuration;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class PortalCacheManagerConfiguration {

	public PortalCacheManagerConfiguration(
		PortalCacheConfiguration defaultPortalCacheConfiguration,
		Set<PortalCacheConfiguration> portalCacheConfigurations) {

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

	public PortalCacheConfiguration getDefaultPortalCacheConfiguration() {
		return _defaultPortalCacheConfiguration;
	}

	public PortalCacheConfiguration getPortalCacheConfiguration(
		String portalCacheName) {

		return _portalCacheConfigurations.computeIfAbsent(
			portalCacheName,
			key -> _defaultPortalCacheConfiguration.newPortalCacheConfiguration(
				key));
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

	public void setDefaultPortalCacheConfiguration(
		PortalCacheConfiguration defaultPortalCacheConfiguration) {

		_defaultPortalCacheConfiguration = defaultPortalCacheConfiguration;
	}

	private PortalCacheConfiguration _defaultPortalCacheConfiguration;
	private final Map<String, PortalCacheConfiguration>
		_portalCacheConfigurations = new ConcurrentHashMap<>();

}