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

package com.liferay.portal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.config.BaseMessagingConfigurator;
import com.liferay.portal.kernel.messaging.config.DefaultMessagingConfigurator;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Michael C. Han
 */
@RunWith(Arquillian.class)
public class BaseMessagingConfiguratorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			BaseMessagingConfiguratorTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() {
		_serviceTracker.close();

		_baseMessagingConfigurator.destroy();
	}

	@Test
	public void testCustomClassLoaderDestinationConfiguration()
		throws InterruptedException, InvalidSyntaxException {

		final ClassLoader testClassLoader = new ClassLoader() {
		};

		CountDownLatch countDownLatch = new CountDownLatch(1);

		_baseMessagingConfigurator = new BaseMessagingConfigurator() {

			@Override
			protected ClassLoader getOperatingClassloader() {
				return testClassLoader;
			}

			protected void initialize() {
				super.initialize();

				countDownLatch.countDown();
			}

		};

		Set<DestinationConfiguration> destinationConfigurations =
			new HashSet<>();

		destinationConfigurations.add(
			DestinationConfiguration.createSynchronousDestinationConfiguration(
				"liferay/plugintest1"));
		destinationConfigurations.add(
			DestinationConfiguration.createParallelDestinationConfiguration(
				"liferay/plugintest2"));

		_baseMessagingConfigurator.setDestinationConfigurations(
			destinationConfigurations);

		List<MessageListener> messageListenersList = new ArrayList<>();

		Map<String, List<MessageListener>> messageListeners =
			HashMapBuilder.<String, List<MessageListener>>put(
				"liferay/plugintest1", messageListenersList
			).build();

		messageListenersList.add(
			new TestClassLoaderMessageListener(testClassLoader));

		_baseMessagingConfigurator.setMessageListeners(messageListeners);

		_baseMessagingConfigurator.afterPropertiesSet();

		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			_bundleContext.createFilter(
				"(&(destination.name=*plugintest*)(objectClass=com.liferay." +
					"portal.kernel.messaging.Destination))"),
			null);

		_serviceTracker.open();

		countDownLatch.await();

		Object[] services = _serviceTracker.getServices();

		Assert.assertEquals(Arrays.toString(services), 2, services.length);

		for (Object service : services) {
			Destination destination = (Destination)service;

			String destinationName = destination.getName();

			Assert.assertTrue(
				destinationName, destinationName.contains("plugintest"));

			if (destinationName.equals("liferay/plugintest1")) {
				Assert.assertEquals(1, destination.getMessageListenerCount());
			}

			if (destination.getMessageListenerCount() > 0) {
				Message message = new Message();

				message.setDestinationName(destinationName);

				destination.send(message);
			}
		}
	}

	@Test
	public void testPortalClassLoaderDestinationConfiguration()
		throws InterruptedException, InvalidSyntaxException {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		_baseMessagingConfigurator = new DefaultMessagingConfigurator() {

			protected void initialize() {
				super.initialize();

				countDownLatch.countDown();
			}

		};

		Set<DestinationConfiguration> destinationConfigurations =
			new HashSet<>();

		destinationConfigurations.add(
			DestinationConfiguration.createSynchronousDestinationConfiguration(
				"liferay/portaltest1"));
		destinationConfigurations.add(
			DestinationConfiguration.createParallelDestinationConfiguration(
				"liferay/portaltest2"));

		_baseMessagingConfigurator.setDestinationConfigurations(
			destinationConfigurations);

		List<MessageListener> messageListenersList1 = new ArrayList<>();

		Map<String, List<MessageListener>> messageListeners =
			HashMapBuilder.<String, List<MessageListener>>put(
				"liferay/portaltest1", messageListenersList1
			).build();

		messageListenersList1.add(
			new TestMessageListener("liferay/portaltest1"));

		List<MessageListener> messageListenersList2 = new ArrayList<>();

		messageListeners.put("liferay/portaltest2", messageListenersList2);

		messageListenersList2.add(
			new TestMessageListener("liferay/portaltest2"));

		_baseMessagingConfigurator.setMessageListeners(messageListeners);

		_baseMessagingConfigurator.afterPropertiesSet();

		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			_bundleContext.createFilter(
				"(&(destination.name=*portaltest*)(objectClass=com.liferay." +
					"portal.kernel.messaging.Destination))"),
			null);

		_serviceTracker.open();

		countDownLatch.await();

		Object[] services = _serviceTracker.getServices();

		Assert.assertEquals(Arrays.toString(services), 2, services.length);

		for (Object service : services) {
			Destination destination = (Destination)service;

			String destinationName = destination.getName();

			Assert.assertTrue(
				destinationName, destinationName.contains("portaltest"));

			if (destinationName.equals("liferay/portaltest1")) {
				Assert.assertEquals(1, destination.getMessageListenerCount());
			}

			if (destination.getMessageListenerCount() > 0) {
				Message message = new Message();

				message.setDestinationName(destinationName);

				destination.send(message);
			}
		}
	}

	private static BundleContext _bundleContext;

	private BaseMessagingConfigurator _baseMessagingConfigurator;
	private ServiceTracker<Destination, Destination> _serviceTracker;

	private static class TestClassLoaderMessageListener
		implements MessageListener {

		public TestClassLoaderMessageListener(ClassLoader testClassLoader) {
			_testClassLoader = testClassLoader;
		}

		@Override
		public void receive(Message message) {
			Thread currentThread = Thread.currentThread();

			ClassLoader currentClassLoader =
				currentThread.getContextClassLoader();

			Assert.assertEquals(_testClassLoader, currentClassLoader);
		}

		private final ClassLoader _testClassLoader;

	}

	private static class TestMessageListener implements MessageListener {

		public TestMessageListener(String destinationName) {
			_destinationName = destinationName;
		}

		@Override
		public void receive(Message message) {
			Assert.assertEquals(_destinationName, message.getDestinationName());
		}

		private final String _destinationName;

	}

}