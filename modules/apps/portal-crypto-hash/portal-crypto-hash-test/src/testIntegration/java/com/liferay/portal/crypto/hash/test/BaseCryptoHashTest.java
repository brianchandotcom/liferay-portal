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

package com.liferay.portal.crypto.hash.test;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedServiceFactory;

/**
 * @author Carlos Sierra Andrés
 */
public class BaseCryptoHashTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(CryptoHashTest.class);

		bundleContext = bundle.getBundleContext();
	}

	@Before
	public void setUp() {
		autoCloseables = new ArrayList<>();
	}

	@After
	public void tearDown() {
		ListIterator<AutoCloseable> listIterator = autoCloseables.listIterator(
			autoCloseables.size());

		while (listIterator.hasPrevious()) {
			AutoCloseable previousAutoCloseable = listIterator.previous();

			try {
				previousAutoCloseable.close();
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}
	}

	protected <S, R, E extends Throwable> R callService(
			BundleContext bundleContext, Class<S> serviceClass,
			String filterString, UnsafeFunction<S, R, E> unsafeFunction)
		throws E {

		ServiceReference<S>[] serviceReferences = null;

		try {
			serviceReferences =
				(ServiceReference<S>[])bundleContext.getAllServiceReferences(
					serviceClass.getName(), filterString);
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			ReflectionUtil.throwException(invalidSyntaxException);
		}

		if (serviceReferences == null) {
			return unsafeFunction.apply(null);
		}

		if (ArrayUtil.isEmpty(serviceReferences)) {
			return unsafeFunction.apply(null);
		}

		ServiceReference<S> serviceReference = serviceReferences[0];

		try {
			return unsafeFunction.apply(
				bundleContext.getService(serviceReference));
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	protected Configuration createFactoryConfiguration(
		BundleContext bundleContext, String factoryPid,
		Dictionary<String, ?> properties) {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Dictionary<String, Object> registrationProperties =
			new HashMapDictionary<>();

		registrationProperties.put(Constants.SERVICE_PID, factoryPid);

		ServiceRegistration<ManagedServiceFactory> serviceRegistration =
			bundleContext.registerService(
				ManagedServiceFactory.class,
				new ManagedServiceFactory() {

					@Override
					public void deleted(String pid) {
					}

					@Override
					public String getName() {
						return "Test managed service factory for PID " +
							factoryPid;
					}

					@Override
					public void updated(
						String pid, Dictionary<String, ?> updatedProperties) {

						if (updatedProperties == null) {
							return;
						}

						if (isIncluded(properties, updatedProperties)) {
							countDownLatch.countDown();
						}
					}

				},
				registrationProperties);

		try {
			ServiceReference<ConfigurationAdmin> serviceReference =
				bundleContext.getServiceReference(ConfigurationAdmin.class);

			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			Configuration configuration = null;

			try {
				configuration = configurationAdmin.createFactoryConfiguration(
					factoryPid, StringPool.QUESTION);

				configuration.update(properties);

				countDownLatch.await(5, TimeUnit.MINUTES);

				return configuration;
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
			catch (InterruptedException interruptedException) {
				try {
					configuration.delete();
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}

				throw new RuntimeException(interruptedException);
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	protected Configuration createFactoryConfiguration(
		String factoryPid, Dictionary<String, ?> properties) {

		Configuration configuration = createFactoryConfiguration(
			bundleContext, factoryPid, properties);

		String configurationPid = configuration.getPid();

		autoCloseables.add(
			() -> {
				CountDownLatch countDownLatch = new CountDownLatch(1);

				Dictionary<String, Object> registrationProperties =
					new HashMapDictionary<>();

				registrationProperties.put(Constants.SERVICE_PID, factoryPid);

				ServiceRegistration<ManagedServiceFactory> serviceRegistration =
					bundleContext.registerService(
						ManagedServiceFactory.class,
						new ManagedServiceFactory() {

							@Override
							public void deleted(String pid) {
								if (configurationPid.equals(pid)) {
									countDownLatch.countDown();
								}
							}

							@Override
							public String getName() {
								return "Test managed service factory for PID " +
									factoryPid;
							}

							@Override
							public void updated(
								String pid,
								Dictionary<String, ?> updatedProperties) {
							}

						},
						registrationProperties);

				try {
					configuration.delete();

					countDownLatch.await(10, TimeUnit.SECONDS);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
				finally {
					serviceRegistration.unregister();
				}
			});

		return configuration;
	}

	protected boolean isIncluded(
		Dictionary<String, ?> properties1, Dictionary<String, ?> properties2) {

		if (properties1.size() > properties2.size()) {
			return false;
		}

		Enumeration<String> enumeration = properties1.keys();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			if (!Objects.deepEquals(
					properties1.get(key), properties2.get(key))) {

				return false;
			}
		}

		return true;
	}

	protected byte[] randomBytes() {
		String string = RandomTestUtil.randomString();

		return string.getBytes();
	}

	protected static BundleContext bundleContext;

	protected ArrayList<AutoCloseable> autoCloseables;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCryptoHashTest.class);

}