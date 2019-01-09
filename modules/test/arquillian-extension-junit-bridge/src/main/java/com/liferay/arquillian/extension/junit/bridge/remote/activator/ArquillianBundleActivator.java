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

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.jboss.arquillian.protocol.jmx.JMXTestRunner;
import org.jboss.arquillian.testenricher.osgi.BundleAssociation;
import org.jboss.arquillian.testenricher.osgi.BundleContextAssociation;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Cristina González Castellano
 */
public class ArquillianBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws JMException {
		Bundle bundle = bundleContext.getBundle();

		MBeanServer mBeanServer = _findOrCreateMBeanServer();

		_testRunner = new JMXTestRunner(bundle::loadClass) {

			@Override
			public byte[] runTestMethod(String className, String methodName) {
				BundleAssociation.setBundle(bundle);

				BundleContextAssociation.setBundleContext(bundleContext);

				return super.runTestMethod(className, methodName);
			}

		};

		_testRunner.registerMBean(mBeanServer);
	}

	@Override
	public void stop(BundleContext context) throws JMException {
		MBeanServer mBeanServer = _findOrCreateMBeanServer();

		_testRunner.unregisterMBean(mBeanServer);
	}

	private MBeanServer _findOrCreateMBeanServer() {
		MBeanServer mBeanServer = null;

		List<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(
			null);

		if (!mBeanServers.isEmpty()) {
			mBeanServer = mBeanServers.get(0);
		}

		if (mBeanServer == null) {
			mBeanServer = ManagementFactory.getPlatformMBeanServer();
		}

		return mBeanServer;
	}

	private JMXTestRunner _testRunner;

}