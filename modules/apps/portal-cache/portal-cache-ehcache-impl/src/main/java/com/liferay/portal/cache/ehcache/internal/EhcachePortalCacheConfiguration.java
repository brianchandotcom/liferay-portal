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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.configuration.PortalCacheConfiguration;

import java.util.Properties;
import java.util.Set;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheConfiguration extends PortalCacheConfiguration {

	public EhcachePortalCacheConfiguration(
		String portalCacheName,
		Set<Properties> portalCacheListenerPropertiesSet,
		Properties portalCacheBootstrapLoaderProperties,
		boolean requireSerialization) {

		super(
			portalCacheName, portalCacheListenerPropertiesSet,
			portalCacheBootstrapLoaderProperties);

		_requireSerialization = requireSerialization;
	}

	public boolean isRequireSerialization() {
		return _requireSerialization;
	}

	@Override
	public PortalCacheConfiguration newPortalCacheConfiguration(
		String portalCacheName) {

		return new EhcachePortalCacheConfiguration(
			portalCacheName, getPortalCacheListenerPropertiesSet(),
			getPortalCacheBootstrapLoaderProperties(), _requireSerialization);
	}

	private final boolean _requireSerialization;

}