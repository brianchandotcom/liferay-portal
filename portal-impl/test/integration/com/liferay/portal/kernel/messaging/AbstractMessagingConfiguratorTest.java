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
	public void testCustomClassLoaderDestinationConfig() {
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
				"liferay/plugintest"));
		destinationConfigurations.add(
			DestinationConfiguration.createParallelDestinationConfiguration(
				"liferay/plugintest2"));

		pluginMessagingConfigurator.setDestinationConfigurations(
			destinationConfigurations);

		Map<String, List<MessageListener>> messageListeners = new HashMap<>();

		List<MessageListener> testListeners = new ArrayList<>();
		messageListeners.put("liferay/plugintest", testListeners);

		testListeners.add(new TestClassLoaderMessageListener(testClassLoader));

		pluginMessagingConfigurator.setMessageListeners(messageListeners);

		pluginMessagingConfigurator.afterPropertiesSet();

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(objectClass=com.liferay.portal.kernel.messaging.Destination)" +
				"(destination.name=*plugintest*))");

		ServiceTracker<Destination, Destination> tracker =
			registry.trackServices(filter);

		tracker.open();

		try {
			while (ArrayUtil.isEmpty(tracker.getServices())) {
				Thread.currentThread().sleep(1000);
			}

			Object[] services = tracker.getServices();

			Assert.assertEquals(2, services.length);

			for (Object service : services) {
				Destination destination = (Destination)service;

				Assert.assertTrue(destination.getName().contains("plugintest"));

				if (destination.getName().equals("liferay/plugintest")) {
					Assert.assertEquals(
						1, destination.getMessageListenerCount());
				}

				if (destination.getMessageListenerCount() > 0) {
					Message message = new Message();

					message.setDestinationName(destination.getName());

					destination.send(message);
				}
			}
		}
		catch (Exception e) {
			Assert.fail(StackTraceUtil.getStackTrace(e));
		}
	}

	@Test
	public void testPortalClassLoaderDestinationConfig() {
		DefaultMessagingConfigurator defaultMessagingConfigurator =
			new DefaultMessagingConfigurator();

		Set<DestinationConfiguration> destinationConfigurations =
			new HashSet<>();

		destinationConfigurations.add(
			DestinationConfiguration.createSynchronousDestinationConfiguration(
				"liferay/portaltest"));
		destinationConfigurations.add(
			DestinationConfiguration.createParallelDestinationConfiguration(
				"liferay/portaltest2"));

		defaultMessagingConfigurator.setDestinationConfigurations(
			destinationConfigurations);

		Map<String, List<MessageListener>> messageListeners = new HashMap<>();

		List<MessageListener> testListeners = new ArrayList<>();
		messageListeners.put("liferay/portaltest", testListeners);

		testListeners.add(new TestMessageListener("liferay/portaltest"));

		List<MessageListener> testListeners2 = new ArrayList<>();
		messageListeners.put("liferay/portaltest2", testListeners2);

		testListeners2.add(new TestMessageListener("liferay/portaltest2"));

		defaultMessagingConfigurator.setMessageListeners(messageListeners);

		defaultMessagingConfigurator.afterPropertiesSet();

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(objectClass=com.liferay.portal.kernel.messaging.Destination)" +
				"(destination.name=*portaltest*))");

		ServiceTracker<Destination, Destination> tracker =
			registry.trackServices(filter);

		tracker.open();

		try {
			while (ArrayUtil.isEmpty(tracker.getServices())) {
				Thread.currentThread().sleep(1000);
			}

			Object[] services = tracker.getServices();

			Assert.assertEquals(2, services.length);

			for (Object service : services) {
				Destination destination = (Destination)service;

				Assert.assertTrue(destination.getName().contains("portaltest"));

				if (destination.getName().equals("liferay/portaltest")) {
					Assert.assertEquals(
						1, destination.getMessageListenerCount());
				}

				if (destination.getMessageListenerCount() > 0) {
					Message message = new Message();

					message.setDestinationName(destination.getName());

					destination.send(message);
				}
			}
		}
		catch (Exception e) {
			Assert.fail(StackTraceUtil.getStackTrace(e));
		}
	}

	private class TestClassLoader extends ClassLoader {
	}

	private class TestClassLoaderMessageListener implements MessageListener {

		public TestClassLoaderMessageListener(TestClassLoader testClassLoader) {
			_testClassLoader = testClassLoader;
		}

		@Override
		public void receive(Message message) throws MessageListenerException {
			ClassLoader currentClassLoader =
				Thread.currentThread().getContextClassLoader();

			Assert.assertEquals(_testClassLoader, currentClassLoader);
		}

		private final ClassLoader _testClassLoader;

	}

	private class TestMessageListener implements MessageListener {

		public TestMessageListener(String destinationName) {
			_destinationName = destinationName;
		}

		@Override
		public void receive(Message message) throws MessageListenerException {
			Assert.assertEquals(_destinationName, message.getDestinationName());
		}

		private final String _destinationName;

	}

}