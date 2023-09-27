/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

import java.net.URL;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Joseph Shum
 */
@ProviderType
public interface PortalCacheManager<K extends Serializable, V> {

	public static final String PORTAL_CACHE_MANAGER_NAME =
		"portal.cache.manager.name";

	public void clearAll() throws PortalCacheException;

	public void destroy();

	public PortalCache<K, V> fetchPortalCache(String portalCacheName);

	public PortalCache<K, V> getPortalCache(String portalCacheName)
		throws PortalCacheException;

	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean mvcc)
		throws PortalCacheException;

	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean mvcc, boolean sharded)
		throws PortalCacheException;

	public Set<PortalCacheManagerListener> getPortalCacheManagerListeners();

	public String getPortalCacheManagerName();

	public void reconfigurePortalCaches(
		URL configurationURL, ClassLoader classLoader);

	public boolean registerPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener);

	public void removePortalCache(String portalCacheName);

	public void removePortalCaches(long companyId);

	public boolean unregisterPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener);

	public void unregisterPortalCacheManagerListeners();

}