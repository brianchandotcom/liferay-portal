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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cache.cluster.EhcachePortalCacheClusterReplicatorFactory;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvMethodRule;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.FactoryConfiguration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class EhcacheConfigurationUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		_configurationURL = EhcacheConfigurationUtilTest.class.getResource(
			"dependencies/test-multi-ehcache-config.xml");

		Assert.assertNotNull(_configurationURL);

		_configuration = ConfigurationFactory.parseConfiguration(
			_configurationURL);
	}

	@AdviseWith(adviceClasses = {DisableEhcacheBootstrapAdvice.class})
	@Test
	public void testBootstrapDisabled() {
		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, false);

		_assertBootStrap(configuration, false);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, true);

		_assertBootStrap(configuration, false);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, false);

		_assertBootStrap(configuration, false);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, true);

		_assertBootStrap(configuration, false);
	}

	@AdviseWith(adviceClasses = {EnableEhcacheBootstrapAdvice.class})
	@Test
	public void testBootstrapEnabled() {
		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, false);

		_assertBootStrap(configuration, true);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, true);

		_assertBootStrap(configuration, true);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, false);

		_assertBootStrap(configuration, true);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, true);

		_assertBootStrap(configuration, true);
	}

	@AdviseWith(adviceClasses = {DisableClusterLinkAdvice.class})
	@Test
	public void testClusterDisabled() {
		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, false);

		_assertListenerConfigsEquals(_configuration, configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, true);

		_assertListenerConfigsEquals(_configuration, configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, false);

		_assertNoDefaultReplicatorConfigs(configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, true);

		_assertNoListenerConfigs(configuration);
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class,
			DisableClusterLinkReplicateAdvice.class
		}
	)
	@Test
	public void testClusterEnabled1() {
		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, false);

		_assertListenerConfigsEquals(_configuration, configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, true);

		_assertListenerConfigsEquals(_configuration, configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, false);

		_assertListenerConfigsEquals(_configuration, configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, true);

		_assertListenerConfigsEquals(_configuration, configuration);
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkAdvice.class,
			EnableClusterLinkReplicateAdvice.class
		}
	)
	@Test
	public void testClusterEnabled2() {
		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, false);

		_assertListenerConfigsEquals(_configuration, configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, false, true);

		_assertListenerConfigsEquals(_configuration, configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, false);

		_assertClusterLinkReplicatorConfigs(configuration, false);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			_configurationURL, true, true);

		_assertClusterLinkReplicatorConfigs(configuration, true);
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testMisc() {
		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			"WrongConfigurationPath");

		Assert.assertNull(configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			StringPool.BLANK, true);

		Assert.assertNull(configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			StringPool.BLANK, true, true);

		Assert.assertNull(configuration);

		URL configurationURL = EhcacheConfigurationUtilTest.class.getResource(
			"dependencies/test-single-ehcache-config.xml");

		Assert.assertNotNull(configurationURL);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			configurationURL);

		Assert.assertNotNull(configuration);

		configuration = EhcacheConfigurationUtil.getConfiguration(
			configurationURL, true);

		Assert.assertNotNull(configuration);

		new EhcacheConfigurationUtil();
	}

	@Rule
	public final AspectJNewEnvMethodRule aspectJNewEnvMethodRule =
		new AspectJNewEnvMethodRule();

	@Aspect
	public static class DisableClusterLinkAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object disableClusterLink(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class DisableClusterLinkReplicateAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED)")
		public Object disableClusterLinkReplicate(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class DisableEhcacheBootstrapAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"EHCACHE_BOOTSTRAP_CACHE_LOADER_ENABLED)")
		public Object disableEhcacheBootStrap(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class EnableClusterLinkAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object enableClusterLink(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	@Aspect
	public static class EnableClusterLinkReplicateAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED)")
		public Object enableClusterLinkReplicate(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	@Aspect
	public static class EnableEhcacheBootstrapAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"EHCACHE_BOOTSTRAP_CACHE_LOADER_ENABLED)")
		public Object enableEhcacheBootstrap(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	private void _assertBootStrap(
		Configuration configuration, boolean enabled) {

		List<CacheConfiguration> cacheConfigurations =
			_getAllCacheConfigurations(configuration);

		for (CacheConfiguration cacheConfiguration : cacheConfigurations) {
			if (enabled) {
				Assert.assertNotNull(
					cacheConfiguration.
						getBootstrapCacheLoaderFactoryConfiguration());
			}
			else {
				Assert.assertNull(
					cacheConfiguration.
						getBootstrapCacheLoaderFactoryConfiguration());
			}
		}
	}

	private void _assertClusterLinkReplicatorConfigs(
		Configuration configuration, boolean onlyReplicator) {

		List<?> peerListenerFactoryConfigurations =
			configuration.getCacheManagerPeerListenerFactoryConfigurations();

		Assert.assertTrue(peerListenerFactoryConfigurations.isEmpty());

		List<?> peerProviderFactoryConfigurations =
			configuration.getCacheManagerPeerProviderFactoryConfiguration();

		Assert.assertTrue(peerProviderFactoryConfigurations.isEmpty());

		List<CacheConfiguration> cacheConfigurations =
			_getAllCacheConfigurations(configuration);

		for (CacheConfiguration cacheConfiguration : cacheConfigurations) {
			List<FactoryConfiguration<?>> factoryConfigurations =
				cacheConfiguration.getCacheEventListenerConfigurations();

			if (onlyReplicator) {
				Assert.assertEquals(1, factoryConfigurations.size());
			}
			else {
				Assert.assertTrue(factoryConfigurations.size() > 1);
			}

			boolean exist = false;

			for (FactoryConfiguration<?> factoryConfiguration :
					factoryConfigurations) {

				String fullyQualifiedClassPath =
					factoryConfiguration. getFullyQualifiedClassPath();

				if (fullyQualifiedClassPath.equals(
						EhcachePortalCacheClusterReplicatorFactory.class.
							getName())) {

					Assert.assertEquals(
						_CACHE_EVENT_LISTENER_PROPERTIES,
						factoryConfiguration.getProperties());

					exist = true;

					break;
				}
			}

			if (!exist) {
				Assert.fail();
			}
		}
	}

	private void _assertListenerConfigsEquals(
		Configuration configuration1, Configuration configuration2) {

		Assert.assertEquals(
			configuration1.getCacheManagerPeerProviderFactoryConfiguration(),
			configuration2.getCacheManagerPeerProviderFactoryConfiguration());
		Assert.assertEquals(
			configuration1.getCacheManagerPeerListenerFactoryConfigurations(),
			configuration2.getCacheManagerPeerListenerFactoryConfigurations());

		CacheConfiguration cacheConfiguration1 =
			configuration1.getDefaultCacheConfiguration();
		CacheConfiguration cacheConfiguration2 =
			configuration2.getDefaultCacheConfiguration();

		Assert.assertEquals(
			cacheConfiguration1.getCacheEventListenerConfigurations(),
			cacheConfiguration2.getCacheEventListenerConfigurations());

		_assertListenerConfigsEquals(
			configuration1.getCacheConfigurations(),
			configuration1.getCacheConfigurations());
	}

	private void _assertListenerConfigsEquals(
		Map<String, CacheConfiguration> cacheConfigurations1,
		Map<String, CacheConfiguration> cacheConfigurations2) {

		if (cacheConfigurations1 == cacheConfigurations2) {
			return;
		}

		Assert.assertEquals(
			cacheConfigurations1.size(), cacheConfigurations1.size());

		if (cacheConfigurations1.isEmpty()) {
			return;
		}

		try {
			for (Entry<String, CacheConfiguration> entry :
					cacheConfigurations1.entrySet()) {

				String key = entry.getKey();
				CacheConfiguration cacheConfiguration1 = entry.getValue();

				CacheConfiguration cacheConfiguration2 =
					cacheConfigurations2.get(key);

				if (cacheConfiguration1 == null) {
					if ((cacheConfiguration2 != null) ||
						cacheConfigurations2.containsKey(key)) {

						Assert.fail();
					}
				}
				else {
					Assert.assertEquals(
						cacheConfiguration1.
							getCacheEventListenerConfigurations(),
						cacheConfiguration2.
							getCacheEventListenerConfigurations());
				}
			}
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

	private void _assertNoDefaultReplicatorConfigs(
		Configuration configuration) {

		List<?> peerListenerFactoryConfigurations =
			configuration.getCacheManagerPeerListenerFactoryConfigurations();

		Assert.assertTrue(peerListenerFactoryConfigurations.isEmpty());

		List<?> peerProviderFactoryConfigurations =
			configuration.getCacheManagerPeerProviderFactoryConfiguration();

		Assert.assertTrue(peerProviderFactoryConfigurations.isEmpty());

		List<CacheConfiguration> cacheConfigurations =
			_getAllCacheConfigurations(configuration);

		for (CacheConfiguration cacheConfiguration : cacheConfigurations) {
			List<FactoryConfiguration<?>> factoryConfigurations =
				cacheConfiguration.getCacheEventListenerConfigurations();

			for (FactoryConfiguration<?> factoryConfiguration :
					factoryConfigurations) {

				String fullyQualifiedClassPath =
					factoryConfiguration.getFullyQualifiedClassPath();

				if (fullyQualifiedClassPath.contains(
						"LiferayCacheEventListenerFactory") ||
					fullyQualifiedClassPath.contains(
						"net.sf.ehcache.distribution")) {

					Assert.fail();
				}
			}
		}
	}

	private void _assertNoListenerConfigs(Configuration configuration) {
		List<?> peerListenerFactoryConfigurations =
			configuration.getCacheManagerPeerListenerFactoryConfigurations();

		Assert.assertTrue(peerListenerFactoryConfigurations.isEmpty());

		List<?> peerProviderFactoryConfigurations =
			configuration.getCacheManagerPeerProviderFactoryConfiguration();

		Assert.assertTrue(peerProviderFactoryConfigurations.isEmpty());

		List<CacheConfiguration> cacheConfigurations =
			_getAllCacheConfigurations(configuration);

		for (CacheConfiguration cacheConfiguration : cacheConfigurations) {
			List<?> cacheEventListenerConfigurations =
				cacheConfiguration.getCacheEventListenerConfigurations();

			Assert.assertTrue(cacheEventListenerConfigurations.isEmpty());
		}
	}

	private List<CacheConfiguration> _getAllCacheConfigurations(
		Configuration configuration) {

		List<CacheConfiguration> cacheConfigurations =
			new ArrayList<CacheConfiguration>();

		CacheConfiguration defaultCacheConfiguration =
			configuration.getDefaultCacheConfiguration();

		if (defaultCacheConfiguration != null) {
			cacheConfigurations.add(defaultCacheConfiguration);
		}

		Map<String, CacheConfiguration> cacheConfigurationsMap =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurationsMap.values()) {

			cacheConfigurations.add(cacheConfiguration);
		}

		return cacheConfigurations;
	}

	private static final String _CACHE_EVENT_LISTENER_PROPERTIES =
		"testKey=testValue";

	private Configuration _configuration;
	private URL _configurationURL;

}