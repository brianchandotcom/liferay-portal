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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.CharPool;

import java.io.IOException;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Filter;

/**
 * @author Shuyang Zhou
 */
public class ClientState {

	public void filterTestClasses(Filter filter, Class<?> testClass) {
		Set<Class<?>> testClasses = _getTestClasses(testClass);

		Iterator<Class<?>> iterator = testClasses.iterator();

		while (iterator.hasNext()) {
			Class<?> clazz = iterator.next();

			if (!filter.shouldRun(Description.createSuiteDescription(clazz))) {
				iterator.remove();
			}
		}
	}

	public boolean isEmpty() {
		return _testClasses.isEmpty();
	}

	public void removeTestClass(Class<?> testClass) {
		Set<Class<?>> testClasses = _getTestClasses(testClass);

		testClasses.remove(testClass);
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

			_testClasses = testClasses;
		}

		return _testClasses;
	}

	private static Set<Class<?>> _testClasses;

}