/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		boolean requireSerialization) {

		super(portalCacheName, portalCacheListenerPropertiesSet);

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
			_requireSerialization);
	}

	private final boolean _requireSerialization;

}