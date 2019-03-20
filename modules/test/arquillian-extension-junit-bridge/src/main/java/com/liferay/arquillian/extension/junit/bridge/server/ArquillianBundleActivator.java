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

package com.liferay.arquillian.extension.junit.bridge.server;

import com.liferay.arquillian.extension.junit.bridge.constants.Headers;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import java.lang.annotation.Annotation;

import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.TestClass;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Cristina González Castellano
 */
public class ArquillianBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Bundle testBundle = bundleContext.getBundle();

		URL url = testBundle.getResource("/META-INF/MANIFEST.MF");

		Manifest manifest = new Manifest();

		try (InputStream inputStream = url.openStream()) {
			manifest.read(inputStream);
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException(
				"Unable to read test manifest", ioe);
		}

		Attributes attributes = manifest.getMainAttributes();

		String hostName = attributes.getValue(
			Headers.TEST_BRIDGE_REPORT_SERVER_HOST_NAME);

		InetAddress inetAddress = null;

		try {
			inetAddress = InetAddress.getByName(hostName);
		}
		catch (UnknownHostException uhe) {
			throw new IllegalArgumentException(
				"Invalid host name " + hostName, uhe);
		}

		ClientBridge clientBridge = new ClientBridge(
			inetAddress,
			Integer.valueOf(
				attributes.getValue(Headers.TEST_BRIDGE_REPORT_SERVER_PORT)));

		String className = attributes.getValue(Headers.TEST_BRIDGE_CLASS_NAME);

		List<String> filterMethodNames = StringUtil.split(
			attributes.getValue(Headers.TEST_BRIDGE_FILTERED_METHOD_NAMES),
			CharPool.COMMA);

		BundleWiring bundleWiring = testBundle.adapt(BundleWiring.class);

		ClassLoader classLoader = bundleWiring.getClassLoader();

		TestClass testClass = new TestClass(classLoader.loadClass(className)) {

			@Override
			protected void scanAnnotatedMembers(
				Map<Class<? extends Annotation>, List<FrameworkMethod>>
					frameworkMethodsMap,
				Map<Class<? extends Annotation>, List<FrameworkField>>
					frameworkFieldsMap) {

				super.scanAnnotatedMembers(
					frameworkMethodsMap, frameworkFieldsMap);

				List<FrameworkMethod> testFrameworkMethods =
					frameworkMethodsMap.get(Test.class);

				List<FrameworkMethod> ignoreFrameworkMethods =
					frameworkMethodsMap.get(Ignore.class);

				if (ignoreFrameworkMethods != null) {
					testFrameworkMethods.removeAll(ignoreFrameworkMethods);
				}

				testFrameworkMethods.removeIf(
					frameworkMethod -> filterMethodNames.contains(
						frameworkMethod.getName()));

				testFrameworkMethods.sort(
					Comparator.comparing(FrameworkMethod::getName));
			}

		};

		bundleContext.addBundleListener(
			bundleEvent -> {
				Bundle bundle = bundleEvent.getBundle();

				if (!testBundle.equals(bundle) ||
					(bundle.getState() != Bundle.ACTIVE)) {

					return;
				}

				try {
					_runTestClass(clientBridge, testClass);
				}
				finally {
					clientBridge.close();
				}
			});
	}

	@Override
	public void stop(BundleContext bundleContext) {
	}

	private void _processThrowable(
		Throwable throwable, ClientBridge clientBridge,
		Description description) {

		if (throwable instanceof AssumptionViolatedException) {

			// To neutralize the nonserializable Matcher field inside
			// AssumptionViolatedException

			AssumptionViolatedException ave = new AssumptionViolatedException(
				throwable.getMessage());

			ave.setStackTrace(throwable.getStackTrace());

			clientBridge.bridge(
				"fireTestAssumptionFailed", new Failure(description, ave));
		}
		else if (throwable instanceof MultipleFailureException) {
			MultipleFailureException mfe = (MultipleFailureException)throwable;

			for (Throwable t : mfe.getFailures()) {
				clientBridge.bridge(
					"fireTestFailure", new Failure(description, t));
			}
		}
		else {
			clientBridge.bridge(
				"fireTestFailure", new Failure(description, throwable));
		}
	}

	private void _runTestClass(ClientBridge clientBridge, TestClass testClass) {
		for (FrameworkMethod frameworkMethod :
				testClass.getAnnotatedMethods(Test.class)) {

			Description description = Description.createTestDescription(
				testClass.getName(), frameworkMethod.getName());

			try {
				clientBridge.bridge("fireTestStarted", description);

				TestExecutorUtil.execute(
					testClass, frameworkMethod.getMethod());
			}
			catch (Throwable t) {
				_processThrowable(t, clientBridge, description);
			}
			finally {
				clientBridge.bridge("fireTestFinished", description);
			}
		}
	}

	private class ClientBridge {

		public ClientBridge(InetAddress inetAddress, int port) {
			_inetAddress = inetAddress;
			_port = port;

			try {
				_socket = new Socket(_inetAddress, _port);

				_objectOutputStream = new ObjectOutputStream(
					_socket.getOutputStream());
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		public void bridge(String methodName, Object object) {
			while (true) {
				try {
					_objectOutputStream.writeUTF(methodName);

					if (object != null) {
						_objectOutputStream.writeObject(object);
					}

					_objectOutputStream.flush();

					return;
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}
			}
		}

		public void close() {
			bridge("kill", null);

			try {
				_socket.close();
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		private final InetAddress _inetAddress;
		private final ObjectOutputStream _objectOutputStream;
		private final int _port;
		private final Socket _socket;

	}

}