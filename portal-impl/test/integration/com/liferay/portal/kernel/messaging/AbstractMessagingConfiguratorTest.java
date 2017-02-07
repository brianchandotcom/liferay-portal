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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.messaging.config.AbstractMessagingConfigurator;
import com.liferay.portal.kernel.messaging.config.DefaultMessagingConfigurator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class AbstractMessagingConfiguratorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCustomClassLoaderDestinationConfiguration() {
		final TestClassLoader testClassLoader = new TestClassLoader();

		AbstractMessagingConfigurator pluginMessagingConfigurator =
			new AbstractMessagingConfigurator() {

				@Override
				protected ClassLoader getOperatingClassloader() {
					return testClassLoader;
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

		pluginMessagingConfigurator.setDestinationConfigurations(
			destinationConfigurations);

		Map<String, List<MessageListener>> messageListeners = new HashMap<>();

		List<MessageListener> messageListenersList = new ArrayList<>();

		messageListeners.put("liferay/plugintest1", messageListenersList);

		messageListenersList.add(
			new TestClassLoaderMessageListener(testClassLoader));

		pluginMessagingConfigurator.setMessageListeners(messageListeners);

		pluginMessagingConfigurator.afterPropertiesSet();

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(destination.name=*plugintest*)(objectClass=com.liferay." +
				"portal.kernel.messaging.Destination))");

		ServiceTracker<Destination, Destination> serviceTracker =
			registry.trackServices(filter);

		serviceTracker.open();

		try {
			while (ArrayUtil.isEmpty(serviceTracker.getServices())) {
				Thread.sleep(1000);
			}

			Object[] services = serviceTracker.getServices();

			Assert.assertEquals(Arrays.toString(services), 2, services.length);

			for (Object service : services) {
				Destination destination = (Destination)service;

				String destinationName = destination.getName();

				Assert.assertTrue(destinationName.contains("plugintest"));

				if (destinationName.equals("liferay/plugintest1")) {
					Assert.assertEquals(
						1, destination.getMessageListenerCount());
				}

				if (destination.getMessageListenerCount() > 0) {
					Message message = new Message();

					message.setDestinationName(destinationName);

					destination.send(message);
				}
			}
		}
		catch (Exception e) {
			Assert.fail(StackTraceUtil.getStackTrace(e));
		}
	}

	@Test
	public void testPortalClassLoaderDestinationConfiguration() {
		DefaultMessagingConfigurator defaultMessagingConfigurator =
			new DefaultMessagingConfigurator();

		Set<DestinationConfiguration> destinationConfigurations =
			new HashSet<>();

		destinationConfigurations.add(
			DestinationConfiguration.createSynchronousDestinationConfiguration(
				"liferay/portaltest1"));
		destinationConfigurations.add(
			DestinationConfiguration.createParallelDestinationConfiguration(
				"liferay/portaltest2"));

		defaultMessagingConfigurator.setDestinationConfigurations(
			destinationConfigurations);

		Map<String, List<MessageListener>> messageListeners = new HashMap<>();

		List<MessageListener> messageListenersList1 = new ArrayList<>();

		messageListeners.put("liferay/portaltest1", messageListenersList1);

		messageListenersList1.add(
			new TestMessageListener("liferay/portaltest1"));

		List<MessageListener> messageListenersList2 = new ArrayList<>();

		messageListeners.put("liferay/portaltest2", messageListenersList2);

		messageListenersList2.add(
			new TestMessageListener("liferay/portaltest2"));

		defaultMessagingConfigurator.setMessageListeners(messageListeners);

		defaultMessagingConfigurator.afterPropertiesSet();

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(destination.name=*portaltest*)(objectClass=com.liferay." +
				"portal.kernel.messaging.Destination))");

		ServiceTracker<Destination, Destination> serviceTracker =
			registry.trackServices(filter);

		serviceTracker.open();

		try {
			while (ArrayUtil.isEmpty(serviceTracker.getServices())) {
				Thread.sleep(1000);
			}

			Object[] services = serviceTracker.getServices();

			Assert.assertEquals(Arrays.toString(services), 2, services.length);

			for (Object service : services) {
				Destination destination = (Destination)service;

				String destinationName = destination.getName();

				Assert.assertTrue(destinationName.contains("portaltest"));

				if (destinationName.equals("liferay/portaltest1")) {
					Assert.assertEquals(
						1, destination.getMessageListenerCount());
				}

				if (destination.getMessageListenerCount() > 0) {
					Message message = new Message();

					message.setDestinationName(destinationName);

					destination.send(message);
				}
			}
		}
		catch (Exception e) {
			Assert.fail(StackTraceUtil.getStackTrace(e));
		}
	}

	private static class TestClassLoader extends ClassLoader {
	}

	private static class TestClassLoaderMessageListener
		implements MessageListener {

		public TestClassLoaderMessageListener(TestClassLoader testClassLoader) {
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