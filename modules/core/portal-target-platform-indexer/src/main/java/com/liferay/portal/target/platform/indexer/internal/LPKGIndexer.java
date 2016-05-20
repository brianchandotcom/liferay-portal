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

import com.liferay.portal.target.platform.indexer.Indexer;

import java.io.File;
import java.io.FileOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.indexer.ResourceIndexer;
import org.osgi.service.indexer.impl.RepoIndex;

/**
 * @author Raymond Augé
 */
public class LPKGIndexer implements Indexer {

	public LPKGIndexer(File lpkgFile) {
		_lpkgFile = lpkgFile;

		_config.put("compressed", "false");
		_config.put(
			"license.url", "https://www.liferay.com/downloads/ce-license");
		_config.put("pretty", "true");
		_config.put("repository.name", lpkgFile.getName());
		_config.put("stylesheet", "http://www.osgi.org/www/obr2html.xsl");
	}

	@Override
	public File index(File outputFile) throws Exception {
		Path tempPath = Files.createTempDirectory(null);

		File tempDir = tempPath.toFile();

		_config.put("root.url", tempDir.getCanonicalPath());

		String bundleSymbolicName = _lpkgFile.getName();
		String bundleVersion = "1.0.0";

		try (ZipFile zipFile = new ZipFile(_lpkgFile)) {
			ResourceIndexer resourceIndexer = new RepoIndex();

			Set<File> files = new LinkedHashSet<>();

			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith("liferay-marketplace.properties")) {
					Properties properties = new Properties();

					properties.load(zipFile.getInputStream(zipEntry));

					bundleSymbolicName = properties.getProperty("title");
					bundleVersion = properties.getProperty("version");

					continue;
				}
				else if (!name.endsWith(".jar")) {
					continue;
				}

				File file = new File(tempDir, name);

				Files.copy(zipFile.getInputStream(zipEntry), file.toPath());

				files.add(file);
			}

			File indexFile = new File(
				tempDir,
				bundleSymbolicName + "-" + bundleVersion + "-index.xml");

			try (FileOutputStream fileOutputStream =
					new FileOutputStream(indexFile)) {

				resourceIndexer.index(files, fileOutputStream, _config);
			}

			if (outputFile.isDirectory()) {
				outputFile = new File(outputFile, indexFile.getName());
			}

			Files.copy(
				indexFile.toPath(), outputFile.toPath(),
				StandardCopyOption.COPY_ATTRIBUTES,
				StandardCopyOption.REPLACE_EXISTING);

			return outputFile;
		}
		finally {
			PathUtil.deltree(tempPath);
		}
	}

	private final Map<String, String> _config = new HashMap<>();
	private final File _lpkgFile;

}