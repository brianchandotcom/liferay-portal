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

import com.liferay.petra.io.unsync.UnsyncBufferedOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.lang.annotation.Annotation;

import java.net.InetAddress;
import java.net.Socket;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.TestClass;

/**
 * @author Matthew Tambara
 */
public class JMXTestRunner implements JMXTestRunnerMBean {

	public JMXTestRunner(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	@Override
	public void runTestMethod(
		String className, String methodName, List<String> filterMethodNames,
		InetAddress inetAddress, int port) {

		ClientBridge clientBridge = new ClientBridge(inetAddress, port);

		Description description = Description.createTestDescription(
			className, methodName);

		clientBridge.bridge("fireTestStarted", description);

		try {
			TestClass testClass = new TestClass(
				_classLoader.loadClass(className)) {

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

			for (FrameworkMethod frameworkMethod :
					testClass.getAnnotatedMethods(Test.class)) {

				if (!methodName.equals(frameworkMethod.getName())) {
					continue;
				}

				TestExecutorUtil.execute(
					testClass, frameworkMethod.getMethod());
			}
		}
		catch (Throwable t) {
			_processThrowable(t, clientBridge, description);
		}
		finally {
			clientBridge.bridge("fireTestFinished", description);
		}
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

	private final ClassLoader _classLoader;

	private class ClientBridge {

		public ClientBridge(InetAddress inetAddress, int port) {
			_inetAddress = inetAddress;
			_port = port;
		}

		public void bridge(String methodName, Object object) {
			try (Socket socket = new Socket(_inetAddress, _port);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new UnsyncBufferedOutputStream(socket.getOutputStream()))) {

				objectOutputStream.writeUTF(methodName);
				objectOutputStream.writeObject(object);
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		private final InetAddress _inetAddress;
		private final int _port;

	}

}