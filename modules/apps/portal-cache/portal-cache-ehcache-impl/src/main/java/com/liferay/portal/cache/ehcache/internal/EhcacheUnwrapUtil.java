/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.PortalCacheWrapper;
import com.liferay.portal.kernel.cache.PortalCache;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;

/**
 * @author Shuyang Zhou
 */
public class EhcacheUnwrapUtil {

	public static Ehcache getEhcache(PortalCache<?, ?> portalCache) {
		PortalCache<?, ?> wrappedPortalCache = getWrappedPortalCache(
			portalCache);

		if (wrappedPortalCache instanceof EhcacheWrapper) {
			EhcacheWrapper ehcacheWrapper = (EhcacheWrapper)wrappedPortalCache;

			return ehcacheWrapper.getEhcache();
		}

		throw new IllegalArgumentException(
			"Unable to locate Ehcache from " + portalCache);
	}

	public static <K extends Serializable, V> BaseEhcachePortalCache<K, V>
		getWrappedPortalCache(PortalCache<K, V> portalCache) {

		PortalCache<K, V> currentPortalCache = portalCache;

		while (currentPortalCache instanceof PortalCacheWrapper) {
			PortalCacheWrapper<K, V> portalCacheWrapper =
				(PortalCacheWrapper<K, V>)currentPortalCache;

			currentPortalCache = portalCacheWrapper.getWrappedPortalCache();
		}

		return (BaseEhcachePortalCache<K, V>)currentPortalCache;
	}

}