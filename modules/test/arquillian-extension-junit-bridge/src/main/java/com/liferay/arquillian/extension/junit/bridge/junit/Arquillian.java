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
import com.liferay.arquillian.extension.junit.bridge.command.RunNotifierCommand;
import com.liferay.petra.string.CharPool;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.lang.annotation.Annotation;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;

import java.nio.channels.ServerSocketChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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

		Random random = new SecureRandom();

		_passCode = random.nextLong();
	}

	@Override
	public void filter(Filter filter) throws NoTestsRemainException {
		_filteredSortedTestClass = new FilteredSortedTestClass(_clazz, filter);

		List<FrameworkMethod> frameworkMethods =
			_filteredSortedTestClass.getAnnotatedMethods(Test.class);

		if (frameworkMethods.isEmpty()) {
			throw new NoTestsRemainException();
		}

		Iterator<Class> iterator = _classes.iterator();

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
			serverSocket = _getServerSocket();

			long bundleId = _installBundle(
				frameworkMBean, serverSocket.getLocalPort());

			try {
				frameworkMBean.startBundle(bundleId);

				while (true) {
					try (Socket socket = serverSocket.accept();
						InputStream inputStream = socket.getInputStream();
						OutputStream outputStream = socket.getOutputStream();
						ObjectInputStream objectInputStream =
							new ObjectInputStream(inputStream);
						ObjectOutputStream objectOutputStream =
							new ObjectOutputStream(outputStream)) {

						if (_passCode != objectInputStream.readLong()) {
							_logger.log(
								Level.WARNING,
								"Pass code mismatch, dropped connection from " +
									socket.getRemoteSocketAddress());

							continue;
						}

						objectOutputStream.writeUTF(_clazz.getName());

						objectOutputStream.flush();

						while (true) {
							RunNotifierCommand runNotifierCommand =
								(RunNotifierCommand)
									objectInputStream.readObject();

							runNotifierCommand.execute(runNotifier);
						}
					}
					catch (EOFException eofe) {
						break;
					}
				}
			}
			finally {
				frameworkMBean.uninstallBundle(bundleId);

				_classes.remove(_clazz);
			}
		}
		catch (Throwable t) {
			runNotifier.fireTestFailure(new Failure(getDescription(), t));
		}
		finally {
			if (_classes.isEmpty()) {
				try {
					serverSocket.close();
				}
				catch (IOException ioe) {
					runNotifier.fireTestFailure(
						new Failure(getDescription(), ioe));
				}
			}
		}
	}

	private static ServerSocket _getServerSocket() throws IOException {
		if (_serverSocket != null) {
			return _serverSocket;
		}

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		int port = _START_PORT;

		while (true) {
			try {
				ServerSocket serverSocket = serverSocketChannel.socket();

				serverSocket.bind(new InetSocketAddress(_inetAddress, port));

				_serverSocket = serverSocket;

				return serverSocket;
			}
			catch (IOException ioe) {
				port++;
			}
		}
	}

	private long _installBundle(FrameworkMBean frameworkMBean, int port)
		throws Exception {

		Path path = BndBundleUtil.createBundle(
			_filteredSortedTestClass._filteredMethodNames,
			_inetAddress.getHostAddress(), port, _passCode);

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

	private static final int _START_PORT = 32764;

	private static final Logger _logger = Logger.getLogger(
		Arquillian.class.getName());

	private static final Set<Class> _classes = new HashSet<Class>() {
		{
			try {
				Path srcDir = Paths.get(
					System.getProperty("user.dir"), "src", "testIntegration",
					"java");

				Files.walkFileTree(
					srcDir,
					new SimpleFileVisitor<Path>() {

						@Override
						public FileVisitResult visitFile(
								Path path,
								BasicFileAttributes basicFileAttributes)
							throws IOException {

							Path relativePath = srcDir.relativize(path);

							String relativePathString = relativePath.toString();

							if (relativePathString.endsWith("Test.java")) {
								relativePathString =
									relativePathString.substring(
										0, relativePathString.length() - 5);

								relativePathString = relativePathString.replace(
									CharPool.SLASH, CharPool.PERIOD);

								try {
									add(Class.forName(relativePathString));
								}
								catch (ClassNotFoundException cnfe) {
									throw new RuntimeException(cnfe);
								}
							}

							return FileVisitResult.CONTINUE;
						}

					});
			}
			catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			}
		}
	};

	private static final InetAddress _inetAddress =
		InetAddress.getLoopbackAddress();
	private static ServerSocket _serverSocket;

	private final Class<?> _clazz;
	private FilteredSortedTestClass _filteredSortedTestClass;
	private final long _passCode;

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