/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static final String PORTAL_CACHE_LISTENER_PROPERTIES_KEY_SCOPE =
		"PORTAL_CACHE_LISTENER_PROPERTIES_KEY_SCOPE";

	public static final String PORTAL_CACHE_NAME_DEFAULT = "default";

	public PortalCacheConfiguration(
		String portalCacheName,
		Set<Properties> portalCacheListenerPropertiesSet) {

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
			portalCacheName, _portalCacheListenerPropertiesSet);
	}

	private final Set<Properties> _portalCacheListenerPropertiesSet;
	private final String _portalCacheName;

}