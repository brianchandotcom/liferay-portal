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

import com.liferay.arquillian.extension.junit.bridge.protocol.jmx.JMXTestRunner;
import com.liferay.arquillian.extension.junit.bridge.protocol.jmx.JMXTestRunnerMBean;

import java.lang.management.ManagementFactory;

import java.util.List;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Cristina González Castellano
 */
public class ArquillianBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws JMException {
		MBeanServer mBeanServer = _findOrCreateMBeanServer();

		Bundle bundle = bundleContext.getBundle();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		mBeanServer.registerMBean(
			new JMXTestRunner(bundleWiring.getClassLoader()), _objectName);
	}

	@Override
	public void stop(BundleContext bundleContext) throws JMException {
		MBeanServer mBeanServer = _findOrCreateMBeanServer();

		mBeanServer.unregisterMBean(_objectName);
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

	private static final ObjectName _objectName;

	static {
		try {
			_objectName = new ObjectName(JMXTestRunnerMBean.OBJECT_NAME);
		}
		catch (MalformedObjectNameException mone) {
			throw new ExceptionInInitializerError(mone);
		}
	}

}