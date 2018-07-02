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

package com.liferay.portal.cache.configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Tina Tian
 */
public class PortalCacheConfiguration {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #PORTAL_CACHE_NAME_DEFAULT}
	 */
	@Deprecated
	public static final String DEFAULT_PORTAL_CACHE_NAME = "default";

	public static final String PORTAL_CACHE_LISTENER_PROPERTIES_KEY_SCOPE =
		"PORTAL_CACHE_LISTENER_PROPERTIES_KEY_SCOPE";

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #PORTAL_CACHE_LISTENER_PROPERTIES_KEY_SCOPE}
	 */
	@Deprecated
	public static final String PORTAL_CACHE_LISTENER_SCOPE =
		"PORTAL_CACHE_LISTENER_SCOPE";

	public static final String PORTAL_CACHE_NAME_DEFAULT = "default";

	public PortalCacheConfiguration(
		String portalCacheName,
		Set<Properties> portalCacheListenerPropertiesSet,
		Properties portalCacheBootstrapLoaderProperties) {

		if (portalCacheName == null) {
			throw new NullPointerException("Portal cache name is null");
		}

		_portalCacheName = portalCacheName;

		if (portalCacheListenerPropertiesSet == null) {
			_portalCacheListenerPropertiesSet = Collections.emptySet();
		}
		else {
			_portalCacheListenerPropertiesSet = new HashSet<>(
				portalCacheListenerPropertiesSet);
		}

		_portalCacheBootstrapLoaderProperties =
			portalCacheBootstrapLoaderProperties;
	}

	public Properties getPortalCacheBootstrapLoaderProperties() {
		return _portalCacheBootstrapLoaderProperties;
	}

	public Set<Properties> getPortalCacheListenerPropertiesSet() {
		return _portalCacheListenerPropertiesSet;
	}

	public String getPortalCacheName() {
		return _portalCacheName;
	}

	public PortalCacheConfiguration newPortalCacheConfiguration(
		String portalCacheName) {

		return new PortalCacheConfiguration(
			portalCacheName, _portalCacheListenerPropertiesSet,
			_portalCacheBootstrapLoaderProperties);
	}

	public void setPortalCacheBootstrapLoaderProperties(
		Properties portalCacheBootstrapLoaderProperties) {

		_portalCacheBootstrapLoaderProperties =
			portalCacheBootstrapLoaderProperties;
	}

	private Properties _portalCacheBootstrapLoaderProperties;
	private final Set<Properties> _portalCacheListenerPropertiesSet;
	private final String _portalCacheName;

}