/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.test.util;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Jiefeng Wu
 */
public class PortalCacheReplicationTestUtil {

	public static PortalCacheListener<?, ?> getPortalCacheListener(
		String className, PortalCache<?, ?> portalCache) {

		portalCache = ReflectionTestUtil.getFieldValue(
			portalCache, "_portalCache");

		Object aggregatedPortalCacheListener = ReflectionTestUtil.getFieldValue(
			portalCache, "aggregatedPortalCacheListener");

		ConcurrentMap<PortalCacheListener<?, ?>, PortalCacheListenerScope>
			portalCacheListeners = ReflectionTestUtil.getFieldValue(
				aggregatedPortalCacheListener, "_portalCacheListeners");

		for (PortalCacheListener<?, ?> portalCacheListener :
				portalCacheListeners.keySet()) {

			Class<?> clazz = portalCacheListener.getClass();

			if (Objects.equals(clazz.getName(), className)) {
				return portalCacheListener;
			}
		}

		throw new IllegalStateException(className + " does not exist");
	}

	public static void setReplicatorFieldValue(
		PortalCache<?, ?> portalCache, String fieldName, boolean fieldValue) {

		ReflectionTestUtil.setFieldValue(
			(Object)ReflectionTestUtil.getFieldValue(
				getPortalCacheListener(
					"com.liferay.portal.cache.ehcache.internal.events." +
						"EhcachePortalCacheReplicatorUtil$" +
							"EhcachePortalCacheReplicator",
					portalCache),
				"_portalCacheReplicator"),
			fieldName, fieldValue);
	}

}