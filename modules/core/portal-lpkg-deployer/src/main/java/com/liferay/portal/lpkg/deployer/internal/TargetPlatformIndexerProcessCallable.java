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

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.ServiceLoader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

/**
 * @author Shuyang Zhou
 */
public class TargetPlatformIndexerProcessCallable
	implements ProcessCallable<byte[]> {

	public TargetPlatformIndexerProcessCallable(String... dirNames) {
		_dirNames = dirNames;
	}

	@Override
	public byte[] call() throws ProcessException {
		Path tempPath = null;

		try {
			tempPath = Files.createTempDirectory(null);

			return _indexTargetPlatform(tempPath);
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
		finally {
			_delete(tempPath);
		}
	}

	private void _delete(Path path) throws ProcessException {
		if (path == null) {
			return;
		}

		try {
			Files.walkFileTree(
				path,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult postVisitDirectory(
							Path dir, IOException exc)
						throws IOException {

						Files.delete(dir);

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path file, BasicFileAttributes attrs)
						throws IOException {

						Files.delete(file);

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
	}

	private byte[] _indexTargetPlatform(Path tempPath) throws ProcessException {
		Framework framework = null;

		try {
			ServiceLoader<FrameworkFactory> serviceLoader = ServiceLoader.load(
				FrameworkFactory.class);

			FrameworkFactory frameworkFactory = serviceLoader.iterator().next();

			framework = frameworkFactory.newFramework(
				Collections.singletonMap(
					org.osgi.framework.Constants.FRAMEWORK_STORAGE,
					tempPath.toString()));

			framework.init();

			BundleContext bundleContext = framework.getBundleContext();

			Bundle systemBundle = bundleContext.getBundle(0);

			ClassLoader classLoader =
				TargetPlatformIndexerProcessCallable.class.getClassLoader();

			Class<?> clazz = classLoader.loadClass(
				"com.liferay.portal.target.platform.indexer.internal." +
					"TargetPlatformIndexer");

			Constructor<?> constructor = clazz.getConstructor(
				Bundle.class, String[].class);

			Object indexer = constructor.newInstance(systemBundle, _dirNames);

			Method method = clazz.getMethod("index", OutputStream.class);

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			method.invoke(indexer, unsyncByteArrayOutputStream);

			return unsyncByteArrayOutputStream.toByteArray();
		}
		catch (Exception e) {
			throw new ProcessException(e);
		}
		finally {
			try {
				framework.stop();
			}
			catch (BundleException be) {
				throw new ProcessException(be);
			}
		}
	}

	private static final long serialVersionUID = 1L;

	private final String[] _dirNames;

}