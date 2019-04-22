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

package com.liferay.arquillian.extension.junit.bridge.junit;

import com.liferay.arquillian.extension.junit.bridge.client.BndBundleUtil;
import com.liferay.arquillian.extension.junit.bridge.client.ClientState;
import com.liferay.arquillian.extension.junit.bridge.client.MBeans;
import com.liferay.arquillian.extension.junit.bridge.client.SocketUtil;
import com.liferay.arquillian.extension.junit.bridge.command.RunNotifierCommand;

import java.io.IOException;

import java.lang.annotation.Annotation;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import org.osgi.jmx.framework.FrameworkMBean;

/**
 * @author Shuyang Zhou
 */
public class Arquillian extends Runner implements Filterable {

	public Arquillian(Class<?> clazz) {
		_clazz = clazz;

		_filteredSortedTestClass = new FilteredSortedTestClass(_clazz, null);
	}

	@Override
	public void filter(Filter filter) throws NoTestsRemainException {
		_filteredSortedTestClass = new FilteredSortedTestClass(_clazz, filter);

		List<FrameworkMethod> frameworkMethods =
			_filteredSortedTestClass.getAnnotatedMethods(Test.class);

		if (frameworkMethods.isEmpty()) {
			throw new NoTestsRemainException();
		}

		_clientState.filterTestClasses(filter, _clazz);
	}

	@Override
	public Description getDescription() {
		return Description.createSuiteDescription(_clazz);
	}

	@Override
	public void run(RunNotifier runNotifier) {
		List<FrameworkMethod> frameworkMethods = new ArrayList<>(
			_filteredSortedTestClass.getAnnotatedMethods(Test.class));

		frameworkMethods.removeIf(
			frameworkMethod -> {
				if (frameworkMethod.getAnnotation(Ignore.class) != null) {
					runNotifier.fireTestIgnored(
						Description.createTestDescription(
							_clazz, frameworkMethod.getName(),
							frameworkMethod.getAnnotations()));

					return true;
				}

				return false;
			});

		if (frameworkMethods.isEmpty()) {
			_clientState.removeTestClass(_clazz);

			return;
		}

		// Enforce client side test class initialization

		try {
			Class.forName(_clazz.getName(), true, _clazz.getClassLoader());
		}
		catch (ClassNotFoundException cnfe) {
			runNotifier.fireTestFailure(new Failure(getDescription(), cnfe));

			return;
		}

		FrameworkMBean frameworkMBean = MBeans.getFrameworkMBean();

		try {
			ServerSocket serverSocket = SocketUtil.getServerSocket();

			if (_bundleId == 0) {
				Random random = new SecureRandom();

				long passCode = random.nextLong();

				_bundleId = _installBundle(
					frameworkMBean,
					_filteredSortedTestClass._filteredMethodNames,
					serverSocket.getInetAddress(), serverSocket.getLocalPort(),
					passCode);

				frameworkMBean.startBundle(_bundleId);

				SocketUtil.connect(passCode);
			}

			try {
				SocketUtil.writeUTF(_clazz.getName());

				Object object = null;

				while ((object = SocketUtil.readObject()) != null) {
					RunNotifierCommand runNotifierCommand =
						(RunNotifierCommand)object;

					runNotifierCommand.execute(runNotifier);
				}
			}
			finally {
				_clientState.removeTestClass(_clazz);

				if (_clientState.isEmpty()) {
					try {
						frameworkMBean.uninstallBundle(_bundleId);

						_bundleId = 0;

						SocketUtil.close();
					}
					catch (IOException ioe) {
						runNotifier.fireTestFailure(
							new Failure(getDescription(), ioe));
					}
				}
			}
		}
		catch (Throwable t) {
			runNotifier.fireTestFailure(new Failure(getDescription(), t));
		}
	}

	private long _installBundle(
			FrameworkMBean frameworkMBean, List<String> filteredMethodNames,
			InetAddress inetAddress, int port, long passCode)
		throws Exception {

		Path path = BndBundleUtil.createBundle(
			filteredMethodNames, inetAddress.getHostAddress(), port, passCode);

		URI uri = path.toUri();

		URL url = uri.toURL();

		try {
			return frameworkMBean.installBundleFromURL(
				url.getPath(), url.toExternalForm());
		}
		finally {
			Files.delete(path);
		}
	}

	private static long _bundleId;
	private static final ClientState _clientState = new ClientState();

	private final Class<?> _clazz;
	private FilteredSortedTestClass _filteredSortedTestClass;

	private class FilteredSortedTestClass extends TestClass {

		@Override
		protected void scanAnnotatedMembers(
			Map<Class<? extends Annotation>, List<FrameworkMethod>>
				frameworkMethodsMap,
			Map<Class<? extends Annotation>, List<FrameworkField>>
				frameworkFieldsMap) {

			super.scanAnnotatedMembers(frameworkMethodsMap, frameworkFieldsMap);

			_testFrameworkMethods = frameworkMethodsMap.get(Test.class);

			_testFrameworkMethods.sort(
				Comparator.comparing(FrameworkMethod::getName));
		}

		private FilteredSortedTestClass(Class<?> clazz, Filter filter) {
			super(clazz);

			if (filter != null) {
				_testFrameworkMethods.removeIf(
					frameworkMethod -> {
						String methodName = frameworkMethod.getName();

						if (filter.shouldRun(
								Description.createTestDescription(
									_clazz, methodName))) {

							return false;
						}

						_filteredMethodNames.add(methodName);

						return true;
					});
			}
		}

		private final List<String> _filteredMethodNames = new ArrayList<>();
		private List<FrameworkMethod> _testFrameworkMethods;

	}

}