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

package com.liferay.portal.kernel.cache.configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class PortalCacheManagerConfiguration {

	public PortalCacheManagerConfiguration(
		Set<CallbackConfiguration> cacheManagerListenerConfigurations,
		PortalCacheConfiguration defaultPortalCacheConfiguration,
		Set<PortalCacheConfiguration> portalCacheConfigurations) {

		if (cacheManagerListenerConfigurations == null) {
			_cacheManagerListenerConfigurations = Collections.emptySet();
		}
		else {
			_cacheManagerListenerConfigurations =
				new HashSet<CallbackConfiguration>(
					cacheManagerListenerConfigurations);
		}

		_defaultPortalCacheConfiguration = defaultPortalCacheConfiguration;

		_portalCacheConfigurations =
			new ConcurrentHashMap<String, PortalCacheConfiguration>();

		if (portalCacheConfigurations != null) {
			for (PortalCacheConfiguration portalCacheConfiguration :
					portalCacheConfigurations) {

				_portalCacheConfigurations.put(
					portalCacheConfiguration.getPortalCacheName(),
					portalCacheConfiguration);
			}
		}
	}

	public Set<CallbackConfiguration>
		getCacheManagerListenerConfigurations() {

		return Collections.unmodifiableSet(_cacheManagerListenerConfigurations);
	}

	public PortalCacheConfiguration getDefaultPortalCacheConfiguration() {
		return _defaultPortalCacheConfiguration;
	}

	public PortalCacheConfiguration getPortalCacheConfiguration(
		String portalCacheName) {

		return _portalCacheConfigurations.get(portalCacheName);
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

	private final Set<CallbackConfiguration>
		_cacheManagerListenerConfigurations;
	private final PortalCacheConfiguration _defaultPortalCacheConfiguration;
	private final Map<String, PortalCacheConfiguration>
		_portalCacheConfigurations;

}