/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.petra.process.ClassPathUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.tools.ToolDependencies;

import java.io.File;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Lily Chi
 */
public class SampleSQLBuilderLauncher {

	public static void main(String[] args) throws Exception {
		ToolDependencies.wireBasic();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		File tempDir = FileUtil.createTempFolder();

		ClassLoader classLoader = new URLClassLoader(
			_getURLs(contextClassLoader, tempDir.toPath()), null);

		currentThread.setContextClassLoader(classLoader);

		try {
			InstanceFactory.newInstance(
				classLoader, SampleSQLBuilder.class.getName());
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);

			FileUtil.deltree(tempDir);
		}
	}

	private static URL[] _getURLs(ClassLoader classLoader, Path tempDirPath)
		throws Exception {

		Set<URL> urls = SetUtil.fromArray(
			ClassPathUtil.getClassPathURLs(
				ClassPathUtil.getJVMClassPath(true)));

		URL url = classLoader.getResource("lib");

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				url.toURI(), Collections.emptyMap());
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				fileSystem.getPath("/lib"), "*.jar")) {

			Iterator<Path> iterator = directoryStream.iterator();

			while (iterator.hasNext()) {
				Path path = iterator.next();

				Path targetPath = tempDirPath.resolve(
					String.valueOf(path.getFileName()));

				Files.copy(path, targetPath);

				URI uri = targetPath.toUri();

				urls.add(uri.toURL());
			}
		}

		return urls.toArray(new URL[0]);
	}

}