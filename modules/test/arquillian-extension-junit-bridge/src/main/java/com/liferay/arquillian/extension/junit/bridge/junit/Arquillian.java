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
import com.liferay.petra.io.unsync.UnsyncBufferedInputStream;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;

import java.nio.channels.ServerSocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;

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
	}

	@Override
	public Description getDescription() {
		return Description.createSuiteDescription(
			_clazz.getName(), _clazz.getAnnotations());
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

		Thread thread = _startServerThread(runNotifier);

		try {

			// Enforce client side test class initialization

			Class.forName(_clazz.getName(), true, _clazz.getClassLoader());

			try (Closeable closeable = _installBundle()) {
				thread.join();
			}
		}
		catch (Throwable t) {
			runNotifier.fireTestFailure(new Failure(getDescription(), t));
		}
	}

	private ServerSocket _getServerSocket() {
		while (true) {
			try {
				ServerSocketChannel serverSocketChannel =
					ServerSocketChannel.open();

				ServerSocket serverSocket = serverSocketChannel.socket();

				_port = new Random().nextInt(65535);

				serverSocket.bind(new InetSocketAddress(_inetAddress, _port));

				return serverSocket;
			}
			catch (IOException ioe) {
			}
		}
	}

	private Closeable _installBundle() throws Exception {
		Path path = BndBundleUtil.createBundle(
			_clazz.getName(), _filteredSortedTestClass._filteredMethodNames,
			_inetAddress.getHostAddress(), _port);

		URI uri = path.toUri();

		URL url = uri.toURL();

		FrameworkMBean frameworkMBean = MBeans.getFrameworkMBean();

		long bundleId;

		try {
			bundleId = frameworkMBean.installBundleFromURL(
				url.getPath(), url.toExternalForm());
		}
		finally {
			Files.delete(path);
		}

		frameworkMBean.startBundle(bundleId);

		return () -> frameworkMBean.uninstallBundle(bundleId);
	}

	private Thread _startServerThread(RunNotifier runNotifier) {
		Thread thread = new Thread(
			new ServerRunnable(runNotifier, _getServerSocket()),
			_clazz.getName() + "-Test-Thread");

		thread.setDaemon(true);

		thread.start();

		return thread;
	}

	private static final InetAddress _inetAddress;
	private static int _port;

	static {
		try {
			_inetAddress = InetAddress.getByName("127.0.0.1");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

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

	private class ServerRunnable implements Runnable {

		public ServerRunnable(
			RunNotifier runNotifier, ServerSocket serverSocket) {

			_runNotifier = runNotifier;
			_serverSocket = serverSocket;
		}

		@Override
		public void run() {
			Class<?> clazz = _runNotifier.getClass();

			while (true) {
				try (Socket socket = _serverSocket.accept();
					InputStream inputStream = socket.getInputStream();
					ObjectInputStream objectInputStream = new ObjectInputStream(
						new UnsyncBufferedInputStream(inputStream))) {

					String methodName = objectInputStream.readUTF();

					if (methodName.equals("kill")) {
						_serverSocket.close();

						break;
					}

					Object object = objectInputStream.readObject();

					Method method = clazz.getMethod(
						methodName, object.getClass());

					method.invoke(_runNotifier, object);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		private final RunNotifier _runNotifier;
		private final ServerSocket _serverSocket;

	}

}