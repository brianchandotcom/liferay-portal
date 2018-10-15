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

package com.liferay.portal.target.platform.indexer.internal;

import aQute.bnd.osgi.repository.SimpleIndexer;

import com.liferay.portal.target.platform.indexer.Indexer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Raymond Augé
 */
public class LPKGIndexer implements Indexer {

	public LPKGIndexer(File lpkgFile, Set<String> excludedJarFileNames) {
		_lpkgFile = lpkgFile;
		_excludedJarFileNames = excludedJarFileNames;
	}

	@Override
	public void index(OutputStream outputStream) throws Exception {
		if (_excludedJarFileNames.isEmpty() && _readCachedIndex(outputStream)) {
			return;
		}

		Path tempPath = Files.createTempDirectory(null);

		File tempDir = tempPath.toFile();

		try (ZipFile zipFile = new ZipFile(_lpkgFile)) {
			Set<File> files = new LinkedHashSet<>();

			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			boolean hasExcludedJarFile = false;

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (!name.endsWith(".jar")) {
					continue;
				}

				if (_excludedJarFileNames.contains(
						_toExcludedJarFileName(name))) {

					hasExcludedJarFile = true;

					continue;
				}

				File file = new File(tempDir, name);

				Files.copy(
					zipFile.getInputStream(zipEntry), file.toPath(),
					StandardCopyOption.REPLACE_EXISTING);

				files.add(file);
			}

			if (!hasExcludedJarFile && _readCachedIndex(outputStream)) {
				return;
			}

			SimpleIndexer simpleIndexer = new SimpleIndexer();

			simpleIndexer.base(tempDir.toURI());
			simpleIndexer.compress(false);
			simpleIndexer.files(files);
			simpleIndexer.name(_getRepositoryName(_lpkgFile));

			simpleIndexer.index(outputStream);
		}
		finally {
			PathUtil.deltree(tempPath);
		}
	}

	private String _getRepositoryName(File lpkgFile) {
		String fileName = lpkgFile.getName();

		int index = fileName.lastIndexOf('.');

		if (index > 0) {
			fileName = fileName.substring(0, index);
		}

		return fileName;
	}

	private boolean _readCachedIndex(OutputStream outputStream)
		throws IOException {

		try (ZipFile zipFile = new ZipFile(_lpkgFile)) {
			ZipEntry zipEntry = zipFile.getEntry("index.xml");

			if (zipEntry != null) {
				try (InputStream inputStream =
						zipFile.getInputStream(zipEntry)) {

					_transfer(inputStream, outputStream);
				}

				return true;
			}
		}

		return false;
	}

	private String _toExcludedJarFileName(String name) {
		Matcher matcher = _pattern.matcher(name);

		if (matcher.matches()) {
			name = matcher.group(1) + matcher.group(3);
		}

		return name.toLowerCase();
	}

	/**
	 * @see com.liferay.portal.kernel.util.StreamUtil#transfer
	 */
	private void _transfer(InputStream inputStream, OutputStream outputStream)
		throws IOException {

		int value = -1;

		byte[] bytes = new byte[8192];

		while ((value = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, value);
		}
	}

	private static final Pattern _pattern = Pattern.compile(
		"(.*?)(-\\d+\\.\\d+\\.\\d+)(\\.jar)");

	private final Set<String> _excludedJarFileNames;
	private final File _lpkgFile;

}