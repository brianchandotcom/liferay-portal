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

package com.liferay.counter.service;

import com.liferay.counter.model.Counter;
import com.liferay.portal.cache.key.SimpleCacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutorUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.BaseTestRule;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.HypersonicServerTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.io.File;

import java.lang.management.ManagementFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class CounterLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			false, new LiferayIntegrationTestRule(),
			MainServletTestRule.INSTANCE, HypersonicServerTestRule.INSTANCE,
			new BaseTestRule<>(
				new BaseTestCallback<Void, Void>() {

					@Override
					public void afterClass(Description description, Void v) {
						CounterLocalServiceUtil.reset(_COUNTER_NAME);
					}

					@Override
					public Void beforeClass(Description description)
						throws Exception {

						CounterLocalServiceUtil.reset(_COUNTER_NAME);

						Counter counter = CounterLocalServiceUtil.createCounter(
							_COUNTER_NAME);

						CounterLocalServiceUtil.updateCounter(counter);

						MBeanServer mBeanServer =
							ManagementFactory.getPlatformMBeanServer();

						for (ObjectName objectName :
								mBeanServer.queryNames(
									null,
									new ObjectName(
										"com.zaxxer.hikari:type=Pool (*"))) {

							mBeanServer.invoke(
								objectName, "softEvictConnections", null, null);
						}

						return null;
					}

				}));

	@Test
	public void testConcurrentIncrement() throws Exception {
		String classPath = getClassPath();

		List<String> arguments = new ArrayList<>();

		arguments.add("-Xmx1024m");
		arguments.add("-XX:MaxPermSize=200m");

		for (String property :
				HypersonicServerTestRule.INSTANCE.getJdbcProperties()) {

			arguments.add("-D" + property);
		}

		Builder builder = new Builder();

		builder.setArguments(arguments);
		builder.setBootstrapClassPath(classPath);
		builder.setReactClassLoader(PortalClassLoaderUtil.getClassLoader());
		builder.setRuntimeClassPath(classPath);

		ProcessConfig processConfig = builder.build();

		List<Future<Long[]>> futuresList = new ArrayList<>();

		for (int i = 0; i < _PROCESS_COUNT; i++) {
			ProcessCallable<Long[]> processCallable =
				new IncrementProcessCallable(
					"Increment Process-" + i, _COUNTER_NAME, _INCREMENT_COUNT);

			ProcessChannel<Long[]> processChannel = ProcessExecutorUtil.execute(
				processConfig, processCallable);

			Future<Long[]> futures =
				processChannel.getProcessNoticeableFuture();

			futuresList.add(futures);
		}

		int total = _PROCESS_COUNT * _INCREMENT_COUNT;

		List<Long> ids = new ArrayList<>(total);

		for (Future<Long[]> futures : futuresList) {
			ids.addAll(Arrays.asList(futures.get()));
		}

		Assert.assertEquals(total, ids.size());

		Collections.sort(ids);

		for (int i = 0; i < total; i++) {
			Long id = ids.get(i);

			Assert.assertEquals(i + 1, id.intValue());
		}
	}

	protected String getClassPath() {
		String classPath = ClassPathUtil.getJVMClassPath(true);

		if (PropsValues.JDBC_DEFAULT_LIFERAY_POOL_PROVIDER.equals("hikaricp")) {
			StringBundler sb = new StringBundler(5);

			sb.append(classPath);
			sb.append(File.pathSeparator);
			sb.append(PropsValues.LIFERAY_LIB_PORTAL_DIR);
			sb.append(File.separator);
			sb.append(
				PropsUtil.get(
					PropsKeys.SETUP_LIFERAY_POOL_PROVIDER_JAR_NAME,
					new Filter("hikaricp")));

			classPath = sb.toString();
		}

		return classPath;
	}

	private static final String _COUNTER_NAME = StringUtil.randomString();

	private static final int _INCREMENT_COUNT = 10000;

	private static final int _PROCESS_COUNT = 4;

	private static class IncrementProcessCallable
		implements ProcessCallable<Long[]> {

		public IncrementProcessCallable(
			String processName, String counterName, int incrementCount) {

			_processName = processName;
			_counterName = counterName;
			_incrementCount = incrementCount;
		}

		@Override
		public Long[] call() throws ProcessException {
			RegistryUtil.setRegistry(new BasicRegistryImpl());

			System.setProperty(
				PropsKeys.COUNTER_INCREMENT + "." + _counterName, "1");

			System.setProperty("catalina.base", ".");
			System.setProperty("external-properties", "portal-test.properties");
			System.setProperty("portal:jdbc.default.maxPoolSize", "1");
			System.setProperty("portal:jdbc.default.minPoolSize", "0");
			System.setProperty("portal:jdbc.default.maximumPoolSize", "1");
			System.setProperty("portal:jdbc.default.minimumIdle", "0");

			CacheKeyGeneratorUtil cacheKeyGeneratorUtil =
				new CacheKeyGeneratorUtil();

			cacheKeyGeneratorUtil.setDefaultCacheKeyGenerator(
				new SimpleCacheKeyGenerator());

			InitUtil.initWithSpring(
				Arrays.asList(
					"META-INF/base-spring.xml", "META-INF/counter-spring.xml"),
				false);

			List<Long> ids = new ArrayList<>();

			try {
				for (int i = 0; i < _incrementCount; i++) {
					ids.add(CounterLocalServiceUtil.increment(_counterName));
				}
			}
			catch (SystemException se) {
				throw new ProcessException(se);
			}

			return ids.toArray(new Long[ids.size()]);
		}

		@Override
		public String toString() {
			return _processName;
		}

		private static final long serialVersionUID = 1L;

		private final String _counterName;
		private final int _incrementCount;
		private final String _processName;

	}

}