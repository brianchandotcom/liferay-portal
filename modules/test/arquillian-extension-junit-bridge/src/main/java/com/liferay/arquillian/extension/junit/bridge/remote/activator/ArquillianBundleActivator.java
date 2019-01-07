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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.management.ManagementFactory;

import java.net.URL;

import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

		// Execute all activators

		_bundleActivators = _loadActivators();

		for (BundleActivator bundleActivator : _bundleActivators) {
			bundleActivator.start(context);
		}

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

		// Execute all activators

		for (BundleActivator bundleActivator : _bundleActivators) {
			bundleActivator.stop(context);
		}

		// Unregister the JMXTestRunner

		MBeanServer mBeanServer = _findOrCreateMBeanServer();

		_testRunner.unregisterMBean(mBeanServer);
	}

	private void _addBundleActivatorToActivatorsListFromStringLine(
		Set<BundleActivator> activators, String line) {

		if (line.startsWith("!")) {
			return;
		}

		ClassLoader classLoader = getClass().getClassLoader();

		try {
			Class<?> aClass = classLoader.loadClass(line);

			Class<? extends BundleActivator> bundleActivatorClass =
				aClass.asSubclass(BundleActivator.class);

			activators.add(bundleActivatorClass.newInstance());
		}
		catch (ClassNotFoundException cnfe) {
			throw new IllegalStateException(
				"Activator " + line + " class not found", cnfe);
		}
		catch (ClassCastException cce) {
			throw new IllegalStateException(
				"Activator " + line + " does not implement expected type " +
					BundleActivator.class.getCanonicalName(),
				cce);
		}
		catch (Exception e) {
			throw new IllegalStateException(
				"Activator " + line + " cannot be created ", e);
		}
	}

	private void _addBundleActivatorToActivatorsListFromURL(
			Set<BundleActivator> activators, URL url)
		throws IOException {

		final InputStream is = url.openStream();

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String line = reader.readLine();

			while (null != line) {
				line = _skipCommentAndTrim(line);

				_addBundleActivatorToActivatorsListFromStringLine(
					activators, line);

				line = reader.readLine();
			}
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
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

	private Set<BundleActivator> _loadActivators() {
		String serviceFile =
			_SERVICES + "/" + BundleActivator.class.getCanonicalName();

		Set<BundleActivator> activators = new LinkedHashSet<>();

		ClassLoader classLoader = getClass().getClassLoader();

		try {
			Enumeration<URL> enumeration = classLoader.getResources(
				serviceFile);

			while (enumeration.hasMoreElements()) {
				_addBundleActivatorToActivatorsListFromURL(
					activators, enumeration.nextElement());
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Could not load bundle activators", e);
		}

		return activators;
	}

	private String _skipCommentAndTrim(String line) {
		final int comment = line.indexOf('#');

		String lineWithoutComment = line;

		if (comment > -1) {
			lineWithoutComment = line.substring(0, comment);
		}

		return lineWithoutComment.trim();
	}

	private static final String _SERVICES = "/META-INF/services";

	private Set<BundleActivator> _bundleActivators;
	private JMXTestRunner _testRunner;

}