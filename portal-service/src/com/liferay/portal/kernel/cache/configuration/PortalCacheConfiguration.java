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

import com.liferay.portal.kernel.cache.PortalCacheListenerScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Tina Tian
 */
public class PortalCacheConfiguration {

	public static final String DEFAULT_PORTAL_CACHE_NAME =
		"DEFAULT_PORTAL_CACHE_NAME";

	public PortalCacheConfiguration(
		String portalCacheName,
		Map<Properties, PortalCacheListenerScope>
			portalCacheListenerConfigurations,
		Properties portalCacheBootstrapLoaderConfiguration) {

		if (portalCacheName == null) {
			throw new NullPointerException("Portal cache name is null");
		}

		_portalCacheName = portalCacheName;

		if (portalCacheListenerConfigurations == null) {
			_portalCacheListenerConfigurations = Collections.emptyMap();
		}
		else {
			_portalCacheListenerConfigurations = new HashMap<>(
				portalCacheListenerConfigurations);
		}

		_portalCacheBootstrapLoaderConfiguration =
			portalCacheBootstrapLoaderConfiguration;
	}

	public Properties
		getPortalCacheBootstrapLoaderConfiguration() {

		return _portalCacheBootstrapLoaderConfiguration;
	}

	public Map<Properties, PortalCacheListenerScope>
		getPortalCacheListenerConfigurations() {

		return Collections.unmodifiableMap(_portalCacheListenerConfigurations);
	}

	public String getPortalCacheName() {
		return _portalCacheName;
	}

	public PortalCacheConfiguration newPortalCacheConfiguration(
		String portalCacheName) {

		return new PortalCacheConfiguration(
			portalCacheName, _portalCacheListenerConfigurations,
			_portalCacheBootstrapLoaderConfiguration);
	}

	private final Properties _portalCacheBootstrapLoaderConfiguration;
	private final Map<Properties, PortalCacheListenerScope>
		_portalCacheListenerConfigurations;
	private final String _portalCacheName;

}