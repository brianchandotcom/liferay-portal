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

import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.configuration.PortalCacheConfiguration;

import java.util.Map;
import java.util.Properties;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheConfiguration extends PortalCacheConfiguration {

	public EhcachePortalCacheConfiguration(
		String portalCacheName,
		Map<Properties, PortalCacheListenerScope>
			portalCacheListenerConfigurations,
		Properties bootstrapLoaderConfiguration, boolean requireSerialization) {

		super(
			portalCacheName, portalCacheListenerConfigurations,
			bootstrapLoaderConfiguration);

		_requireSerialization = requireSerialization;
	}

	public boolean isRequireSerialization() {
		return _requireSerialization;
	}

	@Override
	public PortalCacheConfiguration newPortalCacheConfiguration(
		String portalCacheName) {

		return new EhcachePortalCacheConfiguration(
			portalCacheName, getPortalCacheListenerConfigurations(),
			getPortalCacheBootstrapLoaderConfiguration(),
			_requireSerialization);
	}

	private final boolean _requireSerialization;

}