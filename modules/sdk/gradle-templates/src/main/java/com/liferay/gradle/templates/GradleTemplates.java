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

package com.liferay.gradle.templates;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Andrea Di Giorgi
 */
public class GradleTemplates {

	public String[] getTemplates() throws Exception {
		ClassLoader classLoader = GradleTemplates.class.getClassLoader();

		URL url = classLoader.getResource(_TEMPLATES_ROOT_DIR);

		if (url == null) {
			throw new IOException("Unable to get templates");
		}

		final List<String> templates = new ArrayList<>();

		String protocol = url.getProtocol();

		if (protocol.equals("file")) {
			Path dirPath = Paths.get(url.toURI());

			try (DirectoryStream<Path> directoryStream =
					Files.newDirectoryStream(dirPath, _directoriesOnlyFilter)) {

				for (Path path : directoryStream) {
					Path fileNamePath = path.getFileName();

					templates.add(fileNamePath.toString());
				}
			}
		}
		else if (protocol.equals("jar")) {
			String path = url.getPath();

			int pos = path.indexOf('!');

			if (pos == -1) {
				throw new IOException("Unable to get templates from " + url);
			}

			URI jarFileURI = new URI(path.substring(0, pos));

			try (JarFile jarFile = new JarFile(new File(jarFileURI))) {
				Enumeration<JarEntry> enumeration = jarFile.entries();

				while (enumeration.hasMoreElements()) {
					JarEntry jarEntry = enumeration.nextElement();

					if (!jarEntry.isDirectory()) {
						continue;
					}

					String name = jarEntry.getName();

					if (name.startsWith(_TEMPLATES_ROOT_DIR + "/") &&
						(name.indexOf('/', _TEMPLATES_ROOT_DIR.length() + 1) ==
							(name.length() - 1))) {

						String template = name.substring(
							_TEMPLATES_ROOT_DIR.length() + 1,
							name.length() - 1);

						templates.add(template);
					}
				}
			}
		}
		else {
			throw new IOException("Unable to get templates from " + url);
		}

		Collections.sort(templates);

		return templates.toArray(new String[templates.size()]);
	}

	private static final String _TEMPLATES_ROOT_DIR = "standalone";

	private static final DirectoryStream.Filter<Path> _directoriesOnlyFilter =
		new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				if (Files.isDirectory(path)) {
					return true;
				}

				return false;
			}

		};

}