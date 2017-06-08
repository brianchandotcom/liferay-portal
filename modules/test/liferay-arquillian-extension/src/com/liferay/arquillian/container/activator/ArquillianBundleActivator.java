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

package com.liferay.arquillian.container.activator;

import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.management.ManagementFactory;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.protocol.jmx.JMXTestRunner;
import org.jboss.arquillian.protocol.jmx.JMXTestRunner.TestClassLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public class ArquillianBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		final Bundle bundle = bundleContext.getBundle();

		_jmxTestRunner = new JMXTestRunner(
			new TestClassLoader() {

				@Override
				public Class<?> loadTestClass(String className)
					throws ClassNotFoundException {

					return bundle.loadClass(className);
				}

			});

		_jmxTestRunner.registerMBean(
			ManagementFactory.getPlatformMBeanServer());

		for (BundleActivator bundleActivator : _getBundleActivators()) {
			bundleActivator.start(bundleContext);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		for (BundleActivator bundleActivator : _getBundleActivators()) {
			bundleActivator.stop(bundleContext);
		}

		_jmxTestRunner.unregisterMBean(
			ManagementFactory.getPlatformMBeanServer());
	}

	private List<BundleActivator> _getBundleActivators() throws Exception {
		if (_bundleActivators != null) {
			return _bundleActivators;
		}

		_bundleActivators = new ArrayList<>();

		Class<?> clazz = getClass();

		for (String className : StringUtil.splitLines(
				StringUtil.read(
					clazz.getClassLoader(),
					"/META-INF/services/" + BundleActivator.class.getName(),
					true))) {

			_bundleActivators.add(
				(BundleActivator)InstanceFactory.newInstance(
					clazz.getClassLoader(), className));
		}

		return _bundleActivators;
	}

	private List<BundleActivator> _bundleActivators;
	private JMXTestRunner _jmxTestRunner;

}