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
import java.util.Properties;
import java.util.Set;

/**
 * @author Tina Tian
 */
public class PortalCacheConfiguration {

	public static final String DEFAULT_PORTAL_CACHE_NAME =
		"DEFAULT_PORTAL_CACHE_NAME";

	public static final String PORTAL_CACHE_LISTENER_SCOPE =
		"PORTAL_CACHE_LISTENER_SCOPE";

	public PortalCacheConfiguration(
		String portalCacheName, Set<Properties> portalCacheListenerProperties,
		Properties portalCacheBootstrapLoaderProperties) {

		if (portalCacheName == null) {
			throw new NullPointerException("Portal cache name is null");
		}

		_portalCacheName = portalCacheName;

		if (portalCacheListenerProperties == null) {
			_portalCacheListenerProperties = Collections.emptySet();
		}
		else {
			_portalCacheListenerProperties = new HashSet<>(
				portalCacheListenerProperties);
		}

		_portalCacheBootstrapLoaderProperties =
			portalCacheBootstrapLoaderProperties;
	}

	public Properties getPortalCacheBootstrapLoaderProperties() {
		return _portalCacheBootstrapLoaderProperties;
	}

	public Set<Properties> getPortalCacheListenerProperties() {
		return Collections.unmodifiableSet(_portalCacheListenerProperties);
	}

	public String getPortalCacheName() {
		return _portalCacheName;
	}

	public PortalCacheConfiguration newPortalCacheConfiguration(
		String portalCacheName) {

		return new PortalCacheConfiguration(
			portalCacheName, _portalCacheListenerProperties,
			_portalCacheBootstrapLoaderProperties);
	}

	private final Properties _portalCacheBootstrapLoaderProperties;
	private final Set<Properties> _portalCacheListenerProperties;
	private final String _portalCacheName;

}