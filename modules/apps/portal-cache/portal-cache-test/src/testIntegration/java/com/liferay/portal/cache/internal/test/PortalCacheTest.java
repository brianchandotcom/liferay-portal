/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class PortalCacheTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testPutWithTimeToLive() throws Exception {
		List<PortalCache<String, String>> portalCaches = Arrays.asList(
			PortalCacheHelperUtil.getPortalCache(
				PortalCacheManagerNames.MULTI_VM, "test.cache.multi"),
			PortalCacheHelperUtil.getPortalCache(
				PortalCacheManagerNames.SINGLE_VM, "test.cache.single"),
			PortalCacheHelperUtil.getPortalCache(
				PortalCacheManagerNames.SINGLE_VM,
				"com.liferay.portal.servlet.ComboServlet"));

		try {
			for (PortalCache<String, String> portalCache : portalCaches) {
				portalCache.put("key1", "value", 60);
				portalCache.put("key2", "value");

				Object ehcachePortalCache = ReflectionTestUtil.getFieldValue(
					portalCache, "_portalCache");

				Object ehcache = ReflectionTestUtil.invoke(
					ehcachePortalCache, "getEhcache", new Class<?>[0]);

				Object store = ReflectionTestUtil.getFieldValue(
					ehcache, "store");

				Object valueHolder = ReflectionTestUtil.invoke(
					store, "get", new Class<?>[] {Object.class}, "key1");

				long creationTime = ReflectionTestUtil.invoke(
					valueHolder, "creationTime", new Class<?>[0]);
				long expirationTime = ReflectionTestUtil.invoke(
					valueHolder, "expirationTime", new Class<?>[0]);

				Assert.assertEquals(
					portalCache.getPortalCacheName(), 60000,
					expirationTime - creationTime);

				valueHolder = ReflectionTestUtil.invoke(
					store, "get", new Class<?>[] {Object.class}, "key2");

				expirationTime = ReflectionTestUtil.invoke(
					valueHolder, "expirationTime", new Class<?>[0]);
				long lastAccessTime = ReflectionTestUtil.invoke(
					valueHolder, "lastAccessTime", new Class<?>[0]);

				Assert.assertEquals(
					portalCache.getPortalCacheName(), 600000,
					expirationTime - lastAccessTime);

				portalCache.remove("key1");
				portalCache.remove("key2");
			}
		}
		finally {
			PortalCacheHelperUtil.removePortalCache(
				PortalCacheManagerNames.MULTI_VM, "test.cache.multi");
			PortalCacheHelperUtil.removePortalCache(
				PortalCacheManagerNames.SINGLE_VM, "test.cache.single");
		}
	}

}