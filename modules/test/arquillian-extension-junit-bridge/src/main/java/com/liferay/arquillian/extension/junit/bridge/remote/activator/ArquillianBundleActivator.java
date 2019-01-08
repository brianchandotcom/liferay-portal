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

package com.liferay.arquillian.extension.junit.bridge.remote.activator;

import java.lang.management.ManagementFactory;

import java.util.List;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.jboss.arquillian.protocol.jmx.JMXTestRunner;
import org.jboss.arquillian.testenricher.osgi.BundleAssociation;
import org.jboss.arquillian.testenricher.osgi.BundleContextAssociation;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Cristina González Castellano
 */
public class ArquillianBundleActivator implements BundleActivator {

	@Override
	public void start(final BundleContext context) throws Exception {
		final JMXTestRunner.TestClassLoader testClassLoader =
			new JMXTestRunner.TestClassLoader() {

				@Override
				public Class<?> loadTestClass(String className)
					throws ClassNotFoundException {

					return context.getBundle().loadClass(className);
				}

			};

		// Register the JMXTestRunner

		MBeanServer mBeanServer = _findOrCreateMBeanServer();

		_testRunner = new JMXTestRunner(testClassLoader) {

			@Override
			public byte[] runTestMethod(String className, String methodName) {
				BundleAssociation.setBundle(context.getBundle());
				BundleContextAssociation.setBundleContext(context);

				return super.runTestMethod(className, methodName);
			}

		};

		_testRunner.registerMBean(mBeanServer);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

		// Unregister the JMXTestRunner

		MBeanServer mBeanServer = _findOrCreateMBeanServer();

		_testRunner.unregisterMBean(mBeanServer);
	}

	private MBeanServer _findOrCreateMBeanServer() {
		MBeanServer mBeanServer = null;

		List<MBeanServer> serverArr = MBeanServerFactory.findMBeanServer(null);

		if (!serverArr.isEmpty()) {
			mBeanServer = serverArr.get(0);
		}

		if (mBeanServer == null) {
			mBeanServer = ManagementFactory.getPlatformMBeanServer();
		}

		return mBeanServer;
	}

	private JMXTestRunner _testRunner;

}