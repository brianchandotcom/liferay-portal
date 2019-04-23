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

package com.liferay.arquillian.extension.junit.bridge.client;

import com.liferay.arquillian.extension.junit.bridge.command.RunNotifierCommand;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.CharPool;

import java.io.Closeable;
import java.io.IOException;

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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import org.osgi.jmx.framework.FrameworkMBean;

/**
 * @author Shuyang Zhou
 */
public class ClientState {

	public void filterTestClasses(
		Class<?> testClass, Predicate<Class<?>> predicate) {

		Set<Class<?>> testClasses = _getTestClasses(testClass);

		testClasses.removeIf(predicate);
	}

	public Connection open(
			Class<?> testClass,
			Map<String, List<String>> filteredMethodNamesMap)
		throws Exception {

		if (_bundleId == 0) {
			FrameworkMBean frameworkMBean = MBeans.getFrameworkMBean();

			ServerSocket serverSocket = SocketUtil.getServerSocket();

			Random random = new SecureRandom();

			long passCode = random.nextLong();

			_bundleId = _installBundle(
				frameworkMBean, filteredMethodNamesMap,
				serverSocket.getInetAddress(), serverSocket.getLocalPort(),
				passCode);

			try {
				frameworkMBean.startBundle(_bundleId);

				SocketUtil.connect(passCode);
			}
			catch (Throwable t) {
				frameworkMBean.uninstallBundle(_bundleId);
			}
		}

		return new Connection() {

			@Override
			public void close() throws IOException {
				Set<Class<?>> testClasses = _getTestClasses(testClass);

				testClasses.remove(testClass);

				if (testClasses.isEmpty()) {
					FrameworkMBean frameworkMBean = MBeans.getFrameworkMBean();

					frameworkMBean.uninstallBundle(_bundleId);

					SocketUtil.close();

					_testClasses = null;
					_bundleId = 0;
				}
			}

			@Override
			public void execute(
					String testClassName, Consumer<RunNotifierCommand> consumer)
				throws Exception {

				SocketUtil.writeUTF(testClassName);

				Object object = null;

				while ((object = SocketUtil.readObject()) != null) {
					consumer.accept((RunNotifierCommand)object);
				}
			}

		};
	}

	public void removeTestClass(Class<?> testClass) {
		Set<Class<?>> testClasses = _getTestClasses(testClass);

		testClasses.remove(testClass);
	}

	public interface Connection extends Closeable {

		public void execute(
				String testClassName, Consumer<RunNotifierCommand> consumer)
			throws Exception;

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

								if (clazz.getAnnotation(Ignore.class) == null) {
									testClasses.add(clazz);
								}
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

			if (!testClasses.contains(testClass)) {
				testClasses.clear();

				testClasses.add(testClass);
			}

			_testClasses = testClasses;
		}

		return _testClasses;
	}

	private static long _installBundle(
			FrameworkMBean frameworkMBean,
			Map<String, List<String>> filteredMethodNamesMap,
			InetAddress inetAddress, int port, long passCode)
		throws Exception {

		Path path = BndBundleUtil.createBundle(
			filteredMethodNamesMap, inetAddress.getHostAddress(), port,
			passCode);

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

}