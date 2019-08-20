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

	public static <K extends Serializable, V> PortalCache<K, V>
		getWrappedPortalCache(PortalCache<K, V> portalCache) {

		PortalCache<K, V> currentPortalCache = portalCache;

		while (currentPortalCache instanceof PortalCacheWrapper) {
			PortalCacheWrapper<K, V> portalCacheWrapper =
				(PortalCacheWrapper<K, V>)currentPortalCache;

			currentPortalCache = portalCacheWrapper.getWrappedPortalCache();
		}

		return currentPortalCache;
	}

}