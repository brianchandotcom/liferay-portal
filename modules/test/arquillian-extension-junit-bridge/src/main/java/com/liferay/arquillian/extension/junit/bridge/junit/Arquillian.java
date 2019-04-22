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
import com.liferay.arquillian.extension.junit.bridge.client.MBeans;
import com.liferay.arquillian.extension.junit.bridge.client.SocketUtil;
import com.liferay.arquillian.extension.junit.bridge.command.KillCommand;
import com.liferay.arquillian.extension.junit.bridge.command.RunNotifierCommand;
import com.liferay.petra.string.CharPool;

import java.io.IOException;

import java.lang.annotation.Annotation;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
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

		Set<Class<?>> testClasses = _getTestClasses(_clazz);

		Iterator<Class<?>> iterator = testClasses.iterator();

		while (iterator.hasNext()) {
			Class<?> clazz = iterator.next();

			if (!filter.shouldRun(Description.createSuiteDescription(clazz))) {
				iterator.remove();
			}
		}
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

		ServerSocket serverSocket = null;

		try {
			serverSocket = SocketUtil.getServerSocket();

			if (_bundleId == 0) {
				Random random = new SecureRandom();

				long passCode = random.nextLong();

				_bundleId = _installBundle(
					frameworkMBean, serverSocket.getLocalPort(), passCode);

				frameworkMBean.startBundle(_bundleId);

				SocketUtil.connect(passCode);
			}

			try {
				while (true) {
					SocketUtil.writeUTF(_clazz.getName());

					while (true) {
						Object object = SocketUtil.readObject();

						if (object instanceof KillCommand) {
							return;
						}

						RunNotifierCommand runNotifierCommand =
							(RunNotifierCommand)object;

						runNotifierCommand.execute(runNotifier);
					}
				}
			}
			finally {
				Set<Class<?>> testClasses = _getTestClasses(_clazz);

				testClasses.remove(_clazz);

				if (testClasses.isEmpty()) {
					try {
						frameworkMBean.uninstallBundle(_bundleId);

						_bundleId = 0;

						SocketUtil.close();

						serverSocket.close();
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

	private static Set<Class<?>> _getTestClasses(Class<?> testClass) {
		if (_testClasses == null) {
			Set<Class<?>> testClasses = new HashSet<>();

			ProtectionDomain protectionDomain = testClass.getProtectionDomain();

			CodeSource codeSource = protectionDomain.getCodeSource();

			URL locationURL = codeSource.getLocation();

			Path startPath = Paths.get(locationURL.getPath());

			ClassLoader classLoader = testClass.getClassLoader();

			try {
				Files.walkFileTree(
					startPath,
					new SimpleFileVisitor<Path>() {

						@Override
						public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes) {

							Path relativePath = startPath.relativize(filePath);

							String relativePathString = relativePath.toString();

							if (!relativePathString.endsWith("Test.class")) {
								return FileVisitResult.CONTINUE;
							}

							relativePathString = relativePathString.substring(
								0, relativePathString.length() - 6);

							relativePathString = relativePathString.replace(
								CharPool.SLASH, CharPool.PERIOD);

							try {
								Class<?> clazz = classLoader.loadClass(
									relativePathString);

								RunWith runWith = clazz.getAnnotation(
									RunWith.class);

								if ((runWith == null) ||
									(runWith.value() != Arquillian.class)) {

									return FileVisitResult.CONTINUE;
								}

								testClasses.add(clazz);
							}
							catch (ClassNotFoundException cnfe) {
								throw new RuntimeException(cnfe);
							}

							return FileVisitResult.CONTINUE;
						}

					});
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}

			_testClasses = testClasses;
		}

		return _testClasses;
	}

	private long _installBundle(
			FrameworkMBean frameworkMBean, int port, long passCode)
		throws Exception {

		InetAddress inetAddress = SocketUtil.getInetAddress();

		Path path = BndBundleUtil.createBundle(
			_filteredSortedTestClass._filteredMethodNames,
			inetAddress.getHostAddress(), port, passCode);

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
	private static Set<Class<?>> _testClasses;

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