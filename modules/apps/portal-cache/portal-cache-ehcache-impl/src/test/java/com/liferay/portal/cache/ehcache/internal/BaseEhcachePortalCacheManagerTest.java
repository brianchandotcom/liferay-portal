/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.Configuration;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BaseEhcachePortalCacheManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testInitialize() {
		BaseEhcachePortalCacheManager<String, String>
			baseEhcachePortalCacheManager =
				new BaseEhcachePortalCacheManager<String, String>() {
				};

		baseEhcachePortalCacheManager.bundleContext =
			SystemBundleUtil.getBundleContext();

		baseEhcachePortalCacheManager.setConfigFile(
			"ehcache/liferay-single-vm.xml");
		baseEhcachePortalCacheManager.setPortalCacheManagerName(
			BaseEhcachePortalCacheManagerTest.class.getName());

		baseEhcachePortalCacheManager.initialize();

		try {
			CacheManager cacheManager =
				baseEhcachePortalCacheManager.getEhcacheManager();

			Configuration configuration =
				cacheManager.getRuntimeConfiguration();

			Map<String, CacheConfiguration<?, ?>> cacheConfigurations =
				configuration.getCacheConfigurations();

			Assert.assertTrue(
				String.valueOf(cacheConfigurations.keySet()),
				cacheConfigurations.containsKey("ext"));
		}
		finally {
			baseEhcachePortalCacheManager.destroy();
		}
	}

}